package br.com.arula.arula.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;

import br.com.arula.arula.R;
import br.com.arula.arula.RecyclerViewClickPosition;
import br.com.arula.arula.adapter.CardViewAdapterJob;
import br.com.arula.arula.adapter.CardViewAdapterQuestion;
import br.com.arula.arula.adapter.CardViewAdapterUser;
import br.com.arula.arula.dao.JobDAO;
import br.com.arula.arula.dao.QuestionDAO;
import br.com.arula.arula.dao.UserDAO;
import br.com.arula.arula.model.Job;
import br.com.arula.arula.model.Question;
import br.com.arula.arula.model.User;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickPosition {

    private TextView mTextMessage;

    @BindView(R.id.recyclerViewJobs)
    RecyclerView list;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private List<Job> jobs;
    private List<User> users;
    private List<Question> questions;

    private UserDAO userDAO;
    private JobDAO jobDAO;
    private QuestionDAO questionDAO;

    private int controlNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        userDAO = new UserDAO(this);
        jobDAO = new JobDAO(this);
        questionDAO = new QuestionDAO(this);

        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);

        jobs = jobDAO.Read();
        users = userDAO.Read();
        questions = questionDAO.Read();

        controlNavigation = 1;

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadListJobs();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_jobs:
                    jobs.clear();
                    for(int i = 0; i < jobDAO.Read().size(); i++) {
                        jobs.add(jobDAO.Read().get(i));
                    }
                    loadListJobs();
                    controlNavigation = 1;
                    return true;
                case R.id.navigation_jobsForUser:
                    jobs.clear();
                    for(int i = 0; i < jobDAO.Read().size(); i++) {
                        if(i%2==0)
                            jobs.add(jobDAO.Read().get(i));
                    }
                    loadListJobs();
                    controlNavigation = 2;
                    return true;
                case R.id.navigation_questions:
                    questions.clear();
                    for(Question q : questionDAO.Read())
                        questions.add(q);
                    loadListQuestions();
                    controlNavigation = 3;
                    return true;
                case R.id.navigation_rankings:
                    jobs.clear();
                    users.clear();
                    for(int i = 0; i < userDAO.Read().size(); i++) {
                        users.add(userDAO.Read().get(i));
                    }
                    loadListUsers();
                    controlNavigation = 4;
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_generate, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_generate_jobs:
                generateJobs();
                loadListJobs();
                break;
            case R.id.menu_generate_users:
                generateUsers();
                loadListUsers();
                break;
            case R.id.menu_generate_questions:
                generateQuestions();
                loadListQuestions();
                break;
        }

        return false;
    }

    @Override
    public void getRecyclerViewAdapterPosition(int position) {
        if(controlNavigation == 1 || controlNavigation == 2) {
            Intent intentJobActivity = new Intent(MainActivity.this, JobActivity.class);
            startActivity(intentJobActivity);
        } else if(controlNavigation == 3) {

        } else if(controlNavigation == 4) {
            Intent intentUserActivity = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intentUserActivity);
        }
    }

    public void loadListJobs() {
        mAdapter = new CardViewAdapterJob(jobs, this);
        list.setAdapter(mAdapter);
    }

    public void loadListUsers() {
        mAdapter = new CardViewAdapterUser(users, this);
        list.setAdapter(mAdapter);
    }

    public void loadListQuestions() {
        mAdapter = new CardViewAdapterQuestion(questions, this);
        list.setAdapter(mAdapter);
    }

    public void generateJobs() {
        for(int i = 0; i < 10; i++) {
            jobDAO.Insert(new Job("Job"+i));
        }
    }

    public void generateUsers() {
        for(int i = 0; i < 10; i++) {
            userDAO.Insert(new User("User"+i));
        }
    }

    private void generateQuestions() {
        for(int i = 0; i < 10; i++)
            questionDAO.Insert(new Question("Question"+i));
    }
}
