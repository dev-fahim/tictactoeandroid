package com.marveloustech.tictactoeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        final String playerUniqueID = String.valueOf(System.currentTimeMillis());

        joinRoomBtn.setOnClickListener(view -> {
            final String playerName = playerNameEt.getText().toString();
            final String roomId = roomIdEt.getText().toString();

            if (playerName.isEmpty()) {
                Toast.makeText(InitialActivity.this, "Player name is required!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(InitialActivity.this, GameActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("roomId", roomId);
                intent.putExtra("playerUniqueID", playerUniqueID);
                startActivity(intent);
                finish();
            }
        });

        createNewRoomBtn.setOnClickListener(view -> {
            final String playerName = playerNameEt.getText().toString();

            if (playerName.isEmpty()) {
                Toast.makeText(InitialActivity.this, "Player name is required!", Toast.LENGTH_SHORT).show();
            } else {
                String roomId = String.valueOf(System.currentTimeMillis());

                Intent intent = new Intent(InitialActivity.this, GameActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("roomId", roomId);
                intent.putExtra("playerUniqueID", playerUniqueID);
                startActivity(intent);
                finish();
            }
        });


    }
}
