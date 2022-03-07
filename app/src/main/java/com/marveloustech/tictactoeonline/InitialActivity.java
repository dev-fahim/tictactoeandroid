package com.marveloustech.tictactoeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InitialActivity extends AppCompatActivity {

    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://marveloustechnologiesapplab.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        EditText playerNameEt = findViewById(R.id.initialPlayerNameEt);
        EditText roomIdEt = findViewById(R.id.initialRoomIdEt);
        AppCompatButton joinRoomBtn = findViewById(R.id.initialJoinRoomBtn);
        AppCompatButton createNewRoomBtn = findViewById(R.id.initialCreateNewRoomBtn);


        joinRoomBtn.setOnClickListener(view -> {
            final String playerName = playerNameEt.getText().toString();
            final String roomId = roomIdEt.getText().toString();

            if (playerName.isEmpty()) {
                Toast.makeText(InitialActivity.this, "Player name is required!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(InitialActivity.this, GameActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
                finish();
            }
        });

        createNewRoomBtn.setOnClickListener(view -> {
            final String playerName = playerNameEt.getText().toString();

            if (playerName.isEmpty()) {
                Toast.makeText(InitialActivity.this, "Player name is required!", Toast.LENGTH_SHORT).show();
            } else {
                final String playerUniqueID = String.valueOf(System.currentTimeMillis());
                String roomId = String.valueOf(System.currentTimeMillis());
                DatabaseReference connectionRef = databaseReference.child("connections");

                roomId = connectionRef.push().getKey();

                assert roomId != null;
                databaseReference.child("connections").child(roomId).child(playerUniqueID).child("player_name").getRef().setValue(playerName);

                Intent intent = new Intent(InitialActivity.this, GameActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("roomId", roomId);
                startActivity(intent);
                finish();
            }
        });


    }
}
