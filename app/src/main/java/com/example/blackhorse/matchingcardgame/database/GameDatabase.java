package com.example.blackhorse.matchingcardgame.database;

import com.example.blackhorse.matchingcardgame.models.Game;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Game.class}, version = 1)
public abstract  class GameDatabase extends RoomDatabase {

    public abstract GameDao gameDao();

    private final static String NAME_DATABASE = "game_db";

    //Static instance
    private static GameDatabase sInstance;

    public static GameDatabase getInstance(Context context) {
        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context, GameDatabase.class,   NAME_DATABASE).build();
        }
        return sInstance;
    }
}

