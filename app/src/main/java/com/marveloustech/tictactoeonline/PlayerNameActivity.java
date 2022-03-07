package com.marveloustech.tictactoeonline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class PlayerNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        EditText playerNameEt = findViewById(R.id.playerNameEt);
        AppCompatButton startGameBtn = findViewById(R.id.startGameBtn);

        startGameBtn.setOnClickListener(view -> {
            final String playerName = playerNameEt.getText().toString();

            if (playerName.isEmpty()) {
                Toast.makeText(PlayerNameActivity.this, "Player name is required!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(PlayerNameActivity.this, MainActivity.class);
                intent.putExtra("playerName", playerName);
                startActivity(intent);
                finish();
            }
        });
    }
}
