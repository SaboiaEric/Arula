package br.com.arula.arula.activities;

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

    String[] questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        Question question = (Question) getIntent().getSerializableExtra("question");
        theme.setText(question.getName());
        //content.setText(question.getQuestion());

        //questions = question.toArray();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
