package com.example.fastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class OptionMenuforFast extends AppCompatActivity  implements  View.OnClickListener{

    Button oneday, oneweek, fifteendays, onemonth, twentydays;
    ImageView back;
UserSharedPrefernce userSharedPrefernce;
    FastAppDatabase fastAppDatabase;
    private List<UserModel> userModelList;
    private List<FastModel> fastModelList;
    int userid;
    long starttime, endtime, countdowntimer;
    String fastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menufor_fast);
        fastAppDatabase = Room.databaseBuilder(getApplicationContext(), FastAppDatabase.class, "fastdb")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        oneday=findViewById(R.id.oneday);
        oneweek=findViewById(R.id.oneweek);
        fifteendays=findViewById(R.id.fifteenDays);
        onemonth=findViewById(R.id.onemonth);
        twentydays=findViewById(R.id.twentydays);

        userSharedPrefernce=UserSharedPrefernce.getInstance();
        oneday.setOnClickListener(this);
        oneweek.setOnClickListener(this);
        fifteendays.setOnClickListener(this);
        twentydays.setOnClickListener(this);
        onemonth.setOnClickListener(this);
        getuserdata();





    }


   /* private void checkcountdown()
    {
        fastModelList=new ArrayList<>();
        fastModelList=fastAppDatabase.fastDao().getfastdata();
        for(FastModel fastModel:fastModelList)

        {
            if(fastModel.getSucess()==0)
            {
                oneday.setClickable(false);
                oneweek.setClickable(false);
                fifteendays.setClickable(false);
                twentydays.setClickable(false);
                onemonth.setClickable(false);

                Toast.makeText(this, "You are currently on a fast", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(OptionMenuforFast.this, CountdownScreen.class);
                startActivity(intent);
            }

        }
    }*/
    private void getuserdata()
    {
        System.out.println("*****************getuserdataa***********");
        userModelList = new ArrayList<>();

        userModelList = fastAppDatabase.userDao().getuser();
        for (UserModel userModel : userModelList) {
            userid = userModel.getId();

            System.out.println("userid=="+userid);
        }

    }

    private  void setdata(String fastname, Long starttime, Long endtime, long timerinterval, String date, int i)
    {

        if(i==0){
            FastModel fastModel = new FastModel();
            fastModel.setFastname(fastname);
            fastModel.setStarttime(starttime);
            fastModel.setEndtime(endtime);
            fastModel.setSucess(0);
            fastModel.setUserid(userid);
            fastModel.setTimeinterval(timerinterval);
            fastModel.setStartdate(date);


            fastAppDatabase.fastDao().addfast(fastModel);
            Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
            getfastdata();
        }
     else if(i==1)
        {
            userSharedPrefernce.setFASTNAME(fastname);
            userSharedPrefernce.setENDTIME(endtime);
            userSharedPrefernce.setSUCESS(0);
            userSharedPrefernce.setUSERID(userid);
            userSharedPrefernce.setTIMEINTERVAL(timerinterval);
            Intent intent=new Intent(OptionMenuforFast.this, MainActivity.class);
            intent.putExtra("int", 1);
            startActivity(intent);
            Toast.makeText(OptionMenuforFast.this, "Please update and time", Toast.LENGTH_SHORT).show();

        }



    }

    private void getfastdata()
    {  int id = 0;
        fastModelList=new ArrayList<>();
        fastModelList=fastAppDatabase.fastDao().getfastdata();
        for(FastModel fastModel:fastModelList)
        {
          id=fastModel.getId();
            userSharedPrefernce.setid(id);

        }
        System.out.println("id==="+id);
        Intent intent=new Intent(OptionMenuforFast.this, MainActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    private Boolean iffastexists()
    {
        List<FastModel> fastModel=fastAppDatabase.fastDao().getfastdata();
        for(FastModel fastmodel:fastModel)
        {
            if(fastmodel.getSucess()==0)
            {
                fastname=fastmodel.getFastname();
                return  true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        Long Systemtime=System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM ");
        String currentDateandTime = sdf.format(new Date());

        switch (view.getId())
        {
            case R.id.oneday:



              if(iffastexists())
              {
                  Intent intent = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                  String fastname=userSharedPrefernce.getFASTNAME();
                  intent.putExtra("fastname", fastname);
                  startActivity(intent);

              }
              else{
                    Intent intent = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                    intent.putExtra("fastname", "oneday");
                    userSharedPrefernce.setFASTNAME("oneday");
                    startActivity(intent);}







                break;
            case R.id.oneweek:
                if(iffastexists())
                {
                    Intent intent = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                    String fastname=userSharedPrefernce.getFASTNAME();
                    intent.putExtra("fastname", fastname);
                    startActivity(intent);

                }
                else{
                Intent intent1=new Intent(OptionMenuforFast.this, CountdownScreen.class);
                intent1.putExtra("fastname", "oneweek");
                userSharedPrefernce.setFASTNAME("oneweek");
                startActivity(intent1);}

                break;
            case  R.id.fifteenDays:
                if(iffastexists())
                {
                    Intent intent = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                    String fastname=userSharedPrefernce.getFASTNAME();
                    intent.putExtra("fastname", fastname);
                    startActivity(intent);

                }
                else{
                    Intent intent2 = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                    intent2.putExtra("fastname", "fifteendays");
                    userSharedPrefernce.setFASTNAME("fifteendays");
                    startActivity(intent2);}



                break;
            case R.id.twentydays:
                if(iffastexists())
                {
                    Intent intent = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                    String fastname=userSharedPrefernce.getFASTNAME();
                    intent.putExtra("fastname", fastname);
                    startActivity(intent);

                }
                else{
                Intent intent3=new Intent(OptionMenuforFast.this, CountdownScreen.class);
                intent3.putExtra("fastname", "twentydays");
                userSharedPrefernce.setFASTNAME("twentydays");
                startActivity(intent3);}
                break;


            case R.id.onemonth:
                if(iffastexists())
                {
                    Intent intent = new Intent(OptionMenuforFast.this, CountdownScreen.class);
                    String fastname=userSharedPrefernce.getFASTNAME();
                    intent.putExtra("fastname", fastname);
                    startActivity(intent);

                }
                else{
                Intent intent4=new Intent(OptionMenuforFast.this, CountdownScreen.class);
                intent4.putExtra("fastname", "onemonth");
                userSharedPrefernce.setFASTNAME("onemonth");
                startActivity(intent4);}

                break;





        }

    }


}
