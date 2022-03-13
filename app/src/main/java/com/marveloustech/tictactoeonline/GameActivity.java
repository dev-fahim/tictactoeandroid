package com.marveloustech.tictactoeonline;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

abstract public class GameActivity extends AppCompatActivity {
    public LinearLayout player1Layout, player2Layout;
    public TextView player1TV, player2TV;

    public ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;

    public final List<int[]> combinationList = new ArrayList<>();
    public final List<String> doneBoxes = new ArrayList<>();

    public String[] boxesSelectedBy = {"", "", "", "", "", "", "", "", ""};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        start();
    }

    protected void start() {
    }

    protected void setMode(String mode) {
        Objects.requireNonNull(getSupportActionBar()).setTitle("Playing " + mode);
    }

    public boolean checkPlayerWin(String playerID) {
        boolean isPlayerWon = false;

        for (int i = 0; i < combinationList.size(); i++) {
            final int[] combination = combinationList.get(i);

            if (boxesSelectedBy[combination[0]].equals(playerID) &&
                    boxesSelectedBy[combination[1]].equals(playerID) &&
                    boxesSelectedBy[combination[2]].equals(playerID)) {

                isPlayerWon = true;
            }
        }

        return isPlayerWon;
    }

    public void restart() {
        boxesSelectedBy = new String[]{"", "", "", "", "", "", "", "", ""};
        doneBoxes.clear();
        image1.setImageResource(R.drawable.transparent_back);
        image2.setImageResource(R.drawable.transparent_back);
        image3.setImageResource(R.drawable.transparent_back);
        image4.setImageResource(R.drawable.transparent_back);
        image5.setImageResource(R.drawable.transparent_back);
        image6.setImageResource(R.drawable.transparent_back);
        image7.setImageResource(R.drawable.transparent_back);
        image8.setImageResource(R.drawable.transparent_back);
        image9.setImageResource(R.drawable.transparent_back);
    }

    public void stop() {
    }

    @Override
    public void finish() {
        stop();
        super.finish();
    }

    public void playBtnClick() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.play_btn_click);
        mp.start();
    }

    public void playWin() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.play_win);
        mp.start();
    }

    public void playNotWin() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.play_not_win);
        mp.start();
    }
}
