package com.example.blackhorse.matchingcardgame.viewmodel;

import android.content.Context;
import com.example.blackhorse.matchingcardgame.models.Game;
import com.example.blackhorse.matchingcardgame.repository.GameRepository;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private GameRepository mRepository;
    private LiveData<List<Game>> mGames;

    public MainViewModel(Context context) {
        mRepository = new GameRepository(context);
        mGames = mRepository.getAllGames();
    }

    public LiveData<List<Game>> getGames() {
        return mGames;
    }
    public void insert(Game game) {
        mRepository.insert(game);
    }
    public void update(Game game) {
        mRepository.update(game);
    }
    public void delete(Game game) {
        mRepository.delete(game);
    }
}