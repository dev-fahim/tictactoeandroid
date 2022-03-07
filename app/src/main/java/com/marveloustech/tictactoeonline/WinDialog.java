package com.marveloustech.tictactoeonline;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;

public class WinDialog extends Dialog {
    private final String message;
    private MainActivity mainActivity;

    public WinDialog(@NonNull Context context, String message) {
        super(context);
        this.message = message;
        mainActivity = (MainActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_layout);

        final TextView messageTV = findViewById(R.id.messageTV);
        final AppCompatButton startNewBtn = findViewById(R.id.startNewBtn);

        messageTV.setText(message);
        startNewBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                dismiss();
                getContext().startActivity(new Intent(getContext(), PlayerNameActivity.class));
                mainActivity.finish();
            }
        });
    }
}
