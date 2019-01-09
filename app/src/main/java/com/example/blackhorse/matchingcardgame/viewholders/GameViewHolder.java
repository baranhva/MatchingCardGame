package com.example.blackhorse.matchingcardgame.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.blackhorse.matchingcardgame.R;

import androidx.recyclerview.widget.RecyclerView;

// do i need view.onclicklisteren
public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView gameUserName;
    public TextView gameScore;
    public TextView gameRank;
    public View view;

    public GameViewHolder(View itemView) {
        super(itemView);
        gameUserName = itemView.findViewById(R.id.name);
        gameScore = itemView.findViewById(R.id.score);
        gameRank = itemView.findViewById(R.id.rank);
        view = itemView;
        // do i need this? why?
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
