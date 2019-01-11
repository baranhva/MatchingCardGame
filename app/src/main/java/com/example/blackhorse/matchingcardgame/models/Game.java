package com.example.blackhorse.matchingcardgame.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Chronometer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "game")
public class Game implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(name = "username")
    private String userName;
    @ColumnInfo(name = "score")
    private int score;
    @ColumnInfo(name = "heart")
    private int heart;

    public Game() {
    }

    public Game(String userName, int score, int heart) {
        this.userName = userName;
        this.score = score;
        this.heart = heart;
    }

    public int getHeart() {
        return heart;
    }
    public void setHeart(int heart) {
        this.heart = heart;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.userName);
        dest.writeInt(this.score);
        dest.writeInt(this.heart);
    }

    private Game(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.userName = in.readString();
        this.score = in.readInt();
        this.heart = in.readInt();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel source) {
            return new Game(source);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };
}
