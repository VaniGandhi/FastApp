package com.example.fastapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "fast")
public class FastModel {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name="userid")
    public  int userid;
    @ColumnInfo(name="Fastname")
    public String fastname;
    @ColumnInfo(name="Starttime")
    public  long starttime;
    @ColumnInfo(name="Endtime")
    public  long endtime;
    @ColumnInfo(name="succes")
    public  int sucess;

    @ColumnInfo(name="timeinterval")
    public long timeinterval;

    @ColumnInfo(name="startdate")
    public String startdate;

    public String getStartdate() {
        return startdate;
    }

    @ColumnInfo(name="Badge")
    public int Badge;

    public int getBadge() {
        return Badge;
    }

    public void setBadge(int badge) {
        Badge = badge;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public long getTimeinterval() {
        return timeinterval;
    }

    public void setTimeinterval(long timeinterval) {
        this.timeinterval = timeinterval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getFastname() {
        return fastname;
    }

    public void setFastname(String fastname) {
        this.fastname = fastname;
    }

    public long getStarttime() {
        return starttime;
    }

    public void setStarttime(long starttime) {
        this.starttime = starttime;
    }

    public long getEndtime() {
        return endtime;
    }

    public void setEndtime(long endtime) {
        this.endtime = endtime;
    }

    public int getSucess() {
        return sucess;
    }

    public void setSucess(int sucess) {
        this.sucess = sucess;
    }


}
