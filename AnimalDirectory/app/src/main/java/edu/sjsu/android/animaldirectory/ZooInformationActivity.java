package edu.sjsu.android.animaldirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
//Information activity
public class ZooInformationActivity extends AppCompatActivity {
    private Button callusBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState) ;
        setContentView(R.layout.zoo_information) ;
        //handling button click
        callusBtn = (Button) findViewById(R.id.callus) ;
        callusBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override public void onClick(View v)
            {
                Intent callIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8888888")) ;
                startActivity(callIntent) ;
            }
        }) ;
    }
    //inflating the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu) ;
        return true;
    }
    //handling menu items
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.information:
                Intent information = new Intent(this, ZooInformationActivity.class) ;
                startActivity(information) ;
                return true;

            case R.id.uninstall:
                Uri link = Uri.parse("package:" + getApplicationContext().getPackageName()) ;
                Intent uninstall = new Intent(Intent.ACTION_DELETE, link) ;
                startActivity(uninstall) ;
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}