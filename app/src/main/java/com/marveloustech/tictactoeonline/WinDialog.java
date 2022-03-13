package com.marveloustech.tictactoeonline;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class WinDialog extends Dialog {
    private final String message;
    private final GameActivity gameActivity;

    public WinDialog(@NonNull Context context, String message) {
        super(context);
        this.message = message;
        gameActivity = (GameActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);

        final TextView messageTV = findViewById(R.id.messageTV);
        final AppCompatButton startNewBtn = findViewById(R.id.startNewBtn);
        final AppCompatButton restartBtn = findViewById(R.id.restartBtn);

        messageTV.setText(message);

        startNewBtn.setOnClickListener(view -> {
            dismiss();
            getContext().startActivity(new Intent(getContext(), InitialActivity.class));
            gameActivity.finish();
        });

        restartBtn.setOnClickListener(view -> {
            dismiss();
            gameActivity.restart();
        });
    }
}
