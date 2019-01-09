package com.example.blackhorse.matchingcardgame.adapters;

import com.example.blackhorse.matchingcardgame.fragments.GameFragment;
import com.example.blackhorse.matchingcardgame.fragments.LeaderboardFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionPageAdapter extends FragmentStatePagerAdapter {

    int numberOfTabs;

    public SectionPageAdapter(@NonNull FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new GameFragment();
            case 1:
                return new LeaderboardFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
