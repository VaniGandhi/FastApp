package com.example.fastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class Historyoffast extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    List<FastModel> fastModelList;
    List<FastModel> fastModelList1;
    FastAppDatabase fastAppDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historyoffast);
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(historyAdapter);

        fastAppDatabase = Room.databaseBuilder(getApplicationContext(), FastAppDatabase.class, "fastdb")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        getdatafromdatabase();



    }
   private void getdatafromdatabase()
   {
       fastModelList1=new ArrayList<>();
       fastModelList=new ArrayList<>();
       fastModelList=fastAppDatabase.fastDao().getfastdata();
      for(FastModel fastModel:fastModelList)
      {
          if(fastModel.getSucess()==1||fastModel.getSucess()==2)
          {
              fastModelList1.add(fastModel);
          }
      }



           historyAdapter=new HistoryAdapter(getApplicationContext(), fastModelList1);
           recyclerView.setAdapter(historyAdapter);


        //getdatafromdatabase();
   }

}
