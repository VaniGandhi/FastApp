package com.example.fastapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;


public class SplashScreen extends AppCompatActivity {

    public  static  FastAppDatabase fastAppDatabase;
    String name="vani";
    String fastname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fastAppDatabase= Room.databaseBuilder(getApplicationContext(), FastAppDatabase.class,"fastdb")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();

        UserModel userModel=new UserModel();
        userModel.setName("vani");
        userModel.setEmail("deftsoft16@gmail.com");
        if(ifuserexists())
        {
            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent=new Intent(SplashScreen.this,OptionMenuforFast.class);
                                    intent.putExtra("fastname", fastname);
                                    Toast.makeText(SplashScreen.this, "user exists"+"fast contiue", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);

                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                };
            };
            thread.start();
        }
        else
        {
            fastAppDatabase.userDao().adduser(userModel);

            Thread thread = new Thread(){
                @Override
                public void run() {
                    try {
                        synchronized (this) {
                            wait(500);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Intent intent=new Intent(SplashScreen.this,OptionMenuforFast.class);
                                    Toast.makeText(SplashScreen.this, "user  does not exists", Toast.LENGTH_SHORT).show();
                                    startActivity(intent);

                                }
                            });

                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                };
            };
            thread.start();
        }





    }
    private Boolean ifuserexists( )
    {
        List<UserModel> userModel = fastAppDatabase.userDao().getuser();

        for (UserModel userModel1:userModel) {
            if (userModel1.getName().equalsIgnoreCase(name)) {
                return true;

            }
        }
        return false;
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
}
