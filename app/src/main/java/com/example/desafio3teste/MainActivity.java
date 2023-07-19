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
    private TextView textoPergunta;
    private RadioGroup grupoOpcoes;
    private Button botaoEnviar;
    private List<String> perguntas;
    private List<String[]> opcoes;
    private List<Integer> respostasCorretas;
    private List<Integer> respostasSelecionadas;
    private int perguntaAtual;
    private TextView textoNumeroQuestao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textoNumeroQuestao = findViewById(R.id.textoNumeroQuestao);

        textoPergunta = findViewById(R.id.questionTextView);
        grupoOpcoes = findViewById(R.id.opcoesRadioGroup);
        botaoEnviar = findViewById(R.id.submitButton);

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idRadioButtonSelecionado = grupoOpcoes.getCheckedRadioButtonId();

                if (idRadioButtonSelecionado == -1) {
                    Toast.makeText(MainActivity.this, "Selecione uma opção", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton radioButtonSelecionado = findViewById(idRadioButtonSelecionado);
                int indiceResposta = grupoOpcoes.indexOfChild(radioButtonSelecionado);
                respostasSelecionadas.set(perguntaAtual, indiceResposta);

                if (perguntaAtual < perguntas.size() - 1) {
                    perguntaAtual++;
                    mostrarPergunta();
                } else {
                    showScore();
                }
            }
        });

        inicializarQuiz();
        mostrarPergunta();
    }

    private void inicializarQuiz() {
        perguntas = new ArrayList<>();
        perguntas.add("O Android é baseado em qual sistema operacional?");
        perguntas.add("Por onde a aplicação no Android é iniciada?");
        perguntas.add("Para usar uma activity dentro do app é preciso fazer o quê?");

        opcoes = new ArrayList<>();
        opcoes.add(new String[]{"Linux", "iOS", "Windows", "Java"});
        opcoes.add(new String[]{"Serviço (Service)", "Receptor de Transmissões (Broadcast Receiver)", "Activity Principal", "Provedor de Conteúdo (Content Provider)"});
        opcoes.add(new String[]{"Criar uma classe para a activity", "Declará-la no AndroidManifest.xml", "Conectar a activity ao banco de dados", "Adicionar a activity no build.gradle"});

        respostasCorretas = new ArrayList<>();
        respostasCorretas.add(0); // Resposta correta para a primeira pergunta
        respostasCorretas.add(2); // Resposta correta para a segunda pergunta
        respostasCorretas.add(1); // Resposta correta para a terceira pergunta

        respostasSelecionadas = new ArrayList<>();
        for (int i = 0; i < perguntas.size(); i++) {
            respostasSelecionadas.add(-1); // Valor inicial para as respostas selecionadas (-1 indica nenhuma resposta)
        }

        perguntaAtual = 0;
    }

    private void mostrarPergunta() {
        String pergunta = perguntas.get(perguntaAtual);
        String[] opcoesPergunta = opcoes.get(perguntaAtual);

        textoNumeroQuestao.setText("Questão " + (perguntaAtual + 1) + "/" + perguntas.size());

        textoPergunta.setText(pergunta);

        for (int i = 0; i < grupoOpcoes.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) grupoOpcoes.getChildAt(i);
            radioButton.setText(opcoesPergunta[i]);
        }

        grupoOpcoes.clearCheck();
        if (respostasSelecionadas.get(perguntaAtual) != -1) {
            RadioButton radioButtonSelecionado = (RadioButton) grupoOpcoes.getChildAt(respostasSelecionadas.get(perguntaAtual));
            radioButtonSelecionado.setChecked(true);
        }
    }

    private void showScore() {
        int score = 0;
        for (int i = 0; i < perguntas.size(); i++) {
            if (respostasSelecionadas.get(i) == respostasCorretas.get(i)) {
                score++;
            }
        }

        Intent intent = new Intent(MainActivity.this, LastActivity.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }
}
