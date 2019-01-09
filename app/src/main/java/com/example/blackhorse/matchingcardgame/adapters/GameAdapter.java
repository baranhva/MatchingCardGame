package com.example.blackhorse.matchingcardgame.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.blackhorse.matchingcardgame.R;
import com.example.blackhorse.matchingcardgame.models.Game;
import com.example.blackhorse.matchingcardgame.viewholders.GameViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class GameAdapter extends RecyclerView.Adapter<GameViewHolder>  {

    public List<Game> listGame;

    public GameAdapter(List<Game> listGame) {
        this.listGame = listGame;
    }
    @Override
    public GameViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.score_view, parent, false);
        return new GameViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final GameViewHolder holder, final int position) {
        // Gets a single item in the list from its position
        final Game game = listGame.get(position);
        // The holder argument is used to reference the views inside the viewHolder
        // Populate the views with the data from the list
        holder.gameUserName.setText(game.getUserName());
        holder.gameScore.setText("" + game.getScore());
        holder.gameRank.setText("" + (position + 1));
    }
    @Override
    public int getItemCount() {
        return listGame.size();
    }

        public void swapList (List<Game> newList) {
        listGame = newList;
        if (newList != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }

    }


}