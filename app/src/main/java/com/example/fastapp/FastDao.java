package com.example.fastapp;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface FastDao {

    @Insert
    public  void addfast(FastModel fastModel);

    @Query("select * from fast")
    public List<FastModel> getfastdata();
    @Query("DELETE FROM fast")
    void delete();
    @Query("UPDATE fast SET succes=:status WHERE id = :id")
    void update(int status, int id);

    @Query("UPDATE fast SET Badge=:badge WHERE id = :id")

    void updatebadge(int badge, int id);


}
