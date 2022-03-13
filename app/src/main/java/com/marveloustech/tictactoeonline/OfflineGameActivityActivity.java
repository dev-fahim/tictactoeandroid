package com.marveloustech.tictactoeonline;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OfflineGameActivityActivity extends GameActivity {
    protected String mode = "Offline";

    boolean isPlayerOne = true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void start() {
        setMode("Offline");

        super.start();

        player1Layout = findViewById(R.id.gamePlayer1Layout);
        player2Layout = findViewById(R.id.gamePlayer2Layout);

        image1 = findViewById(R.id.gameImage1);
        image2 = findViewById(R.id.gameImage2);
        image3 = findViewById(R.id.gameImage3);
        image4 = findViewById(R.id.gameImage4);
        image5 = findViewById(R.id.gameImage5);
        image6 = findViewById(R.id.gameImage6);
        image7 = findViewById(R.id.gameImage7);
        image8 = findViewById(R.id.gameImage8);
        image9 = findViewById(R.id.gameImage9);

        TextView player1TV = findViewById(R.id.gamePlayer1TV);
        TextView player2TV = findViewById(R.id.gamePlayer2TV);

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{0, 4, 8});
        combinationList.add(new int[]{2, 4, 6});

        player1TV.setText("Player 1");
        player2TV.setText("Player 2");

        image1.setOnClickListener(new View.OnClickListener() {
            final String _position = "1";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image1, 1);
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            final String _position = "2";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image2, 2);
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            final String _position = "3";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image3, 3);
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            final String _position = "4";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image4, 4);
                }
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            final String _position = "5";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image5, 5);
                }
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            final String _position = "6";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image6, 6);
                }
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            final String _position = "7";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image7, 7);
                }
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            final String _position = "8";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image8, 8);
                }
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            final String _position = "9";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position)) {
                    selectBox(image9, 9);
                }
            }
        });
    }

    private void togglePlayerTurn() {
        isPlayerOne = !isPlayerOne;
        if (isPlayerOne) {
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue_stroke);
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue_20);
        } else {
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue_stroke);
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue_20);
        }
    }

    private void selectBox(ImageView imageView, int selectedBoxPosition) {
        doneBoxes.add(String.valueOf(selectedBoxPosition));

        String selectedByPlayer = isPlayerOne ? "Player 1" : "Player 2";

        boxesSelectedBy[selectedBoxPosition - 1] = selectedByPlayer;

        if (isPlayerOne) {
            imageView.setImageResource(R.drawable.cross_icon);
        } else {
            imageView.setImageResource(R.drawable.zero_icon);
        }
        playBtnClick();

        if (checkPlayerWin(selectedByPlayer)) {
            final WinDialog winDialog;

            winDialog = new WinDialog(OfflineGameActivityActivity.this, selectedByPlayer + " WON the GAME!");

            winDialog.setCancelable(false);
            winDialog.show();

            playWin();
        }

        togglePlayerTurn();

        if (doneBoxes.size() == 9) {
            final WinDialog winDialog = new WinDialog(OfflineGameActivityActivity.this, "It was a DRAW!");
            playNotWin();
            winDialog.setCancelable(false);
            winDialog.show();
        }
    }
}
