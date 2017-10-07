package br.com.arula.arula.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.arula.arula.R;
import br.com.arula.arula.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity {

    @BindView(R.id.imageUserActivity)
    ImageView image;
    @BindView(R.id.userNameUserActivity)
    TextView name;
    @BindView(R.id.formationUserActivity)
    TextView formation;
    @BindView(R.id.scoreUserActivity)
    TextView score;
    @BindView(R.id.descUserActivity)
    TextView desc;
    @BindView(R.id.reqUserActivity)
    TextView req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        User user = (User) getIntent().getSerializableExtra("user");

        //image.setImageBitmap();
        name.setText(user.getName());
        formation.setText(user.getFormation());
        score.setText(user.getScore().toString());
        desc.setText(user.getDesc());
        req.setText(user.getReq());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
