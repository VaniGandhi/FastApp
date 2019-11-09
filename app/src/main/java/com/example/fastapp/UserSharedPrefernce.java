package com.example.fastapp;

import android.content.Context;
import android.content.SharedPreferences;

public class UserSharedPrefernce {


    private  String ID="id";
    private  String FASTNAME="fastname";
    private String STARTTIME="starttime";
    private  String ENDTIME="endtime";
    private String SUCESS="sucess";
    private  String USERID="userid";
    private String TIMEINTERVAL="timeinterval";
    private  String STARTDATE="startdate";
    private String BADGE="badge";
    private String COUNTER="counter";
    private String NEWPROGRESS="newprogress";
    private  String OLDPRGRESS="oldprogress";
    private String II="i";



    SharedPreferences sharedPreferences;
    static UserSharedPrefernce userSharedPrefernce = null;

    Context context;

    public UserSharedPrefernce() {

    }

    public UserSharedPrefernce(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

    }

    public static UserSharedPrefernce

    getInstance() {
        if (userSharedPrefernce == null) {
            userSharedPrefernce = new UserSharedPrefernce(BaseApplication.getContext());
        }
        return userSharedPrefernce;
    }
    public void setid(int id) {
        sharedPreferences.edit().putInt(ID, id).apply();
    }

    public int getID() {
        return sharedPreferences.getInt(ID, 0);
    }

    public  void setFASTNAME(String fastname)
    {
        sharedPreferences.edit().putString(FASTNAME,fastname).apply();
    }
    public  String getFASTNAME()
    {
        return  sharedPreferences.getString(FASTNAME,"");

    }
    public  void setSTARTTIME(long starttime)
    {
        sharedPreferences.edit().putLong(STARTTIME,starttime).apply();
    }
    public  long getSTARTTIME()
    {
        return  sharedPreferences.getLong(STARTTIME,0);

    }
    public  void setENDTIME(Long endtime)
    {
        sharedPreferences.edit().putLong(ENDTIME,endtime).apply();
    }
    public  Long getENDTIME()
    {
        return  sharedPreferences.getLong(ENDTIME,0);

    }

    public   void setSUCESS(int sucess)
    {
        sharedPreferences.edit().putInt(SUCESS,sucess).apply();
    }
    public   int getSUCESS()
    {
        return  sharedPreferences.getInt(SUCESS,0);
    }

    public void setUSERID(int userid)
    {
        sharedPreferences.edit().putInt(USERID,userid).apply();
    }
    public  int getUSERID()
    {
        return  sharedPreferences.getInt(USERID,0);
    }

    public void setTIMEINTERVAL(Long timeinterval)
    {
        sharedPreferences.edit().putLong(TIMEINTERVAL,timeinterval).apply();
    }
    public  Long getTIMEINTERVAL()
    {
        return sharedPreferences.getLong(TIMEINTERVAL,0);
    }

    public void setSTARTDATE(String startdate)
    {
        sharedPreferences.edit().putString(STARTDATE,startdate).apply();
    }
    public String getSTARTDATE()
    {
        return sharedPreferences.getString(STARTDATE,"");
    }

    public void setBADGE(int badge)
    {
        sharedPreferences.edit().putInt(BADGE,badge).apply();
    }
    public int getBADGE()
    {
        return  sharedPreferences.getInt(BADGE,0);
    }


    public void setCOUNTER(int counter)
    {
        sharedPreferences.edit().putInt(COUNTER,counter).apply();
    }
    public int getCOUNTER()
    {
        return  sharedPreferences.getInt(COUNTER,0);
    }
        public void setII(int i)
    {
     sharedPreferences.edit().putInt(II,i).apply();
    }

    public int getII()
    {
        return sharedPreferences.getInt(II,0);
    }

}
