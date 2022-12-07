package edu.sjsu.android.animaldirectory;


import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
//custom adapter
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<int[]> li;
    private Context context;

    //constructor to initialise myadapter
    public MyAdapter(List<int[]> dataset, Context context) {
        li = dataset;
        this.context = context;
    }

    //getting item count
    @Override
    public int getItemCount() {
        return li.size();
    }
    //setting up view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView name;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            image = (ImageView) v.findViewById(R.id.rowimage);
            name = (TextView) v.findViewById(R.id.rowname);
        }
    }

    //inflating the view
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row, parent, false);


        MyAdapter.ViewHolder vh = new MyAdapter.ViewHolder(v);
        return vh;
    }

    //handling  clicks on viewholder
    @Override
    public void onBindViewHolder(ViewHolder holder,  final int position) {

        final int[] data = li.get(position);
        holder.image.setImageResource(data[0]);
        holder.name.setText(data[1]);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AnimalDetailActivity.class);
                //checking if the selected is last item to make the dialog appear
                if (position != getItemCount() - 1)
                {
                    proceed(intent, data, v);
                } else {
                    makeDialog(intent, data, v);
                }
            }
        });
    }

    //method that performs dialog operation
    private void makeDialog(Intent intent, int[] data, View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(R.string.warn)
                .setPositiveButton(R.string.yes, (dialog, id) -> proceed(intent, data, v))
                .setNegativeButton(R.string.no, (dialog, id) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void proceed(Intent intent, int[] data, View v) {
        intent.putExtra("image", data[0]);
        intent.putExtra("name", data[1]);
        intent.putExtra("description", data[2]);
        v.getContext().startActivity(intent);
    }
}