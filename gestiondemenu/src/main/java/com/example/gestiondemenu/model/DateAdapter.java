package com.example.gestiondemenu.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gestiondemenu.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by DevProsper on 11/01/2018.
 */

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.MyViewHolder> {

    private ArrayList<Evenement> dates;
    private SimpleDateFormat format;
    private DateAdapterCB dateAdapterCB;

    public DateAdapter(ArrayList<Evenement> dates, DateAdapterCB dateAdapterCB) {
        this.dates = dates;
        this.dateAdapterCB = dateAdapterCB;
        format = new SimpleDateFormat("dd/MM/yyyy hh'h'mm");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rows, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Evenement evenement = dates.get(position);

        holder.tv_description.setText(evenement.getDescription());
        holder.tv_date.setText(format.format(evenement.getDate()));

        //Glide.with(holder.tv_date.getContext()).load(evenement.getUrl())
                //error(R.drawable.ic_action_name).placeholder(R.drawable.ic_action_name).into(holder.iv);
        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dateAdapterCB != null){
                    dateAdapterCB.onEvenementClick(evenement, holder);
                }
                Toast.makeText(holder.tv_date.getContext(), evenement.getDescription(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_date, tv_description;
        public View root;
        public ImageView iv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_date = (TextView)itemView.findViewById(R.id.date);
            tv_description = (TextView)itemView.findViewById(R.id.description);
            root = itemView.findViewById(R.id.root);
            iv = (ImageView)itemView.findViewById(R.id.img);
        }
    }

    public interface DateAdapterCB{
        void onEvenementClick(Evenement evenement, MyViewHolder myViewHolder);
    }
}
