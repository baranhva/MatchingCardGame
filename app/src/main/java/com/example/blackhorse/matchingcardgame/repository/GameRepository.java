package com.example.blackhorse.matchingcardgame.repository;

import android.content.Context;

import com.example.blackhorse.matchingcardgame.database.GameDao;
import com.example.blackhorse.matchingcardgame.database.GameDatabase;
import com.example.blackhorse.matchingcardgame.models.Game;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;

public class GameRepository {

    private GameDatabase mGameDatabase;
    private GameDao mGameDao;
    private LiveData<List<Game>> mGames;
    private Executor mExecutor = Executors.newSingleThreadExecutor();

    public GameRepository(Context context) {
        mGameDatabase = GameDatabase.getInstance(context);
        mGameDao = mGameDatabase.gameDao();
        mGames = mGameDao.getAllGames();
    }

    public LiveData<List<Game>> getAllGames() {
        return mGames;
    }

    public void insert(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.insertGames(game);
            }
        });
    }

    public void update(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.updateGames(game);
            }
        });
    }

    public void delete(final Game game) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mGameDao.deleteGames(game);
            }
        });
    }
}
