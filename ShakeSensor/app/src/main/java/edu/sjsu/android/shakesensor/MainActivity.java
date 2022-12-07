package edu.sjsu.android.shakesensor;

import androidx.appcompat.app.AppCompatActivity;



import android.graphics.Color ;
import android.hardware.Sensor ;
import android.hardware.SensorEvent ;
import android.hardware.SensorEventListener ;
import android.hardware.SensorManager ;
import android.os.Bundle ;
import android.view.View ;
import android.view.Window ;
import android.view.WindowManager ;
import android.widget.TextView ;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private SensorManager sensorManager ;
    private boolean color = false ;
    private View view ;
    private long lastUpdate ;
    private TextView textx, texty, textz ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        requestWindowFeature(Window.FEATURE_NO_TITLE) ;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN) ;
        setContentView(R.layout.activity_main) ;

        //get text views
        textx = (TextView) findViewById(R.id.xval) ;
        texty = (TextView) findViewById(R.id.yval) ;
        textz = (TextView) findViewById(R.id.zval) ;

        view = findViewById(R.id.textView) ;
        view.setBackgroundColor(Color.BLUE) ;

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE) ;
        lastUpdate = System.currentTimeMillis() ;
    }


    @Override public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            displayAccelerometer(event) ;
            checkShake(event) ;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void displayAccelerometer(SensorEvent event)
    {
        //Many sensors return 3 values, one for each axis.
        float x = event.values[0] ;
        float y = event.values[1] ;
        float z = event.values[2] ;

        //display values using TextView
        textx.setText("X axis" + "\t\t" + x) ;
        texty.setText("Y axis" + "\t\t" + y) ;
        textz.setText("Z axis" + "\t\t" + z) ;
    }

    private void checkShake(SensorEvent event)
    {
        //movement
        float x = event.values[0] ;
        float y = event.values[1] ;
        float z = event.values[2] ;

        float accelerationSquareRoot = (x * x + y * y + z * z) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH) ;
        long actualTime = System.currentTimeMillis() ;
        if (accelerationSquareRoot >= 2)
        {
            if (actualTime - lastUpdate < 200) return;

            lastUpdate = actualTime ;
            Toast.makeText(this, "Don't shake me!", Toast.LENGTH_SHORT).show() ;
            if (color) view.setBackgroundColor(Color.BLUE) ;
            else view.setBackgroundColor(Color.RED) ;
            color = !color ;
        }
    }

    @Override protected void onResume()
    {
        super.onResume() ;
        sensorManager.registerListener( this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL) ;
    }

    @Override protected void onPause()
    {
        super.onPause() ;
        sensorManager.unregisterListener( this) ;
    }
}