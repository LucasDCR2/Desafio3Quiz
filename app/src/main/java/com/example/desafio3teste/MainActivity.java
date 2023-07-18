package com.example.desafio3teste;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView;
    private RadioGroup optionsRadioGroup;
    private Button submitButton;

    private List<String> questions;
    private List<String[]> options;
    private List<Integer> correctAnswers;
    private List<Integer> selectedAnswers;

    private int currentQuestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        optionsRadioGroup = findViewById(R.id.opcoesRadioGroup);
        submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedRadioButtonId = optionsRadioGroup.getCheckedRadioButtonId();

                if (selectedRadioButtonId == -1) {
                    Toast.makeText(MainActivity.this, "Selecione uma opção", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                int answerIndex = optionsRadioGroup.indexOfChild(selectedRadioButton);
                selectedAnswers.set(currentQuestion, answerIndex);

                if (currentQuestion < questions.size() - 1) {
                    currentQuestion++;
                    showQuestion();
                } else {
                    showScore();
                }
            }
        });

        initializeQuiz();
        showQuestion();
    }

    private void initializeQuiz() {
        questions = new ArrayList<>();
        questions.add("O Android é baseado em qual sistema operacional?");
        questions.add("Por onde a aplicação no Android é iniciada?");
        questions.add("Qual é a cor do céu em um dia ensolarado?");

        options = new ArrayList<>();
        options.add(new String[]{"Linux", "iOS", "Windows", "Java"});
        options.add(new String[]{"Serviço (Service)", "Receptor de Transmissões (Broadcast Receiver)", "Activity Principal", "Provedor de Conteúdo (Content Provider)"});
        options.add(new String[]{"Azul", "Verde", "Amarelo", "Vermelho"});

        correctAnswers = new ArrayList<>();
        correctAnswers.add(0); // Resposta correta para a primeira pergunta
        correctAnswers.add(2); // Resposta correta para a segunda pergunta
        correctAnswers.add(0); // Resposta correta para a terceira pergunta

        selectedAnswers = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            selectedAnswers.add(-1); // Valor inicial para as respostas selecionadas (-1 indica nenhuma resposta)
        }

        currentQuestion = 0;
    }

    private void showQuestion() {
        String question = questions.get(currentQuestion);
        String[] questionOptions = options.get(currentQuestion);

        questionTextView.setText(question);

        for (int i = 0; i < optionsRadioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) optionsRadioGroup.getChildAt(i);
            radioButton.setText(questionOptions[i]);
        }

        optionsRadioGroup.clearCheck();
        if (selectedAnswers.get(currentQuestion) != -1) {
            RadioButton selectedRadioButton = (RadioButton) optionsRadioGroup.getChildAt(selectedAnswers.get(currentQuestion));
            selectedRadioButton.setChecked(true);
        }
    }

    private void showScore() {
        int score = 0;
        for (int i = 0; i < questions.size(); i++) {
            if (selectedAnswers.get(i) == correctAnswers.get(i)) {
                score++;
            }
        }

        Intent intent = new Intent(MainActivity.this, LastActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}