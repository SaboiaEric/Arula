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
            Job job = jobs.get(position);
            Intent intentJobActivity = new Intent(MainActivity.this, JobActivity.class);
            intentJobActivity.putExtra("job", job);
            startActivity(intentJobActivity);
        } else if(controlNavigation == 3) {
            Question question = questions.get(position);
            Intent intentQuestionActivity = new Intent(MainActivity.this, QuestionActivity.class);
            intentQuestionActivity.putExtra("question", question);
            startActivity(intentQuestionActivity);
        } else if(controlNavigation == 4) {
            User user = users.get(position);
            Intent intentUserActivity = new Intent(MainActivity.this, UserActivity.class);
            intentUserActivity.putExtra("user", user);
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
        for(int i = 0; i < 5; i++) {
            Job job = new Job("Job"+i);
            job.setCompanyName("Facebook");
            job.setSalary(4000.0);
            job.setType("CLT");
            job.setHour("9h-18h");
            job.setDesc("Pensando mais a longo prazo, a percepção das dificuldades faz parte de um processo de gerenciamento das diretrizes de desenvolvimento para o futuro. No mundo atual, o desenvolvimento contínuo de distintas formas de atuação não pode mais se dissociar do processo de comunicação como um todo. A nível organizacional, a consolidação das estruturas nos obriga à análise do retorno esperado a longo prazo.");
            job.setReq("Go | Python | Kotlin | JS");


            jobDAO.Insert(job);
        }
    }

    public void generateUsers() {
        for(int i = 0; i < 10; i++) {
            User user = new User("User"+i);
            user.setFormation("Engenharia da Computação");
            user.setScore(i*330.0);
            user.setDesc("Do mesmo modo, a consulta aos diversos militantes ainda não demonstrou convincentemente que vai participar na mudança das posturas dos órgãos dirigentes com relação às suas atribuições. Ainda assim, existem dúvidas a respeito de como o novo modelo estrutural aqui preconizado pode nos levar a considerar a reestruturação de alternativas às soluções ortodoxas. Todas estas questões, devidamente ponderadas, levantam dúvidas sobre se a expansão dos mercados mundiais nos obriga à análise dos relacionamentos verticais entre as hierarquias.");
            user.setReq("C# | Java | PHP | C");

            userDAO.Insert(user);
        }
    }

    private void generateQuestions() {
        for(int i = 0; i < 10; i++)
            questionDAO.Insert(new Question("Question"+i));
    }
}
