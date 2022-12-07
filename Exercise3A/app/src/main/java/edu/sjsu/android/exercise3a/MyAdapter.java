package edu.sjsu.android.exercise3a;
import java.util.List ;
import androidx.recyclerview.widget.RecyclerView ;
import android.view.LayoutInflater ;
import android.view.View ;
import android.view.View.OnClickListener ;
import android.view.ViewGroup ;
import android.widget.TextView ;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
{
    private List<String> values ;

    public MyAdapter(List<String> myDataset)
    {
        values = myDataset ;
    }

    public void add(int position, String item)
    {
        values.add(position, item) ;
        notifyItemInserted(position) ;
    }

    public void remove(int position)
    {
        values.remove(position) ;
        notifyItemRemoved(position) ;
    }


    @Override public int getItemCount() {return values.size() ; }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView txtHeader ;
        public TextView txtFooter ;
        public View layout ;

        public ViewHolder(View v)
        {
            super(v) ;
            layout = v ;
            txtHeader = (TextView) v.findViewById(R.id.firstLine) ;
            txtFooter = (TextView) v.findViewById(R.id.secondLine) ;
        }
    }


    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        View v = inflater.inflate(R.layout.row_layout, parent, false) ;


        ViewHolder vh = new ViewHolder(v) ;
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        final String name = values.get(position) ;
        holder.txtHeader.setText(name) ;
        holder.txtHeader.setOnClickListener(new OnClickListener()
        {
            @Override public void onClick(View v) {remove(position) ;}
        }) ;
        holder.txtFooter.setText("Footer: " + name) ;
    }
}