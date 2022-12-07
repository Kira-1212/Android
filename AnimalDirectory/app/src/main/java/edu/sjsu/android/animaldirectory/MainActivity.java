package edu.sjsu.android.animaldirectory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;
//mainactivity
public class MainActivity extends AppCompatActivity {
    //declaring necessary recycler view objects
    private RecyclerView recyclerView ;
    private RecyclerView.Adapter myAdapter ;
    private RecyclerView.LayoutManager layoutManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //attaching the view
        recyclerView = (RecyclerView) findViewById(R.id.recycleView) ;
        recyclerView.setHasFixedSize(true) ;
        //initialising the layout
        layoutManager = new LinearLayoutManager(this) ;
        recyclerView.setLayoutManager(layoutManager) ;
        //calling the custom adapter
        myAdapter = new MyAdapter(createAnimalList(), this) ;
        recyclerView.setAdapter(myAdapter) ;
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
    //method that returns the list
    private List<int[]> createAnimalList()
    {
        List<int[]> li = new ArrayList<>() ;
        li.add(new int[]{R.drawable.lion, R.string.lion, R.string.lionDesc}) ;
        li.add(new int[]{R.drawable.tiger, R.string.tiger, R.string.tigerDesc} ) ;
        li.add(new int[]{R.drawable.liger, R.string.liger, R.string.ligerDesc}) ;
        li.add(new int[]{R.drawable.panther, R.string.panther, R.string.pantherDesc} );
        li.add(new int[]{R.drawable.panda, R.string.panda, R.string.pandaDesc} );


        return li;
    }
}