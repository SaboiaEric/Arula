<<<<<<< HEAD
package br.com.arula.arula.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.arula.arula.R;
import br.com.arula.arula.model.Job;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JobActivity extends AppCompatActivity {

    @BindView(R.id.imageJobActivity)
    ImageView image;
    @BindView(R.id.jobNameJobActivity)
    TextView name;
    @BindView(R.id.companyNameJobActivity)
    TextView company;
    @BindView(R.id.salaryJobActivity)
    TextView salary;
    @BindView(R.id.typeJobActivity)
    TextView type;
    @BindView(R.id.hourJobActivity)
    TextView hour;
    @BindView(R.id.descJobActivity)
    TextView desc;
    @BindView(R.id.reqJobActivity)
    TextView req;

    Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        job = (Job) getIntent().getSerializableExtra("job");

        //image.setImageBitmap();
        name.setText(job.getName());
        company.setText(job.getCompanyName());
        salary.setText(job.getSalary().toString());
        type.setText(job.getType());
        hour.setText(job.getHour());
        desc.setText(job.getDesc());
        req.setText(job.getReq());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_check, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if(item.getItemId() == R.id.menu_check) {
            job.setControl(true);
        }

        return super.onOptionsItemSelected(item);
    }
}
=======
package br.com.arula.arula.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.arula.arula.R;
import br.com.arula.arula.model.Job;
import butterknife.BindView;
import butterknife.ButterKnife;

public class JobActivity extends AppCompatActivity {

    @BindView(R.id.imageJobActivity)
    ImageView image;
    @BindView(R.id.jobNameJobActivity)
    TextView name;
    @BindView(R.id.companyNameJobActivity)
    TextView company;
    @BindView(R.id.salaryJobActivity)
    TextView salary;
    @BindView(R.id.typeJobActivity)
    TextView type;
    @BindView(R.id.hourJobActivity)
    TextView hour;
    @BindView(R.id.descJobActivity)
    TextView desc;
    @BindView(R.id.reqJobActivity)
    TextView req;

    Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        job = (Job) getIntent().getSerializableExtra("job");

        //image.setImageBitmap();
        name.setText(job.getName());
        company.setText(job.getCompanyName());
        salary.setText(job.getSalary().toString());
        type.setText(job.getType());
        hour.setText(job.getHour());
        desc.setText(job.getDesc());
        req.setText(job.getReq());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_check, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        if(item.getItemId() == R.id.menu_check) {
            job.setControl(true);
        }

        return super.onOptionsItemSelected(item);
    }
}
>>>>>>> dc13c0ca8fd3745f4996e2b8186ebb2c83bbb0c0
