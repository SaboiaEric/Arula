package br.com.arula.arula.activities;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.os.Binder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import br.com.arula.arula.R;
import br.com.arula.arula.dao.QuestionDAO;

import br.com.arula.arula.R;

import br.com.arula.arula.model.Question;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.themeQuestionActivity)
    TextView theme;
    @BindView(R.id.countQuestionActivity)
    TextView count;
    @BindView(R.id.contentQuestionActivity)
    TextView content;
    @BindView(R.id.btnAnswerAQuestionActivity)
    Button btnA;
    @BindView(R.id.textAnswerAQuestionActivity)
    TextView textAnswerA;
    @BindView(R.id.btnAnswerBQuestionActivity)
    Button btnB;
    @BindView(R.id.textAnswerBQuestionActivity)
    TextView textAnswerB;
    @BindView(R.id.btnAnswerCQuestionActivity)
    Button btnC;
    @BindView(R.id.textAnswerCQuestionActivity)
    TextView textAnswerC;
    @BindView(R.id.btnAnswerDQuestionActivity)
    Button btnD;
    @BindView(R.id.textAnswerDQuestionActivity)
    TextView textAnswerD;
    @BindView(R.id.btnAnswerEQuestionActivity)
    Button btnE;
    @BindView(R.id.textAnswerEQuestionActivity)
    TextView textAnswerE;
    @BindView(R.id.next)
    Button next;

    QuestionDAO questionDAO;

    List<Question> questions;
    Question question;
    String course;
    boolean correct;
    int corrects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int green = Color.rgb(62, 196, 89);
        final int red = Color.rgb(226, 94, 77);

        questions = new ArrayList<>();
        questionDAO = new QuestionDAO(this);

        ButterKnife.bind(this);

        theme.setText(course);

        course = (String) getIntent().getSerializableExtra("course");
        corrects = getIntent().getIntExtra("corrects", 0);
        final int count =  getIntent().getIntExtra("count", 0);
        List<Question> aux = questionDAO.Read();

        for(Question q: aux)
            if(q.getCourse() != null && !q.getCourse().isEmpty() && q.getCourse().equals(course))
                questions.add(q);

        question = questions.get(count);

        theme.setText(course);
        content.setText(question.getQuestion());

        textAnswerA.setText(question.getResA());
        textAnswerB.setText(question.getResB());
        textAnswerC.setText(question.getResC());
        textAnswerD.setText(question.getResD());
        textAnswerE.setText(question.getResE());

        this.count.setText(count+1 + " de 3");

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getCorrectAnswer() == 0) {
                    correct = true;
                    corrects++;
                    next.setBackgroundColor(green);
                }
                else
                    next.setBackgroundColor(red);
                next.setEnabled(true);

            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getCorrectAnswer() == 1) {
                    correct = true;
                    corrects++;
                    next.setBackgroundColor(green);
                }
                else
                    next.setBackgroundColor(red);
                next.setEnabled(true);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getCorrectAnswer() == 2) {
                    correct = true;
                    corrects++;
                    next.setBackgroundColor(green);
                }
                else
                    next.setBackgroundColor(red);
                next.setEnabled(true);

            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getCorrectAnswer() == 3) {
                    correct = true;
                    corrects++;
                    next.setBackgroundColor(green);
                }
                else
                    next.setBackgroundColor(red);
                next.setEnabled(true);
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(question.getCorrectAnswer() == 4) {
                    correct = true;
                    corrects++;
                    next.setBackgroundColor(green);
                }
                else
                    next.setBackgroundColor(red);
                next.setEnabled(true);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count != 2) {
                    Intent nextIntent = new Intent(QuestionActivity.this, QuestionActivity.class);
                    nextIntent.putExtra("count", count+1);
                    nextIntent.putExtra("corrects", corrects);
                    nextIntent.putExtra("course", course);
                    startActivity(nextIntent);
                }
                else {
                    Intent resultIntent = new Intent(QuestionActivity.this, ResultQuestionActivity.class);
                    resultIntent.putExtra("course", course);
                    resultIntent.putExtra("corrects", corrects);
                    startActivity(resultIntent);
                }

            }
        });
    }

    public void setLayout() {
        theme.setText(course);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
