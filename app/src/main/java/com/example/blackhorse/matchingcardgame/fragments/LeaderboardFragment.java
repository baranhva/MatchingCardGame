package com.example.blackhorse.matchingcardgame.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blackhorse.matchingcardgame.R;
import com.example.blackhorse.matchingcardgame.adapters.GameAdapter;
import com.example.blackhorse.matchingcardgame.models.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    private List<Game> mGames;
    private GameAdapter mAdapter;
    private RecyclerView mRecyclerView;


    public LeaderboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Initialize the local variables
        View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        this.mGames = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));
        List<Game> mGames = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            mGames.add(new Game("USERNAME", 500 - (i * i)));
        }
        GameAdapter mAdapter = new GameAdapter(mGames);
        mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return view;
    }

//    private void updateUI() {
//        if (mAdapter == null) {
//            mAdapter = new GameAdapter(mGames);
//            mRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.notifyDataSetChanged();
//        }
//    }

}
