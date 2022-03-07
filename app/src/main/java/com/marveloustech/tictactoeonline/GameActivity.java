package com.marveloustech.tictactoeonline;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GameActivity extends AppCompatActivity {

    private LinearLayout player1Layout, player2Layout;
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9;
    private TextView player1TV, player2TV;

    private final List<int[]> combinationList = new ArrayList<>();
    private final List<String> doneBoxes = new ArrayList<>();

    private boolean opponentFound = false;

    private String status = "matching";

    private String playerUniqueID = "0";
    private String opponentUniqueID = "0";
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://marveloustechnologiesapplab.firebaseio.com/");

    private String playerTurn = "";

    private String connectionID = "";

    ValueEventListener turnsEventListener, wonEventListener;

    private final String[] boxesSelectedBy = {"", "", "", "", "", "", "", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

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

        player1TV = findViewById(R.id.gamePlayer1TV);
        player2TV = findViewById(R.id.gamePlayer2TV);

        final String getPlayerName = getIntent().getStringExtra("playerName");
        connectionID = getIntent().getStringExtra("roomId");

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{0, 4, 8});
        combinationList.add(new int[]{2, 4, 6});

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Waiting for opponent");
        progressDialog.show();

        playerUniqueID = getIntent().getStringExtra("playerUniqueID");

        player1TV.setText(getPlayerName);

        // before databaseReference.child("connections")
        databaseReference.child("connections").child(connectionID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!opponentFound) {
                    if (snapshot.hasChildren()) {
                        String conID = snapshot.getKey();

                        long getPlayersCount = snapshot.getChildrenCount();

                        if (status.equals("waiting")) {
                            if (getPlayersCount == 2) {

                                playerTurn = playerUniqueID;
                                applyPlayerTurn(playerTurn);

                                // 202
                                boolean playerFound = false;

                                for (DataSnapshot players : snapshot.getChildren()) {
                                    String getPlayerUniqueID = players.getKey();

                                    // 305
                                    assert getPlayerUniqueID != null;
                                    if (getPlayerUniqueID.equals(playerUniqueID)) {
                                        playerFound = true;
                                    } else if (playerFound) {
                                        String getOpponentPlayerName = players.child("player_name").getValue(String.class);
                                        opponentUniqueID = players.getKey();

                                        player2TV.setText(getOpponentPlayerName);

                                        // 542
                                        connectionID = conID;

                                        opponentFound = true;

                                        databaseReference.child("turns").child(connectionID).addValueEventListener(turnsEventListener);
                                        databaseReference.child("won").child(connectionID).addValueEventListener(wonEventListener);

                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }

                                        databaseReference.child("connections").removeEventListener(this);
                                    }
                                }
                            }
                        } else {
                            // 1005
                            if (getPlayersCount == 1) {
                                snapshot.child(playerUniqueID).child("player_name").getRef().setValue(getPlayerName);

                                for (DataSnapshot players : snapshot.getChildren()) {

                                    String getOpponentName = players.child("player_name").getValue(String.class);
                                    opponentUniqueID = players.getKey();

                                    playerTurn = opponentUniqueID;
                                    assert playerTurn != null;
                                    applyPlayerTurn(playerTurn);

                                    player2TV.setText(getOpponentName);

                                    connectionID = conID;
                                    opponentFound = true;

                                    databaseReference.child("turns").child(connectionID).addValueEventListener(turnsEventListener);
                                    databaseReference.child("won").child(connectionID).addValueEventListener(wonEventListener);

                                    if (progressDialog.isShowing()) {
                                        progressDialog.dismiss();
                                    }

                                    databaseReference.child("connections").removeEventListener(this);

                                    break;
                                }
                            }
                        }

                        if (!opponentFound && !status.equals("waiting")) {
                            snapshot.child(playerUniqueID).child("player_name").getRef().setValue(getPlayerName);

                            status = "waiting";
                        }
                    } else {
                        snapshot.child(playerUniqueID).child("player_name").getRef().setValue(getPlayerName);

                        status = "waiting";
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        turnsEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (dataSnapshot.getChildrenCount() == 2) {
                        final int getBoxPosition = Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("box_position").getValue(String.class)));
                        final String getPlayerID = dataSnapshot.child("player_id").getValue(String.class);

                        if (!doneBoxes.contains(String.valueOf(getBoxPosition))) {
                            doneBoxes.add(String.valueOf(getBoxPosition));

                            assert getPlayerID != null;

                            if (getBoxPosition == 1) {
                                selectBox(image1, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 2) {
                                selectBox(image2, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 3) {
                                selectBox(image3, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 4) {
                                selectBox(image4, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 5) {
                                selectBox(image5, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 6) {
                                selectBox(image6, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 7) {
                                selectBox(image7, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 8) {
                                selectBox(image8, getBoxPosition, getPlayerID);
                            } else if (getBoxPosition == 9) {
                                selectBox(image9, getBoxPosition, getPlayerID);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        wonEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("player_id")) {
                    String getWinPlayerID = snapshot.child("player_id").getValue(String.class);

                    final WinDialogV2 winDialog;

                    assert getWinPlayerID != null;
                    if (getWinPlayerID.equals(playerUniqueID)) {
                        winDialog = new WinDialogV2(GameActivity.this, "You WON the GAME!");
                    } else {
                        winDialog = new WinDialogV2(GameActivity.this, "Opponent WON the GAME!");
                    }

                    winDialog.setCancelable(false);
                    winDialog.show();

                    databaseReference.child("turns").child(connectionID).removeEventListener(turnsEventListener);
                    databaseReference.child("won").child(connectionID).removeEventListener(wonEventListener);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        image1.setOnClickListener(new View.OnClickListener() {
            final String _position = "1";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image2.setOnClickListener(new View.OnClickListener() {
            final String _position = "2";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image3.setOnClickListener(new View.OnClickListener() {
            final String _position = "3";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image4.setOnClickListener(new View.OnClickListener() {
            final String _position = "4";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image5.setOnClickListener(new View.OnClickListener() {
            final String _position = "5";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image6.setOnClickListener(new View.OnClickListener() {
            final String _position = "6";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image7.setOnClickListener(new View.OnClickListener() {
            final String _position = "7";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image8.setOnClickListener(new View.OnClickListener() {
            final String _position = "8";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });

        image9.setOnClickListener(new View.OnClickListener() {
            final String _position = "9";

            @Override
            public void onClick(View v) {
                if (!doneBoxes.contains(_position) && playerTurn.equals(playerUniqueID)) {
                    ((ImageView) v).setImageResource(R.drawable.cross_icon);

                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("box_position").setValue(_position);
                    databaseReference.child("turns").child(connectionID).child(String.valueOf(doneBoxes.size() + 1)).child("player_id").setValue(playerUniqueID);

                    playerTurn = opponentUniqueID;
                }
            }
        });
    }

    private void applyPlayerTurn(String playerUniqueID2) {

        if (playerUniqueID2.equals(playerUniqueID)) {
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue_stroke);
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue_20);
        } else {
            player2Layout.setBackgroundResource(R.drawable.round_back_dark_blue_stroke);
            player1Layout.setBackgroundResource(R.drawable.round_back_dark_blue_20);
        }
    }

    private void selectBox(ImageView imageView, int selectedBoxPosition, String selectedByPlayer) {
        boxesSelectedBy[selectedBoxPosition - 1] = selectedByPlayer;

        if (selectedByPlayer.equals(playerUniqueID)) {
            imageView.setImageResource(R.drawable.cross_icon);
            playerTurn = opponentUniqueID;
        } else {
            imageView.setImageResource(R.drawable.zero_icon);
            playerTurn = playerUniqueID;
        }

        applyPlayerTurn(playerTurn);

        if (checkPlayerWin(selectedByPlayer)) {
            databaseReference.child("won").child(connectionID).child("player_id").setValue(selectedByPlayer);
        }

        if (doneBoxes.size() == 9) {
            final WinDialogV2 winDialog = new WinDialogV2(GameActivity.this, "It is a DRAW!");
            winDialog.setCancelable(false);
            winDialog.show();
        }
    }

    private boolean checkPlayerWin(String playerID) {
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
}