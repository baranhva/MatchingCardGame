package com.example.blackhorse.matchingcardgame.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhorse.matchingcardgame.R;
import com.google.android.material.textfield.TextInputLayout;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    private ImageView life1, life2, life3;
    private TextView yourScore, time;
    private Button addPoint, takePoint, addLife, takeLife, resetTime, save;
    private TextInputLayout yourName;

    private int countScore = 0;
    private int countLife = 0;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);

        life1 = view.findViewById(R.id.life1);
        life2 = view.findViewById(R.id.life2);
        life3 = view.findViewById(R.id.life3);
        yourScore = view.findViewById(R.id.yourScore);
        time = view.findViewById(R.id.time);
        addPoint = view.findViewById(R.id.addPoint);
        takePoint = view.findViewById(R.id.takePoint);
        addLife = view.findViewById(R.id.addLife);
        takeLife = view.findViewById(R.id.takeLife);
        resetTime = view.findViewById(R.id.resetTime);
        save = view.findViewById(R.id.save);
        yourName = view.findViewById(R.id.yourName);
        yourScore.setText(String.valueOf(countScore));

        updateLife(countLife);


        addLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                life1.setVisibility(View.INVISIBLE);

                if(countLife<3){
                    countLife++;
                }
                updateLife(countLife);
                
            }
        });

        takeLife.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countLife > 0) {
                    countLife--;
                }
                updateLife(countLife);
            }
        });


        addPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countScore++;
                yourScore.setText(String.valueOf(countScore));
            }
        });

        takePoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countScore--;
                yourScore.setText(String.valueOf(countScore));
            }
        });
        return view;
    }


    public void updateLife(int lifes) {
        switch (lifes) {
            case 0:
                life1.setVisibility(View.INVISIBLE);
                life2.setVisibility(View.INVISIBLE);
                life3.setVisibility(View.INVISIBLE);
            case 1:
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.INVISIBLE);
                life3.setVisibility(View.INVISIBLE);
            case 2:
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.VISIBLE);
                life3.setVisibility(View.INVISIBLE);
            case 3:
                life1.setVisibility(View.VISIBLE);
                life2.setVisibility(View.VISIBLE);
                life3.setVisibility(View.VISIBLE);
        }

    }

}
