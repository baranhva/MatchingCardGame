package com.example.blackhorse.matchingcardgame.activities;

import android.os.Bundle;

import com.example.blackhorse.matchingcardgame.R;
import com.example.blackhorse.matchingcardgame.adapters.GameAdapter;
import com.example.blackhorse.matchingcardgame.adapters.SectionPageAdapter;
import com.example.blackhorse.matchingcardgame.database.GameDatabase;
import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.luseen.spacenavigation.SpaceOnLongClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SpaceNavigationView spaceNavigationView;
    ViewPager viewPager;
    SectionPageAdapter pageAdapter;
//    public static GameDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        db = GameDatabase.getInstance(this);
        viewPager = findViewById(R.id.viewPager);
        pageAdapter = new SectionPageAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(pageAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
            spaceNavigationView.changeCurrentItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // Bottom navbar, added two menu items
        spaceNavigationView = findViewById(R.id.space);
        spaceNavigationView.initWithSaveInstanceState(savedInstanceState);
        spaceNavigationView.addSpaceItem(new SpaceItem("GAME", R.drawable.ic_cards));
        spaceNavigationView.addSpaceItem(new SpaceItem("HIGHSCORE", R.drawable.ic_highscore));
        spaceNavigationView.showIconOnly();

        spaceNavigationView.setSpaceOnClickListener(new SpaceOnClickListener() {

            // Center button click  will show message
            @Override
            public void onCentreButtonClick() {
                Toast.makeText(MainActivity.this, "Hold Down To Shut Off", Toast.LENGTH_SHORT).show();
            }

            // When clicked on menu item, the viewpager will show the right fragment
            @Override
            public void onItemClick(int itemIndex, String itemName) {
                if (itemName == "GAME") {
                    viewPager.setCurrentItem(0);
                } else if (itemName == "HIGHSCORE") {
                    viewPager.setCurrentItem(1);
                }
            }

            // Not used
            @Override
            public void onItemReselected(int itemIndex, String itemName) { }
        });
        spaceNavigationView.setSpaceOnLongClickListener(new SpaceOnLongClickListener() {
            // shuts off application when button is long pressed
            @Override
            public void onCentreButtonLongClick() {
                onBackPressed();
            }

            //not used
            @Override
            public void onItemLongClick(int itemIndex, String itemName) {
            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        spaceNavigationView.onSaveInstanceState(outState);
    }

    // this method is used to shut down the app
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

}
