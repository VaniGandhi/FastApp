package com.example.fastapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Insert
    public  void adduser(UserModel userModel);

    @Query("select * from user")
    public List<UserModel> getuser();
    @Query("DELETE FROM user")
    void delete();
}
