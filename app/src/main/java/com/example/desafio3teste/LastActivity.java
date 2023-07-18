package com.example.desafio3teste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class LastActivity extends AppCompatActivity {

    private TextView scoreTextView;
    private Button restartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last);

        scoreTextView = findViewById(R.id.scoreTextView);
        restartButton = findViewById(R.id.restartButton);

        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartQuiz();
            }
        });

        Intent intent = getIntent();
        int score = intent.getIntExtra("score", 0);

        scoreTextView.setText("Score: " + score);
    }

    private void restartQuiz() {
        // Reinicie o quiz, retornando para a atividade inicial
        Intent intent = new Intent(LastActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

