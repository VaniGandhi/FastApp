package com.example.fastapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

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


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CountdownScreen extends AppCompatActivity implements View.OnClickListener {


    int userid, id;
    FastAppDatabase fastAppDatabase;
    private List<UserModel> userModelList;
    private List<FastModel> fastModelList;
    Intent intent;
    Boolean choosen;
    TextView timertext, timepassed2, date, time, fastnameontop;
    CountDownTimer timer, timer2;
    ImageView datepicker, timepicker, fastpicker;
    private int mYear, mMonth, mDay, mHour, mMinute;
    String chooosendate, chooosentime, choosefast;
    ProgressBar progressBar;
    int pStatus = 0;
    Boolean isstopped = false;
    TextView percentage;
    UserSharedPrefernce userSharedPrefernce;
    Button start, stop, update;
    String fastname;

    long  countdowntimer, startingtime, timeinterval;
    String startdate;
    long updatedstarttime;
    Calendar calendar;
    int counter;
   boolean isupdated=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.startbtn);
        stop = findViewById(R.id.stopbtn);
        timertext = findViewById(R.id.timer);
        timepassed2 = findViewById(R.id.time_passed);
        date = findViewById(R.id.textdate);
        time = findViewById(R.id.texttime);
        datepicker = findViewById(R.id.choosedate);
        timepicker = findViewById(R.id.choosetime);
        update = findViewById(R.id.update);
        progressBar = findViewById(R.id.circularProgressbar);
        percentage = findViewById(R.id.progresspercentage);
        fastnameontop = findViewById(R.id.fastnameontop);
        calendar = Calendar.getInstance();


        fastAppDatabase = Room.databaseBuilder(getApplicationContext(), FastAppDatabase.class, "fastdb")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        String currentDBPath = getDatabasePath("fastdb").getAbsolutePath();
        System.out.println("currentdbpath" + currentDBPath);

        userSharedPrefernce = UserSharedPrefernce.getInstance();
        intent = getIntent();
        fastname = intent.getStringExtra("fastname");
        percentage.setText("0%");


        getintent(fastname);
        getuserdata();
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        datepicker.setOnClickListener(this);
        timepicker.setOnClickListener(this);
        update.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.startbtn:
                if (iffastexists()) {
                     Toast.makeText(this, "You are already on a fast", Toast.LENGTH_SHORT).show();
                } else {


                        startfast();


                   // startfast();
                }

                break;
            case R.id.stopbtn:
                if (iffastexists()) {
                    stop();
                } else {
                      Toast.makeText(this, "There is no fast to be stopped.", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.choosedate:

                String datetext = date.getText().toString();
                if (iffastexists()) {
                    // Toast.makeText(this, "You are already on a fast", Toast.LENGTH_SHORT).show();
                } else {
                    if (iffastexists()) {
                        if (!datetext.equalsIgnoreCase("00/00/00")) {
                            System.out.println("datetext==" + date.getText());
                            //  Toast.makeText(this, "You are already on a fast", Toast.LENGTH_SHORT).show();
                        } else {
                            datepicker();
                        }
                    } else {
                        datepicker();
                    }
                }

                break;
            case R.id.choosetime:
                String timetext = time.getText().toString();
                if (iffastexists()) {
                    // Toast.makeText(this, "You are already on a fast", Toast.LENGTH_SHORT).show();
                } else {

                    if (iffastexists()) {
                        if (!timetext.equalsIgnoreCase("00:00")) {
                            System.out.println("timetext==" + time.getText());
                            //   Toast.makeText(this, "You are already on a fast", Toast.LENGTH_SHORT).show();
                        } else {
                            timepicker();
                        }
                    } else {
                        timepicker();
                    }


                }

                break;
            case R.id.update:
                isupdated=true;


                if (iffastexists()) {
                    Toast.makeText(this, "You are already on a fast" +
                            "", Toast.LENGTH_SHORT).show();
                } else {


                    updatetime();

                }

        }

    }

    private void datepicker() {
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
                        chooosendate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        long date1=calendar.getTimeInMillis();
                        if(date1>System.currentTimeMillis())
                        {
                            Toast.makeText(CountdownScreen.this, "Please select correct date.", Toast.LENGTH_SHORT).show();
                            date.setText("00/00/00");
                            chooosendate = null;
                        }
                        else
                        {
                            date.setText("00/00/00");
                        }
                        date.setText(chooosendate);


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    private void timepicker() {
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        final TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {


                        chooosentime   = hourOfDay + ":" + minute;
                        time.setText(chooosentime);

                        calendar.set(Calendar.HOUR, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        updatedstarttime = calendar.getTimeInMillis() ;
                        if (updatedstarttime > System.currentTimeMillis()) {
                            Toast.makeText(CountdownScreen.this, "Please select correct date.", Toast.LENGTH_SHORT).show();
                            date.setText("00/00/00");
                            time.setText("00:00");
                            updatedstarttime = 0;
                            chooosentime = null;
                            chooosendate = null;//-43200000;
                        }


                        System.out.println("cccc" + updatedstarttime + "currenttimer" + System.currentTimeMillis());
                        userSharedPrefernce.setSTARTTIME(updatedstarttime);


                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private void getfastdateandtime() {
        DateFormat df = new SimpleDateFormat("HH:mm:ss");
        List<FastModel> fastModel = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastModel1 : fastModel) {
            if (fastModel1.getSucess() == 0) {
                String Date = fastModel1.getStartdate();
                long starttime = fastModel1.getStarttime();

                long millis = starttime;
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(millis);
                System.out.println("Milliseconds to Date using Calendar:"
                        + df.format(cal.getTime()));
                String Time = df.format(cal.getTime());


                time.setText("" + Time);
                date.setText(Date);
                System.out.println("hms===" + Time + "  " + "mills===" + millis + " " + "startime" + starttime);


            }
        }

    }


    private void updatetime() {

        if (chooosendate != null) {
            if (chooosentime != null) {
                if (fastname != null) {

                    userSharedPrefernce.setSTARTTIME(updatedstarttime);
                    userSharedPrefernce.setSTARTDATE(chooosendate);
                    userSharedPrefernce.setFASTNAME(fastname);
                    System.out.println("starttime==" + userSharedPrefernce.getSTARTTIME() + "" +
                            "   " + "startdate==" + userSharedPrefernce.getSTARTDATE());

                } else {
                    Toast.makeText(this, "Please choose a fast firstly", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Please choose time", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please choose date", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean iffastexists() {
        List<FastModel> fastModel = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastmodel : fastModel) {
            if (fastmodel.getSucess() == 0) {
                return true;
            }
        }
        return false;
    }

    private void getuserdata() {
        System.out.println("*****************getuserdataa***********");
        userModelList = new ArrayList<>();

        userModelList = fastAppDatabase.userDao().getuser();
        for (UserModel userModel : userModelList) {
            userid = userModel.getId();

            System.out.println("userid==" + userid);
        }

    }

    private void getintent(String fastname) {


        if (fastname.equals("oneday")) {

            System.out.println("oneday");
            fastnameontop.setText("One day");

        }
        if (fastname.equals("oneweek")) {
            fastnameontop.setText("One week");

        }
        if (fastname.equals("fifteendays")) {
            fastnameontop.setText("Fifteen days");
        }
        if (fastname.equals("twentydays")) {
            fastnameontop.setText("Twenty days");
        }
        if (fastname.equals("onemonth")) {
            fastnameontop.setText("One month");
        }
    }


    private void startfast() {
        Long Systemtime = null, endtime = null;
        int i;
        long updatedfasttime = userSharedPrefernce.getSTARTTIME();
        String updatedfastdate = userSharedPrefernce.getSTARTDATE(), date = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/YYYY ");

        if (fastname.equals("oneday")) {

            if ((updatedfastdate != null) && (updatedfasttime != 0)) {
                Systemtime = userSharedPrefernce.getSTARTTIME();
                date = userSharedPrefernce.getSTARTDATE();
                endtime = Systemtime + 120000;//86400000

                i = 2;
            } else {
                Systemtime = System.currentTimeMillis();
                date = sdf.format(new Date());
                endtime = Systemtime + 120000;

                i = 1;

            }

            setdata("oneday", Systemtime, endtime, 120000, date, i);

        }
        if (fastname.equals("oneweek")) {

            if ((updatedfastdate != null) && (updatedfasttime != 0)) {
                Systemtime = userSharedPrefernce.getSTARTTIME();
                date = userSharedPrefernce.getSTARTDATE();
                endtime = Systemtime + 180000;

                i = 2;
            } else {
                Systemtime = System.currentTimeMillis();
                date = sdf.format(new Date());
                endtime = Systemtime + 180000;
                Toast.makeText(this, "normalfast", Toast.LENGTH_SHORT).show();
                i = 1;

            }

            setdata("oneweek", Systemtime, endtime, 180000, date, i);
        }
        if (fastname.equals("fifteendays")) {
            if ((updatedfastdate != null) && (updatedfasttime != 0)) {
                Systemtime = userSharedPrefernce.getSTARTTIME();
                date = userSharedPrefernce.getSTARTDATE();
                endtime = Systemtime + 1296000000;

                i = 2;
            } else {
                Systemtime = System.currentTimeMillis();
                date = sdf.format(new Date());
                endtime = Systemtime + 1296000000;

                i = 1;

            }

            setdata("fifteendays", Systemtime, endtime, 1296000000, date, i);
        }
        if (fastname.equals("twentydays")) {
            if ((updatedfastdate != null) && (updatedfasttime != 0)) {
                Systemtime = userSharedPrefernce.getSTARTTIME();
                date = userSharedPrefernce.getSTARTDATE();
                endtime = Systemtime + 1728000000;
                i = 2;
            } else {
                Systemtime = System.currentTimeMillis();
                date = sdf.format(new Date());
                endtime = Systemtime + 1728000000;
                i = 1;

            }
            setdata("twentydays", Systemtime, endtime, 1728000000, date, i);
        }
        if (fastname.equals("onemonth")) {
            if ((updatedfastdate != null) && (updatedfasttime != 0)) {
                Systemtime = userSharedPrefernce.getSTARTTIME();
                date = userSharedPrefernce.getSTARTDATE();
                endtime = Systemtime + 10000;
                i = 2;
            } else {
                Systemtime = System.currentTimeMillis();
                date = sdf.format(new Date());
                endtime = Systemtime + 10000;
                i = 1;

            }

            setdata("onemonth", Systemtime, endtime, 10000, date, i);
        }

    }

    private void setdata(String fastname, long starttime, long endtime, long timeinterval, String date, int i) {

        int badge = userSharedPrefernce.getBADGE();
        System.out.println("badge" + userSharedPrefernce.getBADGE());
        FastModel fastModel = new FastModel();
        fastModel.setFastname(fastname);
        fastModel.setStarttime(starttime);
        fastModel.setEndtime(endtime);
        fastModel.setSucess(0);
        fastModel.setUserid(userid);
        fastModel.setTimeinterval(timeinterval);
        fastModel.setStartdate(date);
        fastModel.setBadge(badge);
        userSharedPrefernce.setFASTNAME(fastname);
        userSharedPrefernce.setSTARTTIME(starttime);
        userSharedPrefernce.setENDTIME(endtime);
        userSharedPrefernce.setTIMEINTERVAL(timeinterval);


        fastAppDatabase.fastDao().addfast(fastModel);
        getfastdata(i);
        System.out.println("endtime-----" + endtime);
        System.out.println("starttime-----" + starttime);
    }

    private void getfastdata(int i) {
        int id = 0;
        fastModelList = new ArrayList<>();
        fastModelList = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastModel : fastModelList) {
            id = fastModel.getId();
            userSharedPrefernce.setid(id);
            getfastdatafromdatabase(i);

        }
        System.out.println("id===" + id);


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (iffastexists()) {

            int i=userSharedPrefernce.getII();

            getfastdatafromdatabase(i);
            getfastdateandtime();
        }
    }

    private void getfastdatafromdatabase(int i) {

        fastModelList = new ArrayList<>();
        fastModelList = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastModel : fastModelList) {
            int id = fastModel.getId();
            int id1 = userSharedPrefernce.getID();
          //  System.out.println("id==" + id + "id1==" + id1);
            if (fastModel.getSucess() == 0) {
                if (id == id1) {
                    countdowntimer = fastModel.getEndtime();
                    startingtime = fastModel.getStarttime();
                    timeinterval = fastModel.getTimeinterval();
                    startdate = fastModel.getStartdate();
                    startcountdowntimer(countdowntimer, startingtime, timeinterval, i);
                    getfastdateandtime();

                }
            } }
    }

    private void startcountdowntimer(final long time1, final long starttime, final long timeinterval, int i) {

        long lefttime = 0;
        final long currentime = System.currentTimeMillis();

        if(timer!=null)
        {
            timer.cancel();
        }


        if (i == 1) {
            userSharedPrefernce.setII(1);

            if(time1>currentime )
            {
                if(currentime<starttime)
                {
                    Toast.makeText(this, "vdfsg", Toast.LENGTH_SHORT).show();
                            isupdated=true;
                       stop();

                      /* progressBar.setProgress(0);
                       timertext.setText("0 : 0 : 0 : 0");
                       timepassed2.setText("0 : 0 : 0 : 0");*/

                }else{

                lefttime = time1 - currentime;}

            }
            else
            {
                if(timer!=null){
                timer.cancel();}
            }



        }
        if (i == 2) {
            userSharedPrefernce.setII(2);

            if(time1>currentime )
            {

                if(currentime<starttime) {

                    stop();
                }

                else {
                    long caltime = currentime - starttime;
                    lefttime = timeinterval - caltime;

                }

            }
            else
            {
                if(timer!=null){
                timer.cancel();}
            }




        }

        if (i == 0) {
            userSharedPrefernce.setII(0);

            if (time1>currentime ){

                if(currentime<starttime){
                    stop();
                }
                else{
                lefttime = time1 - currentime;}
            }else{
                if(timer!=null)
                {
                timer.cancel();}
            }

        }

        System.out.println(i+"<----CountdownScreen.startcountdowntimer---->"+lefttime);


        timer = new CountDownTimer(lefttime, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000) % 60;
                int minutes = (int) (millisUntilFinished / (1000 * 60)) % 60;
                int hours = (int) (millisUntilFinished / (1000 * 60 * 60)) % 24;
                int Days = (int) (millisUntilFinished / (24 * 60 * 60 * 1000));
                String text = Days + " : " + hours + " : " + minutes + " : " + seconds;
                timertext.setText(text);
                //      checktimer();
                checktimer();
                timepassedcountdowntime(starttime, timeinterval);


            }


            @Override
            public void onFinish() {


                progressBar.setProgress(100);

                timertext.setText("0 : 0 : 0 : 0");
                if(!isupdated) {
                    progressBar.setProgress(100);
                    if (fastname.equals("oneday")) {
                        timepassed2.setText("0 : 0 : 2 : 0");
                        progressBar.setProgress(100);
                    }
                    if (fastname.equals("oneweek")) {
                        timepassed2.setText("0 : 0 : 3 : 0");
                        progressBar.setProgress(100);
                    }
                    if (fastname.equals("fifteendays")) {
                        timepassed2.setText("14 : 23 : 59 : 59");
                        progressBar.setProgress(100);
                    }
                    if (fastname.equals("twentydays")) {
                        timepassed2.setText("19 : 23 : 59 : 59");
                        progressBar.setProgress(100);
                    }
                    if (fastname.equals("onemonth")) {
                        timepassed2.setText("0 : 0 : 0 : 10");
                        progressBar.setProgress(100);
                    }

                    userSharedPrefernce.setSTARTDATE(null);
                    userSharedPrefernce.setSTARTTIME(0);
                    date.setText("00/00/00");
                    time.setText("00:00");
                    checktimer();
                }else
                {
                    progressBar.setProgress(0);
                    timertext.setText("0 : 0 : 0 : 0");
                    timepassed2.setText("0 : 0 : 0 : 0");

                }


            }



        }.start();


    }



    private void timepassedcountdowntime(long starttime, long timeinterval) {
        long curretime = System.currentTimeMillis();
        final long timepassed = curretime - starttime + 1000;

        int seconds = (int) (timepassed / 1000) % 60;
        int minutes = (int) (timepassed / (1000 * 60)) % 60;
        int hours = (int) (timepassed / (1000 * 60 * 60)) % 24;
        int Days = (int) (timepassed / (24 * 60 * 60 * 1000));
        String text = Days + " : " + hours + " : " + minutes + " : " + seconds;
        timepassed2.setText(text);
        showprogress(timepassed, timeinterval);


    }

    private void checktimer() {
        if (timertext.getText().equals("0 : 0 : 0 : 0")) {

            progressBar.setProgress(100);
            percentage.setText("100%");
            fastModelList = new ArrayList<>();
            fastModelList = fastAppDatabase.fastDao().getfastdata();
            for (FastModel fastModel : fastModelList) {
                int id = fastModel.getId();
                int id1 = userSharedPrefernce.getID();
                System.out.println("id==" + id + "id1==" + id1);
                if (fastModel.getSucess() == 0) {
                    if (id == id1) {
                        fastModel.setSucess(1);
                        System.out.println("fastid==" + fastModel.getId() + "fastname==" + fastModel.getFastname() +
                                "fastsucess==" + fastModel.getSucess());
                        fastAppDatabase.fastDao().update(1, id);
                        int count = userSharedPrefernce.getCOUNTER();

                        counter = count + 1;
                        userSharedPrefernce.setCOUNTER(counter);
                        System.out.println("counter===" + counter);

                        if (counter % 5 == 0) {
                            int badge = fastModel.getBadge();
                            int newbadge = badge + 1;
                            userSharedPrefernce.setBADGE(newbadge);
                            System.out.println("badge2" + userSharedPrefernce.getBADGE());
                            fastAppDatabase.fastDao().updatebadge(badge + 1, id);

                        }
                        percentage.setText("100%");
                    }
                }


            }

        }
    }

    private void showprogress(long timepassed, long timeinterval) {


        // long progress= (timepassed*100)/timeinterval;

        float newtimepassed = Float.valueOf(timepassed);
        float newtimeinterval = Float.valueOf(timeinterval);
        float progress1 = (newtimepassed * 100) / newtimeinterval;


        progressBar.setProgress(Math.round(progress1));


        // progressBar.setProgress(int(progress1));


        String s = String.format("%.2f", progress1);
        percentage.setText(s);
     /*   System.out.println("Progress== " + progress1);
        System.out.println("Progress1==" + (int) progress1);*/


    }

    @Override
    protected void onResume() {
        super.onResume();
   /*  String fastname=userSharedPrefernce.getFASTNAME();
     long endtime=userSharedPrefernce.getENDTIME();
     long starttime=userSharedPrefernce.getSTARTTIME();
     int i=userSharedPrefernce.getII();
     long timeinterval=userSharedPrefernce.getTIMEINTERVAL();
     startcountdowntimer(endtime,starttime,timeinterval,i);*/

    }

    private void stop() {

        timer.cancel();
        userSharedPrefernce.setCOUNTER(0);
        counter = 0;
        isstopped = true;
        timertext.setText("0:0:0:0");
        timepassed2.setText("0:0:0:0");
        progressBar.setProgress(0);
        percentage.setText("0%");
        userSharedPrefernce.setSTARTTIME(0);
        userSharedPrefernce.setENDTIME(Long.valueOf(0));
        userSharedPrefernce.setTIMEINTERVAL(Long.valueOf(0));
        userSharedPrefernce.setFASTNAME(null);
        userSharedPrefernce.setSTARTDATE(null);
        userSharedPrefernce.setSUCESS(2);
        time.setText("00:00");
        date.setText("00/00/00");

        fastModelList = fastAppDatabase.fastDao().getfastdata();
        for (FastModel fastModel : fastModelList) {
            int id = fastModel.getId();
            int id1 = userSharedPrefernce.getID();
            System.out.println("id==" + id + "id1==" + id1);
            if (id == id1) {
                if (fastModel.getSucess() == 0) {

                    fastModel.setSucess(2);
                    timer.cancel();
                    fastAppDatabase.fastDao().update(2, id);

                }
            }


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.History:
                Intent intent = new Intent(CountdownScreen.this, Historyoffast.class);
                startActivity(intent);
                break;
            case R.id.clear_database:
             fastAppDatabase.userDao().delete();
            fastAppDatabase.fastDao().delete();

        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
