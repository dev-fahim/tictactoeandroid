package com.marveloustech.tictactoeonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.util.Objects;

public class InitialActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        TextView tvVersion = findViewById(R.id.tvVersion);

        tvVersion.setText("Version " + BuildConfig.VERSION_NAME);

        Objects.requireNonNull(getSupportActionBar()).hide();

        EditText playerNameEt = findViewById(R.id.initialPlayerNameEt);
        EditText roomIdEt = findViewById(R.id.initialRoomIdEt);
        AppCompatButton joinRoomBtn = findViewById(R.id.initialJoinRoomBtn);
        AppCompatButton createNewRoomBtn = findViewById(R.id.initialCreateNewRoomBtn);
        AppCompatButton offlinePlayBtn = findViewById(R.id.initialOfflinePlayBtn);

        final String playerUniqueID = String.valueOf(System.currentTimeMillis());

        joinRoomBtn.setOnClickListener(view -> {
            final String playerName = playerNameEt.getText().toString();
            final String roomId = roomIdEt.getText().toString();

            if (playerName.isEmpty()) {
                Toast.makeText(InitialActivity.this, "Player name is required!", Toast.LENGTH_SHORT).show();
            } else if (roomId.isEmpty()) {
                Toast.makeText(InitialActivity.this, "Room ID is required!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(InitialActivity.this, OnlineGameActivity.class);
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

                Intent intent = new Intent(InitialActivity.this, OnlineGameActivity.class);
                intent.putExtra("playerName", playerName);
                intent.putExtra("roomId", roomId);
                intent.putExtra("playerUniqueID", playerUniqueID);
                startActivity(intent);
                finish();
            }
        });

        offlinePlayBtn.setOnClickListener(view -> {
            Intent intent = new Intent(InitialActivity.this, OfflineGameActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
