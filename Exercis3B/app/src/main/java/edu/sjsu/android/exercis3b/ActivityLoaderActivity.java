package edu.sjsu.android.exercis3b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ActivityLoaderActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.activity_main) ;


        Button webButton = findViewById(R.id.web) ;
        Button callButton = findViewById(R.id.call) ;


        webButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String url ="http://www.amazon.com" ;
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url)) ;

                //source: https://developer.android.com/training/basics/intents/sending#java
                String title = getResources().getString(R.string.prompt) ;
                Intent chooser = Intent.createChooser(webIntent, title) ;   //intent for app chooser
                startActivity(chooser) ;
            }
        });

        callButton.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                String phoneNo = "tel:+194912344444" ;
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNo)) ;
                startActivity(callIntent) ;
            }
        });
    }
}