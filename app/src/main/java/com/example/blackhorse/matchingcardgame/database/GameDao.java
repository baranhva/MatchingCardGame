package com.example.blackhorse.matchingcardgame.database;

import com.example.blackhorse.matchingcardgame.models.Game;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface GameDao {

    @Query("SELECT * FROM game")
    public LiveData <List<Game>> getAllGames();
    @Insert
    public void insertGames(Game games);
    @Delete
    public void deleteGames(Game games);
    @Update
    public void updateGames(Game games);

}