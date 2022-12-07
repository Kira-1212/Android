package edu.sjsu.android.accgame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PowerManager;
import android.view.WindowManager ;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "edu.sjsu.android.accgame:MainActivity" ;
    private PowerManager.WakeLock mWakeLock ;
    private SimulationView mSimulationView ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;

        PowerManager mPowerManager = (PowerManager) getSystemService(POWER_SERVICE) ;
        mWakeLock = mPowerManager.newWakeLock(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, TAG) ;
        mSimulationView = new SimulationView(this) ;

        // Set to the simulation view instead of layout file
        setContentView(mSimulationView) ;
    }

    @Override protected void onResume()
    {
        super.onResume() ;
        mWakeLock.acquire();    // acquire wakelock
        mSimulationView.startSimulation() ; // start simulation to register the listener
    }

    @Override protected void onPause()
    {
        super.onPause() ;
        mWakeLock.release() ;   // Release wakelock
        mSimulationView.stopSimulation() ;  // stop simulation to unregister the listener
    }
}