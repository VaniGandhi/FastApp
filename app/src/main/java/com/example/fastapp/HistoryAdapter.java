package com.example.fastapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

   private List<FastModel> fastModelList;
   private Context context;

   public HistoryAdapter(Context context, List<FastModel> fastModelList)
   {
       this.context=context;
       this.fastModelList=fastModelList;
   }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recycleritemview,parent,false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       FastModel fastModel=fastModelList.get(position);
       holder.fastid.setText(""+fastModel.getId());
       holder.fastname.setText(""+fastModel.getFastname());
       holder.fastdate.setText(""+fastModel.getStartdate());
       holder.fastbadge.setText(""+fastModel.getBadge());
     if(fastModel.getSucess()==0)
     {
         holder.faststatus.setText("continue");
     }
        if(fastModel.getSucess()==1)
        {
            holder.faststatus.setText("completed");
        }
        if(fastModel.getSucess()==2)
        {
            holder.faststatus.setText("stopped");
        }


    }

    @Override
    public int getItemCount() {
        return fastModelList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {

        public TextView fastid, fastname, fastdate, faststatus, fastbadge;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fastid=itemView.findViewById(R.id.fastid);
            fastname=itemView.findViewById(R.id.fastname);
            fastdate=itemView.findViewById(R.id.fastdate);
            faststatus=itemView.findViewById(R.id.faststatus);
            fastbadge=itemView.findViewById(R.id.badge);

        }
    }


}
