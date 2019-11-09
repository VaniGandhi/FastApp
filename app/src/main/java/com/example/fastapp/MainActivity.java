package com.example.fastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button start, stop, update;
    UserSharedPrefernce userSharedPrefernce;
    String fastname, startdate;
    long starttime, endtime, countdowntimer, startingtime, timeinterval;

    int userid, id;
    FastAppDatabase fastAppDatabase;
    private List<UserModel> userModelList;
    private List<FastModel> fastModelList;
    Intent intent;
    Boolean choosen;
    TextView timertext, timepassed2, date ,time;
    CountDownTimer timer, timer2;
    ImageView datepicker, timepicker, fastpicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String chooosendate, chooosentime, choosefast;
    ProgressBar progressBar;
    int pStatus = 0;
  Boolean isstopped=false;
    TextView percentage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startbtn);
        stop=findViewById(R.id.stopbtn);
        timertext = findViewById(R.id.timer);
         timepassed2=findViewById(R.id.time_passed);
         date=findViewById(R.id .textdate);
         time=findViewById(R.id.texttime);
         datepicker=findViewById(R.id.choosedate);
         timepicker=findViewById(R.id.choosetime);
         update=findViewById(R.id.update);
         progressBar=findViewById(R.id.circularProgressbar);
         percentage=findViewById(R.id.progresspercentage);
         progressBar.setMax(100);


        fastAppDatabase = Room.databaseBuilder(getApplicationContext(), FastAppDatabase.class, "fastdb")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        String currentDBPath = getDatabasePath("fastdb").getAbsolutePath();
        System.out.println("currentdbpath"+currentDBPath);

        userSharedPrefernce = UserSharedPrefernce.getInstance();

            getfastdatafromdatabase();

        progressBar.setProgress(0);
        percentage.setText("0%");


        intent = getIntent();
        choosen = intent.getBooleanExtra("choosen", true);



        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OptionMenuforFast.class);
                startActivity(intent);
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                stop();
                timer.cancel();



            }
        });

        intent=getIntent();
        int i=intent.getIntExtra("int",0);
        if(i==1)
        {
            updatesetdataindatabase();
        }



        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datepicker();
            }
        });
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timepicker();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
        private void datepicker()
        {
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                           // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            chooosendate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                            date.setText(chooosendate);
                            userSharedPrefernce.setSTARTDATE(chooosendate);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }

        private void timepicker()
        {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                         //   txtTime.setText(hourOfDay + ":" + minute);
                            chooosentime=hourOfDay + ":" + minute;
                            time.setText(chooosentime);

                            long Startime = c.getTimeInMillis();
                            userSharedPrefernce.setSTARTTIME(Startime);


                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }
    private void stop()


    {    isstopped=true;
        timertext.setText("0:0:0:0");
        timepassed2.setText("0:0:0:0");
        progressBar.setProgress(0);
        percentage.setText("0%");

        fastModelList = new ArrayList<>();
        fastModelList = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastModel : fastModelList) {
            int id=fastModel.getId();
            int id1=userSharedPrefernce.getID();
            System.out.println("id=="+id+"id1=="+id1);
            if (id == id1) {
            if(fastModel.getSucess()==0) {

                   fastModel.setSucess(2);
                   timer.cancel();
                   fastAppDatabase.fastDao().update(2, id);

                }
            }


        }


    }


    private void getfastdatafromdatabase() {

        fastModelList = new ArrayList<>();
        fastModelList = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastModel : fastModelList) {
            int id=fastModel.getId();
            int id1=userSharedPrefernce.getID();
            System.out.println("id=="+id+"id1=="+id1);
           if(fastModel.getSucess()==0) {
               if (id == id1) {
                   countdowntimer = fastModel.getEndtime();
                   startingtime=fastModel.getStarttime();
                   timeinterval=fastModel.getTimeinterval();
                   startdate=fastModel.getStartdate();
                   startcountdowntimer(countdowntimer, startingtime, timeinterval);
                   setdateandtime(startdate, startingtime);
               }


           }


        }





    }

    private void setdateandtime(String textdate, long texttime)
    {
        date.setText(textdate);


        int seconds = (int) (texttime / 1000) % 60;
        int minutes = (int) (texttime / (1000 * 60)) % 60;
        int hours = (int) (texttime / (1000 * 60 * 60)) % 24;

        String text = hours + " : " + minutes + " : " + seconds;
        time.setText(text);
    }

    private  void timepassedcountdowntime(long starttime, long timeinterval)
    {
        long curretime=System.currentTimeMillis();
        final long timepassed=curretime-starttime;

                int seconds = (int) (timepassed / 1000) % 60;
                int minutes = (int) (timepassed / (1000 * 60)) % 60;
                int hours = (int) (timepassed / (1000 * 60 * 60)) % 24;
                int Days =(int) (timepassed / (24 * 60 * 60 * 1000));
                String text =Days+" : "+ hours + " : " + minutes + " : " + seconds;
                timepassed2.setText(text);
                showprogress(timepassed, timeinterval);













    }

    private void startcountdowntimer(final long time, final long starttime, final long timeinterval) {


        long currentime = System.currentTimeMillis();
        final long lefttime = time - currentime;
        if (!isstopped) {
            timer = new CountDownTimer(lefttime, 1000) {
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) (millisUntilFinished / (1000 * 60)) % 60;
                    int hours = (int) (millisUntilFinished / (1000 * 60 * 60)) % 24;
                    int Days = (int) (millisUntilFinished / (24 * 60 * 60 * 1000));
                    String text = Days + " : " + hours + " : " + minutes + " : " + seconds;
                    timertext.setText(text);
                    //      checktimer();
                    timepassedcountdowntime(starttime, timeinterval);


                }


                @Override
                public void onFinish() {
                    progressBar.setProgress(100);
                    percentage.setText("100%");
                    checktimer();


                }


            }.start();

        }
    }


    private  void updatesetdataindatabase()
    {


        if(userSharedPrefernce.getSTARTDATE()!=null)
        {
            if(chooosentime!=null)
            {
                int userid=userSharedPrefernce.getUSERID();
                String fastname=userSharedPrefernce.getFASTNAME();
                Long starttime=userSharedPrefernce.getSTARTTIME();
                Long endtime=userSharedPrefernce.getENDTIME();
                int sucess=userSharedPrefernce.getSUCESS();
                Long timeinterval=userSharedPrefernce.getTIMEINTERVAL();
                String startdate=userSharedPrefernce.getSTARTDATE();

                FastModel fastModel=new FastModel();
                fastModel.setUserid(userid);
                fastModel.setStartdate(startdate);
                fastModel.setStarttime(starttime);
                fastModel.setEndtime(endtime);
                fastModel.setTimeinterval(timeinterval);
                fastModel.setFastname(fastname);
                fastModel.setSucess(sucess);
                fastAppDatabase.fastDao().addfast(fastModel);
                getfastdata(endtime,starttime, timeinterval);

            }
            else
            {
                Toast.makeText(this, "Please choose time", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(this, "Please choose date", Toast.LENGTH_SHORT).show();
        }
       /* String textdate=date.getText().toString();
        Long texttime=startingtime;


        if(!TextUtils.isEmpty(textdate)&&texttime!=null)
        {
            userSharedPrefernce.setSTARTTIME(texttime);
            userSharedPrefernce.setSTARTDATE(textdate);
        }
        int id=userSharedPrefernce.getID();
        int userid=userSharedPrefernce.getUSERID();
        String fastname=userSharedPrefernce.getFASTNAME();
        Long starttime=userSharedPrefernce.getSTARTTIME();
        Long endtime=userSharedPrefernce.getENDTIME();
        int sucess=userSharedPrefernce.getSUCESS();
        Long timeinterval=userSharedPrefernce.getTIMEINTERVAL();
        String startdate=userSharedPrefernce.getSTARTDATE();*/








    }
    private void getfastdata(final long time, final long starttime, final long timeinterval)
    {  int id = 0;
        fastModelList=new ArrayList<>();
        fastModelList=fastAppDatabase.fastDao().getfastdata();
        for(FastModel fastModel:fastModelList)
        {
            id=fastModel.getId();
            userSharedPrefernce.setid(id);

        }
        System.out.println("id==="+id);
        startcountdowntimer(time,starttime, timeinterval);




    }


        private  void showprogress(long timepassed, long timeinterval)

        {
             long progress=(timepassed*100)/timeinterval;
            progressBar.setProgress((int) progress);
            percentage.setText(progress+"%");




        }



        private  void checktimer()
    {
        if(timertext.getText().equals("0 : 0 : 0 : 0"))
        {
            Toast.makeText(this, "ended", Toast.LENGTH_SHORT).show();
            fastModelList = new ArrayList<>();
            fastModelList = fastAppDatabase.fastDao().getfastdata();
            for (FastModel fastModel : fastModelList) {
                int id=fastModel.getId();
                int id1=userSharedPrefernce.getID();
                System.out.println("id=="+id+"id1=="+id1);
                if(fastModel.getSucess()==0) {
                    if (id == id1) {
                    fastModel.setSucess(1);
                        System.out.println("fastid=="+fastModel.getId()+"fastname=="+fastModel.getFastname()+
                                "fastsucess=="+fastModel.getSucess());
                        fastAppDatabase.fastDao().update(1,id);
                        progressBar.setProgress(0);
                        percentage.setText("100%");
                    }
                }


            }

         }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getfastdatafromdatabase();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getfastdatafromdatabase();
    }

    @Override
    public void onBackPressed() {
         Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.History:
               Intent intent=new Intent(MainActivity.this, Historyoffast.class);
               startActivity(intent);
               break;
            case R.id.clear_database:
                fastAppDatabase.userDao().delete();
                fastAppDatabase.fastDao().delete();

        }
        return  false;
    }


}

