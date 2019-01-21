package com.example.blackhorse.matchingcardgame.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import nl.dionsegijn.konfetti.Confetti;
import nl.dionsegijn.konfetti.KonfettiView;
import nl.dionsegijn.konfetti.models.Shape;
import nl.dionsegijn.konfetti.models.Size;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhorse.matchingcardgame.R;
import com.example.blackhorse.matchingcardgame.activities.SettingActivity;
import com.example.blackhorse.matchingcardgame.database.GameDatabase;
import com.example.blackhorse.matchingcardgame.models.Game;
import com.example.blackhorse.matchingcardgame.viewmodel.MainViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private ImageView life1, life2, life3;
    private TextView yourScore;
    private Button addPoint, takePoint, addLife, takeLife, save, settings;
    private TextInputLayout yourName;
    private Chronometer chronometer;
    private KonfettiView viewKonfetti;
    private MainViewModel mMainViewModel;
    private FirebaseFirestore firebaseDb;

    private int countScore = 0;
    private int countLife = 3;

    public GameFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        life1 = view.findViewById(R.id.life1);
        life2 = view.findViewById(R.id.life2);
        life3 = view.findViewById(R.id.life3);
        yourScore = view.findViewById(R.id.yourScore);
        addPoint = view.findViewById(R.id.addPoint);
        takePoint = view.findViewById(R.id.takePoint);
        addLife = view.findViewById(R.id.addLife);
        takeLife = view.findViewById(R.id.takeLife);
        save = view.findViewById(R.id.save);
        settings = view.findViewById(R.id.settings);
        yourName = view.findViewById(R.id.yourName);
        yourScore.setText(String.valueOf(countScore));
        chronometer = view.findViewById(R.id.chronometer);
        viewKonfetti = view.findViewById(R.id.viewKonfetti);

        final MediaPlayer coin_up_sound = MediaPlayer.create(view.getContext(), R.raw.coin_up_sound);
        final MediaPlayer coin_down_sound = MediaPlayer.create(view.getContext(), R.raw.coin_down_sound);
        final MediaPlayer heart_down_sound = MediaPlayer.create(view.getContext(), R.raw.heart_down_sound);
        final MediaPlayer heart_up_sound = MediaPlayer.create(view.getContext(), R.raw.heart_up_sound);

        mMainViewModel = new MainViewModel(view.getContext());

        mMainViewModel.getGames().observe(this, new Observer<List<Game>>() {

            @Override
            public void onChanged(@Nullable List<Game> games) {
                if (!games.isEmpty()) {
                    yourName.getEditText().setText(games.get(games.size() - 1).getUserName());
                    countScore = games.get(games.size() - 1).getScore();
                    yourScore.setText(String.valueOf(countScore));
                    countLife = games.get(games.size() - 1).getHeart();
                    updateLife(countLife);
                }
            }
        });

        // Access a Cloud Firestore instance from your fragment
        firebaseDb = FirebaseFirestore.getInstance();

        // start game with 3 hearts
        updateLife(countLife);

        // When clicked on settings button, open the settings activity
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = yourName.getEditText().getText().toString();
                Game game = new Game(name, countScore, countLife);
                Snackbar snackbar = Snackbar.make(view, "game saved", 1000);
                snackbar.show();

                heart_up_sound.start();

                viewKonfetti.build()
                        .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                        .setDirection(0.0, 359.0)
                        .setSpeed(1f, 5f)
                        .setFadeOutEnabled(true)
                        .setTimeToLive(2000L)
                        .addShapes(Shape.RECT, Shape.CIRCLE)
                        .addSizes(new Size(12, 5))
                        .setPosition(viewKonfetti.getX() + viewKonfetti.getWidth() / 2, viewKonfetti.getY() + viewKonfetti.getHeight() / 2)
                        .burst(100);

                // insert in room database
//                new GameAsyncTask(TASK_INSERT_GAME).execute(game);
                mMainViewModel.insert(game);

                // Insert in firebase. adds a new document with a generated ID
                firebaseDb.collection("scores")
                        .add(game)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d("Gamefragment", "DocumentSnapshot added with ID: " + documentReference.getId());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("Gamefragment", "Error adding document", e);
                            }
                        });
            }
        });

        //start timer, it start from 00:00
        chronometer.start();

        addLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                life1.setVisibility(View.INVISIBLE);

                // dont count and play a sound when life is already 3
                if (countLife < 3) {
                    coin_up_sound.stop();
                    coin_down_sound.stop();
                    heart_down_sound.stop();
                    heart_up_sound.stop();

                    countLife++;
                    heart_up_sound.start();
                }
                updateLife(countLife);

            }
        });

        // dont count down and play sound when life is already 0
        takeLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countLife > 0) {
                    coin_up_sound.stop();
                    coin_down_sound.stop();
                    heart_down_sound.stop();
                    heart_up_sound.stop();

                    countLife--;
                    heart_down_sound.start();
                }
                updateLife(countLife);
            }
        });

        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin_up_sound.stop();
                coin_down_sound.stop();
                heart_down_sound.stop();
                heart_up_sound.stop();

                countScore++;
                yourScore.setText(String.valueOf(countScore));
                coin_up_sound.start();
            }
        });

        takePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coin_up_sound.stop();
                coin_down_sound.stop();
                heart_down_sound.stop();
                heart_up_sound.stop();

                countScore--;
                yourScore.setText(String.valueOf(countScore));
                coin_down_sound.start();
            }
        });
        return view;
    }

    private void updateLife(int lifes) {
        switch (lifes) {
            case 0:
                life1.setVisibility(View.INVISIBLE);
                life2.setVisibility(View.INVISIBLE);
                life3.setVisibility(View.INVISIBLE);
                break;
            case 1:
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.INVISIBLE);
                life3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.VISIBLE);
                life3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.VISIBLE);
                life3.setVisibility(View.VISIBLE);
                break;
        }
    }

}
