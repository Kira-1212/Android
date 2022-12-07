package edu.sjsu.android.accgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;

public class SimulationView extends View implements SensorEventListener
{
    private SensorManager sensorManager ;
    private Sensor sensor ;
    private Display display ;

    private Bitmap mField ;
    private Bitmap mBasket ;
    private Bitmap mBitMAP ;

    private static final int BALL_SIZE = 64 ;
    private static final int BASKET_SIZE = 80 ;
    private static int screenWidth ;
    private static int screenHeight ;

    private float mXOrigin = 0 ;
    private float mYOrigin = 0 ;
    private float mHorizontalBound = 0 ;
    private float mVerticalBound = 0 ;

    // movement in 3 dimensions
    private float x ;   // x axis
    private float y ;   // y axis
    private float z ;   // z axis
    private long timestamp ;    // timestamp of the event

    private Particle mBall = new Particle() ;

    public SimulationView(Context context)
    {
        super(context) ;

        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE) ;
        display = mWindowManager.getDefaultDisplay() ;

        // Getting the device screen size.
        // Source: https://stackoverflow.com/questions/1016896/how-to-get-screen-dimensions-as-pixels-in-android
        Point size = new Point() ;
        display.getSize(size) ;
        screenWidth = size.x ;
        screenHeight = size.y ;

        // Initializing images from drawable
        Bitmap ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball) ;
        mBitMAP = Bitmap.createScaledBitmap(ball, BALL_SIZE, BALL_SIZE, true) ;

        Bitmap basket = BitmapFactory.decodeResource(getResources(), R.drawable.basket) ;
        mBasket = Bitmap.createScaledBitmap(basket, BASKET_SIZE, BASKET_SIZE, true) ;

        Bitmap field = BitmapFactory.decodeResource(getResources(), R.drawable.field) ;
        mField = Bitmap.createScaledBitmap(field, screenWidth, screenHeight, true) ;

        // Initializing the accelerometer sensor
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE) ;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) ;
        startSimulation() ;
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh) ;

        mXOrigin = w * 0.5f ;
        mYOrigin = h * 0.5f ;
        mHorizontalBound = (w - BALL_SIZE) * 0.5f ;
        mVerticalBound = (h - BALL_SIZE) * 0.5f ;
    }

    @Override public void onSensorChanged(SensorEvent event)
    {
        // handling events from the accelerometer
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            // based on the rotation values, update values of accelerometer
            switch (display.getRotation())
            {
                case Surface.ROTATION_0:
                    x = event.values[0] ;
                    y = event.values[1] ;
                    break ;
                case Surface.ROTATION_90:
                    x = -event.values[1] ;
                    y = event.values[0] ;
                    break;
                case Surface.ROTATION_180:
                    x = -event.values[0] ;
                    y = -event.values[1] ;
                    break;
                case Surface.ROTATION_270:
                    x = event.values[1] ;
                    y = -event.values[0] ;
                    break ;
            }

            z = event.values[2] ;
            timestamp = event.timestamp ;
        }
    }

    @Override public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    public void startSimulation()
    {
        sensorManager.registerListener(this, sensor, sensorManager.SENSOR_DELAY_GAME) ;
    }

    public void stopSimulation() {sensorManager.unregisterListener(this) ;}


    //Making the screen filled with basketball field
    @Override protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas) ;

        canvas.drawBitmap(mField, 0, 0, null) ;
        canvas.drawBitmap(mBasket, mXOrigin - BASKET_SIZE / 2, mYOrigin - BASKET_SIZE / 2, null) ;

        mBall.updatePosition(x, y, z, timestamp) ;
        mBall.resolveCollisionWithBounds(mHorizontalBound, mVerticalBound) ;

        canvas.drawBitmap(mBitMAP, (mXOrigin - BALL_SIZE / 2) + mBall.mPosX, (mYOrigin - BALL_SIZE / 2) - mBall.mPosY, null) ;

        invalidate() ;
    }
}
