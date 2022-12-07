package edu.sjsu.android.animaldirectory;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
//details activity
public class AnimalDetailActivity extends AppCompatActivity
{
    private ImageView imageView ;
    private TextView nameView ;
    private TextView descriptionView ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.animal_detail) ;

        //fetching the intent from viewholder click
        Intent intent = getIntent() ;
        Bundle extras = intent.getExtras() ;
        int image = extras.getInt("image") ;
        int name = extras.getInt("name") ;
        int description = extras.getInt("description") ;

        imageView = (ImageView) findViewById(R.id.animalimage) ;
        nameView = (TextView) findViewById(R.id.animalname) ;
        descriptionView = (TextView) findViewById(R.id.animaldesc) ;

        imageView.setImageResource(image) ;
        nameView.setText(name) ;
        descriptionView.setText(description) ;
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
