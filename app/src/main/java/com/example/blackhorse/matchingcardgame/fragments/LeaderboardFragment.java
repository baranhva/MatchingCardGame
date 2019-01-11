package com.example.blackhorse.matchingcardgame.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blackhorse.matchingcardgame.R;
import com.example.blackhorse.matchingcardgame.adapters.GameAdapter;
import com.example.blackhorse.matchingcardgame.models.Game;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {

    private List<Game> mGames;
    private GameAdapter mAdapter;
    private RecyclerView mRecyclerView;

    FirebaseFirestore firebaseDb;


    public LeaderboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Initialize the local variables
        final View view = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        this.mGames = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false));

        // Access a Cloud Firestore instance from your fragment
        firebaseDb = FirebaseFirestore.getInstance();

        firebaseDb.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            mGames.clear();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Game game = document.toObject(Game.class);
                                mGames.add(game);
                                Log.d("LeaderboardFragment", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w("LeaderboardFragment", "Error getting documents.", task.getException());
                        }
                    }
                });

        GameAdapter mAdapter = new GameAdapter(mGames);
        mRecyclerView.setAdapter(mAdapter);

        firebaseDb.collection("scores")
                .orderBy("score", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("LeaderboardFragment", "Listen failed.", e);
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    mGames.add(dc.getDocument().toObject(Game.class));
                                    break;
                                case MODIFIED:
                                    Log.d("LeaderboardFragment", "Modified city: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d("LeaderboardFragment", "Removed city: " + dc.getDocument().getData());
                                    break;
                            }
                        }
                        updateUI();
                    }
                });

        // Inflate the layout for this fragment
        return view;
    }

    private void updateUI() {
        if (mAdapter == null) {
            mAdapter = new GameAdapter(mGames);
            mRecyclerView.setAdapter(mAdapter);
        } else {
        mAdapter.swapList(mGames);
        }
    }

}
