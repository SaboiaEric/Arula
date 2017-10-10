package br.com.arula.arula.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.arula.arula.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ResultQuestionActivity extends AppCompatActivity {

    private int corrects;

    @BindView(R.id.correctAnswersResultActivity)
    TextView correctsTV;
    @BindView(R.id.scoreResultActivity)
    TextView score;
    @BindView(R.id.btnResultActivity)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_question);

        ButterKnife.bind(this);

        corrects = getIntent().getIntExtra("corrects", 0);

        correctsTV.setText("Você acertou " + corrects + " questões!");
        score.setText(corrects * 100 + " pontos foram adicionados ao seu perfil.");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMain = new Intent(ResultQuestionActivity.this, MainActivity.class);
                startActivity(intentMain);
            }
        });

    }
}
