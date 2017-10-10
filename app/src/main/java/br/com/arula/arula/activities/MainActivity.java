
package br.com.arula.arula.activities;

import android.app.SearchManager;
import android.content.Intent;

import android.media.Image;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.LinkedList;

import java.util.List;


import br.com.arula.arula.R;
import br.com.arula.arula.RecyclerViewClickPosition;

import br.com.arula.arula.adapter.CardViewAdapterCourse;
import br.com.arula.arula.adapter.CardViewAdapterJob;

import br.com.arula.arula.adapter.CardViewAdapterJob;


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

    private List<String> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        userDAO = new UserDAO(this);
        jobDAO = new JobDAO(this);
        questionDAO = new QuestionDAO(this);


        courses = generateCourses();


        mLayoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(mLayoutManager);

        jobs = jobDAO.Read();
        users = userDAO.Read();
        questions = questionDAO.Read();


        generateQuestions();

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
                    for(Job j:  jobDAO.Read()) {
                        jobs.add(j);
                    }
                    loadListJobs();
                    controlNavigation = 1;
                    return true;
                case R.id.navigation_jobsForUser:
                    jobs.clear();
                    int cont = 0;
                    for(Job j:  jobDAO.Read()) {
                        if(cont%2==0)
                            jobs.add(j);
                        cont++;
                    }
                    loadListJobs();
                    controlNavigation = 2;
                    return true;
                case R.id.navigation_questions:
                    questions.clear();
                    for(Question q : questionDAO.Read())
                        questions.add(q);

                    loadListCourses();

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


//        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.menu_busca));
//        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

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
                generateCourses();
                generateQuestions();
                loadListCourses();

                break;
            case R.id.menu_profile:
                Intent profile = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(profile);
                break;
//            case R.id.menu_busca:
//
//                break;
//            case R.id.menu_configuracao:
//                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
//                startActivity(settings);
//                break;




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
            String course = courses.get(position);
            Intent intentQuestionActivity = new Intent(MainActivity.this, QuestionActivity.class);
            intentQuestionActivity.putExtra("course", course);
            intentQuestionActivity.putExtra("count", 0);
            intentQuestionActivity.putExtra("corrects", 0);
            startActivity(intentQuestionActivity);
        } else if(controlNavigation == 4) {
            User user = users.get(position);
            Intent intentUserActivity = new Intent(MainActivity.this, UserActivity.class);
            intentUserActivity.putExtra("user", user);
            startActivity(intentUserActivity);
        }
    }

    public void loadListJobs() {
        mAdapter = new CardViewAdapterJob(jobDAO.Read(), this);
        list.setAdapter(mAdapter);
    }

    public void loadListUsers() {
        mAdapter = new CardViewAdapterUser(users, this);
        list.setAdapter(mAdapter);
    }


    public void loadListCourses() {
        mAdapter = new CardViewAdapterCourse(courses, this);
        list.setAdapter(mAdapter);
    }

    public void generateJobs() {
        List<Job> jobsAux = new ArrayList<>();

        jobsAux.add(new Job("Full Stack Developer", "IBM" , 10000.0, "PJ", "8h - 17h", "Desenvolvimento com programação funcional, com: JavaScript, NodeJs, ES6, ReactJS, Redux e MongoDb. Experiência na área. Habilidades no trabalho em equipe e colaboração com equipes externas. Método ágil, com desenvolvimentos de API, Rest e Post. Habilidades com melhores práticas de codificação, reutilização de software TDD, BDD e Git.","JAVASCRIPT | GIT | NODEJS | SCRUM | GO", "1"));
        jobsAux.add(new Job("Analista Desenvolvedor", "Mercado Livre", 8500.0, "CLT", "9h - 18h", "Análise e desenvolvimento de software. Integração e colaboração com equipe back e front. Desenvolvimento de end-point e testes.", "JAVASCRIPT | NODE.JS | ES6 | MongoDb | Rest/API", "2"));
        jobsAux.add(new Job("Especialista Cisco", "Cisco do Brasil", 4500.0, "CLT", "8h - 17h", "Analisar configurações no NX OS, realizar troubleshooting, sugerir alterações, implementá- las, conduzir reuniões, atuar nas janelas de configurações e realizar o suporte técnico no dia- a-dia", "NEXUS | CISCO | REDES DE COMPUTADORES", "3"));
        jobsAux.add(new Job("Programador .NET Pleno", "GVT",3200.0 , "CLT", "9h - 18h", "Irá desenvolver em .NET em nosso cliente, manutenção e testes. Experiência em desenvolvimento de sistemas web com tecnologias como: .NET C#, HTML, JavaScript+jQuery, atuação com banco de dados Oracle e SQL. Experiência com projetos para instituições financeiras. Ensino Superior em Ciências da Computação ou Sistemas da Informação. Conhecimento em metodologia Ágil (SCRUM) e básico em UML.", "PLATAFORMA .NET | JAVASCRIPT | SCRUM | HTML | SQL-SERVER", "4"));
        jobsAux.add(new Job("Analista Funcional", "Banco Intermedium", 4500.0, "CLT", "9h - 18h", "Realizar análise funcional, análises de projetos e apoio no ciclo de vida dos projetos e buscando melhorias dos processos. Experiência em mapeamento de processos, levantamento de requisitos do negócio e técnico. Execução de teste unitários e integrados para validação das entregas de projetos", "SCRUM | JAVA | JAVASCRIPT | MongoDB", "6"));
        jobsAux.add(new Job("Analista Desenvolvedor IOS", "Instagram", 7200.0 , "PJ", "9h - 18h", "Projetar e desenvolver aplicativos mobile iOS (iPhone e iPad) com integração a sistemas, serviços e banco de dados. Graduação cursando em Sistemas de Informação, Engenharia de Software, Ciência da Computação, Engenharia da Computação e afins. Vivência em linguagem de programação IOS - Swift, ferramenta de programação IOS - XCode, Json/XML, APIs RESTful e SOAP. Vivência em GIT (TFS, Jira, GitHub e/ou outros), adaptação de layouts de iPhone e IPad, vivência em push notification, geração de certificados IOS, em publicação de aplicativos na loja do Apple Store.", "IOS | XCODE | SOAP | GIT | SQL", "8"));
        jobsAux.add(new Job("DBA", "Nubank", 12700.0, "PJ", "8h - 17h", "Atuar com criação, manutenção e gestão do banco de dados, extração de dados e geração de querys. Operacionalizar as atividades da área de gestão de dados executando a sustentação das rotinas de cargas de bases de dados garantindo os prazos estabelecidos, com constante avaliação dos resultados gerados nos processos rotineiros. Ensino Superior completo na área de Tecnologia da Informação. Conhecimento em DBA, Sybase ASE e IQ em ambientes Linux e Solaris, SQL server 2005, 2008 e 2012. Desejável certificação Sybase IQ15 Administrator Professional e Certificação ITIL V3 Foundation", "LINUX | SYSBASE ASE | CERTIFICAÇÕES-ITIL-MVP", "9"));
        jobsAux.add(new Job("Analista de Infraestrutura", "Uber", 6500.0 , "PJ", "9h - 18h", "Atuar com levantamento de demandas e desenho de arquitetura, atuação em projetos de infraestrutura, sobretudo em ambientes VMware. Atuar com gerenciamento e configuração de recursos avançados VMware. Conhecimento em VROPS (Realize Operations IT Operations Management)", "LINUX | VMware | Debian | CISCO", "10"));
        jobsAux.add(new Job("Técnico em Informática", "Kibom", 1300.0 , "CLT", "8h - 17h", "Configuração e instalação de máquinas, notebooks e servidores.", "TÉCNICO EM INFORMÁTICA", "5"));
        jobsAux.add(new Job("Assistente de Design Júnior", "Meu Sucesso", 970.0 , "CLT", "8h - 17h", "Dar apoio na criação de logomarcas, manual de identidade visual, criação de imagens para mídias sociais, banners, layouts e website. ", "UX | UI | SCRUM | COREL DRAW", "7"));


        for(Job j : jobsAux)
           jobDAO.Insert(j);

        jobs = jobsAux;
    }

    public void generateUsers() {
        List<User> usersAux = new ArrayList<>();
        usersAux.add(new User("Luiz Fernando", "Engenharia da Computação", 600.0, "Tenho grande experiência com desenvolvimento e busco aprender novas tecnologias.", "C# | Python | Java", "4"));
        usersAux.add(new User("Angela Maria", "Engenharia Mecatrônica", 1200.0, "Tenho interesse por tecnolgia e quero adquirir novos conhecimentos.", "C | Java", "1"));
        usersAux.add(new User("Fernanda Carla", "Engenharia Elétrica", 100.0, "Tenho experiência ao trabalhar com desenvolvimento back-end, mas desejo migrar para front-end.", "C | Go", "3"));
        usersAux.add(new User("Gustavo Ferreira", "Engenharia Elétrica", 400.0, "Tenho grande experiência com desenvolvimento móvel e desejo me especializar nisso.", "Kotlin | Java | Android | Python", "5"));
        usersAux.add(new User("Matheus da Cruz", "Engenharia Mecatrônica", 6600.0, "Desenvolvedor back-end e apaixonado por tecnologia.", "PHP | Go | Python", "6"));
        usersAux.add(new User("Gisele Prado", "Engenharia da Computação", 13000.0, "Desejo entrar no mercado de trabalho e colocar em prática o que me foi ensinado.", "C | Java | C#", "7"));
        usersAux.add(new User("Ronaldo Dias", "Engenharia Elétrica", 5400.0, "Busco uma recolocação no mercado de desenvolvimento.", "PHP | C | Python", "8"));
        usersAux.add(new User("Heitor Augusto", "Engenharia Mecatrônica", 300.0, "Tenho 17 anos de experiência e quero trabalhar com novas tecnologias.", "Python | NodeJS", "9"));
        usersAux.add(new User("Patrícia Gonçalves", "Engenharia da Computação", 1500.0, "Procuro por um estágio que me faça entender todas as tecnologias da empresa", "C# | Go", "2"));
        usersAux.add(new User("Otávio Cardoso", "Engenharia da Computação", 9500.0, "Desejo entrar no mercado de trabalho e colocar em prática o que me foi ensinado.", "JavaScript | Java | C#", "10"));

        for(User u : usersAux)
            userDAO.Insert(u);

    }

    private List<String> generateCourses() {
        if(courses != null)
            courses.clear();

        List <String> coursesL = new ArrayList<>();

        coursesL.add("Engenharia de Alimentos");
        coursesL.add("Engenharia Civil");
        coursesL.add("Engenharia da Computação");
        coursesL.add("Engenharia Elétrica");
        coursesL.add("Engenharia Mecânica");
        coursesL.add("Engenharia Mecatrônica");
        coursesL.add("Engenharia de Produção");
        coursesL.add("Engenharia Química");

        return coursesL;
    }

    private void generateQuestions() {
        for(Question q: questions)
            questionDAO.Remove(q);

        questionDAO.Insert(new Question("(ENADE 2014) Considerando a concepção de um novo projeto de indústria de " +
                "processamento de cana-de-açúcar visando à produção de açúcar e álcool, avalie as " +
                "afirmações a seguir.\n" +
                "I. O profissional responsável pela escolha dos equipamentos que " +
                "irão compor a linha de produção deve identificar a capacidade de produção " +
                "dos equipamentos em regime normal de funcionamento, sem a " +
                "necessidade de se preocupar com seus índices de rendimento.\n" +
                "II. Para o processo do açúcar, o arranjo físico deve facilitar o fluxo " +
                "de produção e impedir o fluxo cruzado, já que pode acontecer a " +
                "contaminação do produto.\n" +
                "III. Para a produção do álcool, as dornas de fermentação devem ser " +
                "projetadas considerando-se a agitação constante e visando à incorporação " +
                "de oxigênio ao meio.\n" +
                "IV. As determinações da engenharia, conjuntamente com as " +
                "necessidades do processo, influenciam diretamente o tipo de efluente " +
                "industrial que será produzido e as suas respectivas formas de tratamento e " +
                "utilização.\n" +
                "É correto apenas o que se afirma em: \n", "Engenharia de Alimentos", "I e III.", "I e IV.", "I e IV.", "I e IV.", "I e IV.", 2));
        questionDAO.Insert(new Question("(ENADE 2014) Várias indústrias de alimentos utilizam análises físico-químicas\n" +
                "e análises bioquímicas para avaliar a quantidade da matéria-prima, para mensurar\n" +
                "alguns parâmetros de processos e também a qualidade do produto.\n" +
                "Nesse contexto, avalie as afirmações a seguir.\n" +
                "I. Na recepção do leite em laticínios, é importante fazer o teste do\n" +
                "alizarol para avaliar se a acidez do leite está dentro da faixa especificada\n" +
                "na legislação brasileira.\n" +
                "II. Uma forma de avaliar a eficiência do branqueamento aplicado\n" +
                "em produtos vegetais antes do seu processamento é verificar se enzimas\n" +
                "como a catalase e a peroxidase foram inativadas.\n" +
                "III. Na pasteurização do leite, tanto a enzima peroxidase quanto a\n" +
                "fosfatase alcalina devem ser inativadas para comprovar a eficiência do\n" +
                "tratamento térmico aplicado.\n" +
                "IV. A composição química do alimento deve ser a mesma daquela\n" +
                "declarada no rótulo do produto, sendo aceitável uma variação máxima de\n" +
                "5%, que é inerente a possíveis erros de análises.\n" +
                "V. A redução do valor do pH de um produto durante o\n" +
                "armazenamento pode ser decorrente do crescimento de micro-organismos.\n" +
                "É correto apenas o que se afirma em: \n", "Engenharia de Alimentos", "I, II e IV.", "I, II e V.", "I, III e IV.", "II, III e V.", "III, IV e V.", 1));
        questionDAO.Insert(new Question("(ENADE 2014) Os tratamentos térmicos são usualmente empregados no\n" +
                "processamento de alimentos com a finalidade de inativar micro-organismos e enzimas,\n" +
                "prolongando a vida útil dos produtos. Considerando a ampla utilização dos processos\n" +
                "térmicos na indústria de alimentos, avalie as afirmações a seguir.\n" +
                "I. A pasteurização ê um tratamento térmico brando que tem como\n" +
                "finalidade inativar micro-organismos patogênicos e deteriorantes, capazes\n" +
                "de se desenvolver no alimento nas condições de armazenamento.\n" +
                "II. Alimentos de alta acidez, que apresentam pH 4,5, exigem\n" +
                "tratamentos térmicos mais intensos quando visam à eliminação do\n" +
                "Clostridium botulinum, micro-organismo produtor de toxinas letais.\n" +
                "III. A esterilização visa à destruição de todos os micro-organismos\n" +
                "patogênicos e deteriorantes que possam se desenvolver no alimento sob\n" +
                "condições normais de estocagem, e tem como exigência o armazenamento\n" +
                "refrigerado do produto.\n" +
                "IV. O estabelecimento do binômio tempo/temperatura de tratamento\n" +
                "térmico deve levar em consideração fatores como resistência dos micro-\n" +
                "organismos e enzimas existentes no alimento, o pH do alimento e as\n" +
                "condições de aquecimento. V. O branqueamento, comumente usado em\n" +
                "frutas e hortaliças, tem como objetivo principal inativar enzimas associadas\n" +
                "a processos de deterioração, minimizando alterações sensoriais e\n" +
                "nutricionais durante a estocagem.\n" +
                "É correto apenas o que se afirma em: \n", "Engenharia de Alimentos", "I, II e III.", "I, III e V.", "I, III e V.", "I, III e V.", "I, III e V.", 2));
        questionDAO.Insert(new Question("(ENADE 2014) A corrosão do aço em estruturas de concreto armado\n" +
                "considerada uma manifestação patológica não muito rara nas construções.\n" +
                "Nesse contexto, avalie as seguintes afirmações.\n" +
                "I. A corrosão do aço é um processo eletroquímico que se inicia e\n" +
                "prossegue devido a características construtivas da estrutura de concreto\n" +
                "armado, além de condições climáticas e de exposição.\n" +
                "II. A colocação de espaçadores que auxiliam o correto\n" +
                "posicionamento das armaduras dentro das formas é uma prática que\n" +
                "procura garantir o cobrimento especificado e a vida útil prevista das\n" +
                "estruturas em projeto.\n" +
                "III. Os produtos da corrosão são expansivos e podem ocasionar\n" +
                "fissuras em vigas e pilares e, ate mesmo, o deslocamento de pedaços de\n" +
                "concreto.\n" +
                "IV. A utilização de adições minerais em concretos pouco influencia a\n" +
                "deterioração por corrosão da armadura.\n" +
                "É correto que se afirma em: \n", "Engenharia Civil", "I, II e III, apenas.", "I, II e IV, apenas.", "I, II e IV, apenas.", "I, II e IV, apenas.", "I, II e IV, apenas.", 0));
        questionDAO.Insert(new Question("(ENADE 2014) Visando o dimensionamento de um bueiro em uma ferrovia, o\n" +
                "engenheiro encarregado do estudo hidrológico optou pela utilização do Método\n" +
                "Racional, cuja equação i dada por Q = C.I. A, em que Q é a vazão, C é o coeficiente\n" +
                "de escoamento superficial, I é a intensidade da chuva na região e A é a área da bacia\n" +
                "hidrográfica. Nesse estudo, foram coletadas as informações a seguir.\n" +
                "\uF0B7 Em 40% da área da bacia, o valor de C a ser adotado é igual a\n" +
                "0,2 e, no restante da área, 0,5.\n" +
                "\uF0B7 A intensidade da chuva na região (em mm/h) obtida pela\n" +
                "equação I = 400TR0,5/t, em que TR é o tempo de recorrência ou período de\n" +
                "retorno (em anos), adotado igual a 25 anos; t é o tempo de duração da chuva\n" +
                "ou tempo de concentração (em minutos), estimado em 20 minutos, em razão\n" +
                "das características da bacia hidrográfica.\n" +
                "\uF0B7 A área da bacia hidrográfica é igual a 0,72.\n" +
                "A partir dos dados coletados no estudo hidrográfico, a vazão Q de projeto para\n" +
                "o dimensionamento do bueiro, em litros por segundo, será de: \n", "Engenharia Civil", "7,6", "14,4.", "25,2.", "27,4.", "36,0.", 0));
        questionDAO.Insert(new Question("(ENADE 2014) Visando o dimensionamento de um bueiro em uma ferrovia, o\n" +
                "engenheiro encarregado do estudo hidrológico optou pela utilização do Método\n" +
                "Racional, cuja equação i dada por Q = C.I. A, em que Q é a vazão, C é o coeficiente\n" +
                "de escoamento superficial, I é a intensidade da chuva na região e A é a área da bacia\n" +
                "hidrográfica. Nesse estudo, foram coletadas as informações a seguir.\n" +
                "\uF0B7 Em 40% da área da bacia, o valor de C a ser adotado é igual a\n" +
                "0,2 e, no restante da área, 0,5.\n" +
                "\uF0B7 A intensidade da chuva na região (em mm/h) obtida pela\n" +
                "equação I = 400TR0,5/t, em que TR é o tempo de recorrência ou período de\n" +
                "retorno (em anos), adotado igual a 25 anos; t é o tempo de duração da chuva\n" +
                "ou tempo de concentração (em minutos), estimado em 20 minutos, em razão\n" +
                "das características da bacia hidrográfica.\n" +
                "\uF0B7 A área da bacia hidrográfica é igual a 0,72.\n" +
                "A partir dos dados coletados no estudo hidrográfico, a vazão Q de projeto para\n" +
                "o dimensionamento do bueiro, em litros por segundo, será de: \n", "Engenharia Civil", "empilhar, próximo aos apoios da laje, 8 sacos de cimento de 0,5\n" +
                "kN, um em cima do outro.", "empilhar, próximo aos apoios da laje, 4 sacos de cimento de 0,5\n" +
                "kN, um em cima do outro.", "posicionar um caixote de 1,0 m x 1,0 m x 0,40 m de madeira\n" +
                "sobre a região central da laje e enchê-lo com areia grossa", "posicionar sobre a região central da laje uma piscina\n" +
                "plástica, com área superior a 1,0 m², e encher até que se atinja 0,40 m de\n" +
                "lâmina d’água.", "posicionar sobre a região próxima aos apoios da laje uma\n" +
                "piscina plástica, com área superior a 1,0 m², e encher até que se atinja 0,40 m\n" +
                "de lâmina d’água.\n", 3));
        questionDAO.Insert(new Question("(ENADE 2014) A compressão de dados pode ser realizada por intermédio de\n" +
                "diversos algoritmos de compressão, reduzindo a quantidade de bits para representar\n" +
                "um conjunto de dados. A compressão de imagem ia forma de armazenar informações\n" +
                "visuais mais compactamente. A maioria desses métodos considera a identificação e o\n" +
                "uso de estruturas e redundâncias que existem nos dados da imagem. Os tipos de\n" +
                "redundância encontrados nas imagens são ligados à codificação de tons ou cor,\n" +
                "redundância da informação inter-pixel, espectral e psicovisual.\n" +
                "CONCI, A.: AZEVEDO, E.: LETA, F.R. Computação Gráfica: teoria e prática. v. 2, Rio de Janeiro:\n" +
                "Elsevier, 2008 (adaptado)\n" +
                "Nesse contexto, avalie as afirmações a seguir.\n" +
                "I. A redundância de codificação de tons ou cor ocorre quando os\n" +
                "níveis de cinza ou as cores de uma imagem são codificados com mais\n" +
                "símbolos de codificação que o necessário. A redundância inter-pixel\n" +
                "(redundâncias espaciais) são as resultantes das relações geométricas ou\n" +
                "estruturais entre os objetos na imagem.\n" +
                "II. A redundância espectral é a que ocorre em imagens com mais\n" +
                "de uma faixa espectral, quando os valores espectrais para a mesma\n" +
                "posição na matriz de pixels de cada banda são correlacionados. Nesse\n" +
                "caso, apenas um canal precisa ser armazenado com mais detalhes.\n" +
                "III. As redundâncias psicovisuais ou baseadas na percepção são\n" +
                "aquelas relacionadas ao fato de o sistema visual humano responder com a\n" +
                "mesma sensibilidade a todas as informações visuais. Tais informações não\n" +
                "podem ser eliminadas sem prejudicar significativamente a qualidade da\n" +
                "imagem.\n" +
                "É correto o que se afirma em: \n", "Engenharia da Computação", "I, apenas.", "III, apenas.", "I e II, apenas.", "II e III, apenas", "I, II e III.\n", 2));
        questionDAO.Insert(new Question("(ENADE 2014) O código a seguir mostra um programa escrito na linguagem de\n" +
                "programação Java.\n" +
                "public class Java {\n" +
                "   private static void trocaB (String a, String b) {\n" +
                "        String tmp = a;\n" +
                "        a = b;\n" +
                "        b = tmp;\n" +
                "   }\n" +
                "   private static void troca (int array[], String a){\n" +
                "       for (int x = 0; x < array.length. x++){\n" +
                "       array[x] = array[x] * Integer.valueOf(a);\n" +
                "       }\n" +
                "    }\n" +
                "   public static void main (String[] args) {\n" +
                "       int array[] = { 1, 2, 3, 4, 5};\n" +
                "       String a = “2”, b = “5”;\n" +
                "       trocaB (a, b);\n" +
                "       trocaC (array, a);\n" +
                "       System.out.print(a + “ “ + b + “ “);\n" +
                "       for (int x = 0; x < array.length; x++){\n" +
                "           System.out.print(array[x] + “ “);\n" +
                "       }\n" +
                "   }\n" +
                "}\n" +
                "Após ser executado o código, o valor impresso na saída padrão do usuário\n" +
                "será: \n", "Engenharia da Computação", "5 2 5 10 15 20 25.", "2 5 2 4 6 8 10.", "5 2 2 4 6 8 10.", "5 2 1 2 3 4 5.", "2 5 1 2 3 4 5.", 1));
        questionDAO.Insert(new Question("(ENADE 2014) Suponha que, para armazenar exatamente 999 999 chaves de\n" +
                "um índice, um engenheiro de computação tenha escolhido a estrutura de uma árvore \n" +
                "B, de grau mínimo 5, com todos os nós completos. Nessa situação, a profundidade\n" +
                "dessa árvore é igual a: \n", "Engenharia da Computação", "4", "5", "6", "7", "8", 1));
        questionDAO.Insert(new Question("(ENADE 2014) Até alguns anos atrás, bastava ligar um liquidificador na cozinha\n" +
                "enquanto uma TV estava ligada na sala para perceber a interferência, mas o\n" +
                "desenvolvimento de tecnologias já conseguiu diminui r, ou até mesmo eliminar a\n" +
                "interferência entre alguns equipamentos. Para evitar o mau funcionamento dos\n" +
                "eletroeletrônicos foram criadas blindagens, geralmente feitas com algum tipo de metal.\n" +
                "Disponível em <http://www.osetoreletrico.com.br> Acesso em 02 out. 2014 (adaptado).\n" +
                "A interferência citada no texto é de origem eletromagnética e pode causar\n" +
                "degradação no desempenho de um equipamento eletroeletrônico devido à geração de\n" +
                "campos eletromagnéticos no ambiente.\n" +
                "No que se refere aos aspectos relacionados a esse fenômeno, avalie as\n" +
                "afirmações a seguir.\n" +
                "I. As causas de um problema de interferência eletromagnética\n" +
                "podem ocorrer tanto dentro como fora do equipamento.\n" +
                "II. As linhas de transmissão de energia, descargas elétricas e\n" +
                "lâmpadas fluorescentes são consideradas causadoras de interferência\n" +
                "eletromagnética.\n" +
                "III. Quando dois ou mais dispositivos contribuem simultaneamente\n" +
                "para a interferência eletromagnética, diz-se que eles possuem\n" +
                "compatibilidade magnética.\n" +
                "IV. Aterramento, blindagem e filtragem são soluções que podem ser\n" +
                "utilizadas no projeto com o intuito de se controlar ou suprimir a interferência\n" +
                "eletromagnética.\n" +
                "É correto apenas o que se afirma em: \n", "Engenharia Elétrica", "I e IV.", "II e III.\n", "III e IV.", "I, II e III.", "I, II e IV", 4));
        questionDAO.Insert(new Question("(ENADE 2014) A levitação magnética (maglev) é uma forma de levitar objetos,\n" +
                "a partir de campos magnéticos, de modo silencioso e sem necessidade do uso de\n" +
                "combustíveis. Países, como Japão e Alemanha, têm investido bilhões de dólares em\n" +
                "pesquisas e desenvolvimento nessa área, principalmente com foco no trem maglev.\n" +
                "Sobre os fenômenos eletromagnéticos envolvidos nesse tipo de levitação,\n" +
                "assinale a alternativa correta.\n", "Engenharia Elétrica", "Os campos magnéticos necessários para se obter a levitação\n" +
                "magnética, em geral, são pouco intensos.", "Materiais com propriedade supercondutora não são indicados\n" +
                "nesse tipo de levitação, uma vez que eles repelem o campo magnético", "Para que a levitação seja garantida, é necessário que a força\n" +
                "repulsiva sobre um objeto diamagnético seja menor do que o peso desse\n" +
                "objeto", "A levitação emprega o diamagnetismo, propriedade que\n" +
                "alguns materiais possuem de se magnetizarem em oposição ao campo\n" +
                "magnético aplicado, repelindo-se mutuamente.", "Para a aplicabilidade em veículos (como trens, por exemplo), a\n" +
                "levitação magnética suficiente para a locomoção, sendo dispensável o uso de\n" +
                "um motor de propulsão.", 3));
        questionDAO.Insert(new Question("(ENADE 2014) As lâmpadas a LED (Light Emitting Diode) possuem vida útil\n" +
                "muito superior aos outros tipos de lâmpadas disponíveis no mercado. Além disso, elas\n" +
                "apresentam um índice de reprodução de cor (RC) considerado aceitável mesmo pelos\n" +
                "padrões mais exigentes de iluminação de interiores. Oferecem, ainda, eficiência\n" +
                "luminosa bastante superior às fluorescentes compactas e às incandescentes. Com\n" +
                "tamanhas vantagens, verifica-se que existe uma tendência da iluminação tradicional,\n" +
                "comporta por lâmpadas incandescentes, dicroicas e fluorescentes tubulares ou\n" +
                "compactas, ser gradualmente substituída pelas lâmpadas de LED.\n" +
                "Com relação aos LED, avalie as afirmações a seguir.\n" +
                "I. A diferença entre os semicondutores intrínsecos e extrínsecos é\n" +
                "que os intrínsecos apresentam impurezas dopantes inseridas de forma a\n" +
                "ficarem rendas dentro do cristal semicondutor, e os extrínsecos possuem\n" +
                "dopantes posicionados externamente ao cristal semicondutor.\n" +
                "II. Quando a junção P-N de um LED é diretamente polarizada,\n" +
                "elétrons e lacunas se recombinam, resultando na emissão de fótons.\n" +
                "III. A luz emitida pelo LED tem comprimento de onda relacionado\n" +
                "ao tipo de material utilizado na dopagem do semicondutor, por exemplo,\n" +
                "gálio, alumínio, arsênio, fósforo, índio ou nitrogênio.\n" +
                "É correto o que se afirma em: \n", "Engenharia Elétrica", "I, apenas.", "I, apenas.", "I, apenas.", "I, apenas.", "I, apenas.", 3));
        questionDAO.Insert(new Question("(ENADE 2014) As turbinas Pelton são turbinas de ação que recebem um jato\n" +
                "de fluido proveniente de um injetor. Esse jato incide tangencialmente ao rotor em pás\n" +
                "distribuídas ao longo de sua periferia. Sabendo que a força aplicada nas pás é\n" +
                "proporcional à variação da quantidade de movimento do fluido (segunda lei de\n" +
                "Newton), avalie as afirmações a seguir.\n" +
                "I. Para uma mesma velocidade do fluído na saída no bocal do\n" +
                "injetar, quanto maior o diâmetro do rotor, maior será a velocidade angular.\n" +
                "II. Para uma mesma velocidade do fluido na saída no bocal do\n" +
                "injetor, quanto maior o diâmetro do rotor, maior será o torque.\n" +
                "III. Para um mesmo formato e tamanho de pás, quanto maior a\n" +
                "velocidade do fluido na saída do bocal do injetor, maior será a força\n" +
                "tangencial.\n" +
                "É correto o que se afirma em: \n", "Engenharia Mecânica", "I, apenas", "III, apenas", "I e II, apenas.", "II e III, apenas.", "I, II e III.", 3));
        questionDAO.Insert(new Question("(ENADE 2014) Um metal, em sua condição encruada, possui energia interna\n" +
                "elevada em relação ao metal não-deformado plasticamente. Aumentando-se a\n" +
                "temperatura, existe a tendência de o metal retornar à condição mais estável de menor\n" +
                "energia interna. O tratamento térmico para se obter esse efeito é denominado\n" +
                "recozimento e, além da recuperação da estrutura cristalina do metal, esse tratamento \n" +
                "provoca a diminuição da resistência mecânica, bem como a elevação da ductilidade.\n" +
                "Nesse contexto, avalie as asserções a seguir e a relação proposta entre elas.\n" +
                "I. A fabricação de latas de alumínio de refrigerantes é realizada por\n" +
                "estampagem em mais de uma etapa, ocorrendo a necessidade de se\n" +
                "realizar tratamento térmico de recozimento entre as etapas para fornecer\n" +
                "ao metal ductilidade suficiente para a continuidade do processo.\n" +
                "PORQUE\n" +
                "II. A estampagem das latas de refrigerantes é realizada a frio,\n" +
                "ocorrendo encruamento em cada etapa, com alteração das propriedades\n" +
                "mecânicas do material da chapa.\n" +
                "A respeito dessas asserções, assinale a opção correta.\n", "Engenharia Mecânica", "As asserções I e II são proposições verdadeiras, e a II é uma\n" +
                "justificativa correta da I.\n", "As asserções I e II são proposições verdadeiras, mas a II não é\n" +
                "uma justificativa correta da I.\n", "As asserções I e II são proposições verdadeiras, mas a II não é\n" +
                "uma justificativa correta da I.\n", "A asserção I é uma proposição falsa, e a II é uma proposição\n" +
                "verdadeira.\n", "As asserções I e II são proposições falsas.", 3));
        questionDAO.Insert(new Question("(ENADE 2014) Um fabricante de bicicletas de velocidade está mudando o\n" +
                "material de fabricação dos seus produtos para tubos de parede fina de alumínio da\n" +
                "classe 6XXX, que têm como elementos principais o silício e o magnésio. Essa\n" +
                "mudança decorre da necessidade de redução de peso desse tipo de bicicleta para\n" +
                "melhorar seu desempenho. Os quadros de aço carbono fabricados anteriormente\n" +
                "eram soldados, usando-se eletrodos revestidos. A utilização da mesma técnica,\n" +
                "mantendo-se os parâmetros tradicionais, não funcionou bem para o alumínio.\n" +
                "Na situação descrita acima, o processo mais adequado para atender a\n" +
                "demanda de produção do fabricante é o: \n", "Engenharia Mecânica", "TIG.", "MIG.", "MAG.", "oxiacetileno.", "eletrodo revestido com diminuição do seu diâmetro e utilização\n" +
                "de transformador de solda convencional.", 0));
        questionDAO.Insert(new Question("(ENADE 2014) Sensores ultrassônicos são usados na medição de grandezas\n" +
                "como distância e nível. Alguns desses sensores emitem um sinal na frequência de 40\n" +
                "kHz que, ao atingir um objeto, retorna e, quando captado, permite calcular a distância\n" +
                "do objeto ao sensor. Nesse contexto, avalie as afirmações a seguir.\n" +
                "I. O cálculo da distância pode ser obtido usando-se equações da\n" +
                "mecânica clássica.\n" +
                "II. Para evitar uma interferência entre o sinal enviado e sinais\n" +
                "espúrios advindos de fontes eletromagnéticas, deve-se usar um filtro.\n" +
                "III. No caso especifico de um sensor ultrassônico, o cálculo da\n" +
                "distância baseia-se na variação da velocidade e, dessa forma, no efeito\n" +
                "doppler.\n" +
                "IV. Para uma leitura adequada do sinal desse sensor em um\n" +
                "sistema de aquisição de dados, deve-se utilizar uma taxa de amostragem\n" +
                "de no mínimo 80 kHz de forma a evitar o efeito aliasing.\n" +
                "É correto apenas o que se afirma em: \n", "Engenharia Mecatrônica", "I e IV.", "II e III.", "III e IV", "I, II e III.\n", "I, II e IV.\n", 0));
        questionDAO.Insert(new Question("(ENADE 2014) Um engenheiro de mecatrônica necessita especificar um\n" +
                "transdutor de temperatura para um projeto de controle automático de um forno. É\n" +
                "requisito de projeto que o controlador lógico programável (CLP) esteja localizado na\n" +
                "sala de comando, a qual está a uma distância de 300 metros do forno.\n" +
                "Nesse contexto, considerando-se que o engenheiro escolheu um transdutor\n" +
                "com transmissão no padrão 4 mA a 20 mA, avalie as seguintes asserções e a relação\n" +
                "proposta entre elas.\n" +
                "I. A transmissão de sinal de transdutores distantes do CLP é mais\n" +
                "segura no padrão 4 mA a 20 mA, que nos padrões 0 V a 5 V ou 0 mA a 20\n" +
                "mA.\n" +
                "PORQUE\n" +
                "II. No padrão 4 mA a 20 mA é possível identificar perda de sinal\n" +
                "nos cabos de transmissão, como, por exemplo, em problemas de\n" +
                "rompimento de cabo ou desconexão com o CLP.\n" +
                "A respeito dessas asserções, assinale a opção correta.\n", "Engenharia Mecatrônica", "As asserções I e II são proposições verdadeiras, e a II é uma\n" +
                "justificativa correta da I.", "As asserções I e II são proposições verdadeiras, mas a II não é\n" +
                "uma justificativa correta da I.", "A asserção I é uma proposição verdadeira, e a II é uma\n" +
                "proposição falsa.", "A asserção I ê uma proposição falsa, e a II é uma proposição\n" +
                "verdadeira.\n", "As asserções I e II são proposições falsas.\n", 0));
        questionDAO.Insert(new Question("(ENADE 2014) Um veículo aéreo não tripulado (VANT), também chamado UAV\n" +
                "(do inglês Unmaned Aerial Vehicle), é todo e qualquer tipo de aeronave que não\n" +
                "necessita de pilotos embarcados para ser guiada. Esses veiculos são controlados por\n" +
                "meios eletrônicos e computacionais, sob a supervisão humana remota ou sem a sua\n" +
                "intervenção.\n" +
                "A respeito da navegação autônoma dos VANTs, avalie as afirmações a seguir.\n" +
                "I. O GPS tem como funcionalidade básica determinar coordenadas\n" +
                "geográficas.\n" +
                "II. O acelerômetro tem como funcionalidade básica determinar\n" +
                "variação na orientação.\n" +
                "III. O giroscópio tem como funcionalidade básica determinar\n" +
                "variação no posicionamento.\n" +
                "É correto o que se afirma em", "Engenharia Mecatrônica", "I, apenas. ", "III, apenas", "I e II, apenas.", "II e III, apenas.", "I, II e III.", 0));
        questionDAO.Insert(new Question("(ENADE 2014) Em projetos de desenvolvimento de novos produtos considerase\n" +
                "que a participação de uma equipe profissional multidisciplinar contribui para o\n" +
                "sucesso do projeto. Com relação à participação do representante da manufatura na\n" +
                "equipe do projeto, espera-se que sejam usados os princípios do DFMA (Design for\n" +
                "Manufacturing and Assembly) nas fases do desenvolvimento. \n" +
                "BOOTHROYD. G; DEWHURST. P; KNIGHT, W. A. Product design for manufacture and\n" +
                "assembly. 3 ed. Boca Raton: Taylor and Francis Group, LCC, 2011 (adaptado).\n" +
                "Considerando as especificidades e princípios do DFMA, avalie as afirmações a\n" +
                "seguir.\n" +
                "I. É esperado que o produto resultante passe por menos etapas\n" +
                "de produção e, como consequência, tenha menor tempo de produção.\n" +
                "II. O design do produto, resultante do projeto, terá maior grau de\n" +
                "novidade.\n" +
                "III. A participação do representante da manufatura reduzirá os\n" +
                "custos de execução do projeto.\n" +
                "IV. É almejado que o novo produto apresente menor número de\n" +
                "componentes ou peças.\n" +
                "É correto apenas o que se afirma em", "Engenharia de Produção", "I e IV.", "II e III.", "III e IV.", "I, II e III", "I, II e IV.\n", 0));
        questionDAO.Insert(new Question("(ENADE 2014) O conceito de produtividade global considera que os benefícios\n" +
                "obtidos pela melhoria dos processos devem ser mensurados no conjunto,\n" +
                "considerando os fatores econômicos, sociais e ambientais de todos os setores ou\n" +
                "departamentos da organização. Nessa lógica, as organizações que executam projetos\n" +
                "para melhorar seus ganhos globais buscam implantar os denominados Escritórios de\n" +
                "Projetos, também conhecidos como PMO, do inglês, Project Management Office.\n" +
                "PMBOK – Guia do conjunto de conhecimento de projetos. 5 ed. Project Management Institute,\n" +
                "2013 (adaptado).\n" +
                "A respeito do Escritório de Projetos, avalie as afirmações a seguir.\n" +
                "I. É uma unidade funcional responsável pela tomada de decisões\n" +
                "sobre quais projetos devem ser executados pela organização.\n" +
                "II. Tem como função prover recursos financeiros para os projetos\n" +
                "da organização.\n" +
                "III. É uma estrutura de gestão que padroniza os processos de\n" +
                "governança relacionados aos projetos.\n" +
                "IV. É responsável pela decisão de encerramento de projetos.\n" +
                "V. Facilita o compartilhamento de recursos, métodos, ferramentas e\n" +
                "técnicas nos projetos sob seu domínio. \n" +
                "É correto apenas o que se afirma em: \n", "Engenharia de Produção", "II e IV.", "III e V", "I, III e IV.", "I, II, III e V.", "I, II, IV e V.", 1));
        questionDAO.Insert(new Question("(ENADE 2014) O conceito de produtividade global considera que os benefícios\n" +
                "obtidos pela melhoria dos processos devem ser mensurados no conjunto,\n" +
                "considerando os fatores econômicos, sociais e ambientais de todos os setores ou\n" +
                "departamentos da organização. Nessa lógica, as organizações que executam projetos\n" +
                "para melhorar seus ganhos globais buscam implantar os denominados Escritórios de\n" +
                "Projetos, também conhecidos como PMO, do inglês, Project Management Office.\n" +
                "PMBOK – Guia do conjunto de conhecimento de projetos. 5 ed. Project Management Institute,\n" +
                "2013 (adaptado).\n" +
                "A respeito do Escritório de Projetos, avalie as afirmações a seguir.\n" +
                "I. É uma unidade funcional responsável pela tomada de decisões\n" +
                "sobre quais projetos devem ser executados pela organização.\n" +
                "II. Tem como função prover recursos financeiros para os projetos\n" +
                "da organização.\n" +
                "III. É uma estrutura de gestão que padroniza os processos de\n" +
                "governança relacionados aos projetos.\n" +
                "IV. É responsável pela decisão de encerramento de projetos.\n" +
                "V. Facilita o compartilhamento de recursos, métodos, ferramentas e\n" +
                "técnicas nos projetos sob seu domínio. \n" +
                "É correto apenas o que se afirma em: \n", "Engenharia de Produção", "I e III.", "II e III.", "II e IV.", "I, II e IV.", "I, III e IV.\n", 2));
        questionDAO.Insert(new Question("(ENADE 2014) O conceito de produtividade global considera que os benefícios\n" +
                "obtidos pela melhoria dos processos devem ser mensurados no conjunto,\n" +
                "considerando os fatores econômicos, sociais e ambientais de todos os setores ou\n" +
                "departamentos da organização. Nessa lógica, as organizações que executam projetos\n" +
                "para melhorar seus ganhos globais buscam implantar os denominados Escritórios de\n" +
                "Projetos, também conhecidos como PMO, do inglês, Project Management Office.\n" +
                "PMBOK – Guia do conjunto de conhecimento de projetos. 5 ed. Project Management Institute,\n" +
                "2013 (adaptado).\n" +
                "A respeito do Escritório de Projetos, avalie as afirmações a seguir.\n" +
                "I. É uma unidade funcional responsável pela tomada de decisões\n" +
                "sobre quais projetos devem ser executados pela organização.\n" +
                "II. Tem como função prover recursos financeiros para os projetos\n" +
                "da organização.\n" +
                "III. É uma estrutura de gestão que padroniza os processos de\n" +
                "governança relacionados aos projetos.\n" +
                "IV. É responsável pela decisão de encerramento de projetos.\n" +
                "V. Facilita o compartilhamento de recursos, métodos, ferramentas e\n" +
                "técnicas nos projetos sob seu domínio. \n" +
                "É correto apenas o que se afirma em: \n", "Engenharia Química", "I, apenas.", "II, apenas.", "I e III, apenas.", "II e III, apenas.\n", "I, II e III.", 2));
        questionDAO.Insert(new Question("(ENADE 2014) O conceito de produtividade global considera que os benefícios\n" +
                "obtidos pela melhoria dos processos devem ser mensurados no conjunto,\n" +
                "considerando os fatores econômicos, sociais e ambientais de todos os setores ou\n" +
                "departamentos da organização. Nessa lógica, as organizações que executam projetos\n" +
                "para melhorar seus ganhos globais buscam implantar os denominados Escritórios de\n" +
                "Projetos, também conhecidos como PMO, do inglês, Project Management Office.\n" +
                "PMBOK – Guia do conjunto de conhecimento de projetos. 5 ed. Project Management Institute,\n" +
                "2013 (adaptado).\n" +
                "A respeito do Escritório de Projetos, avalie as afirmações a seguir.\n" +
                "I. É uma unidade funcional responsável pela tomada de decisões\n" +
                "sobre quais projetos devem ser executados pela organização.\n" +
                "II. Tem como função prover recursos financeiros para os projetos\n" +
                "da organização.\n" +
                "III. É uma estrutura de gestão que padroniza os processos de\n" +
                "governança relacionados aos projetos.\n" +
                "IV. É responsável pela decisão de encerramento de projetos.\n" +
                "V. Facilita o compartilhamento de recursos, métodos, ferramentas e\n" +
                "técnicas nos projetos sob seu domínio. \n" +
                "É correto apenas o que se afirma em: \n", "Engenharia Química", "I e II.", "I e IV.", "II e III.", "I, III e IV", "II, III e IV.", 3));
        questionDAO.Insert(new Question("(ENADE 2014) O conceito de produtividade global considera que os benefícios\n" +
                "obtidos pela melhoria dos processos devem ser mensurados no conjunto,\n" +
                "considerando os fatores econômicos, sociais e ambientais de todos os setores ou\n" +
                "departamentos da organização. Nessa lógica, as organizações que executam projetos\n" +
                "para melhorar seus ganhos globais buscam implantar os denominados Escritórios de\n" +
                "Projetos, também conhecidos como PMO, do inglês, Project Management Office.\n" +
                "PMBOK – Guia do conjunto de conhecimento de projetos. 5 ed. Project Management Institute,\n" +
                "2013 (adaptado).\n" +
                "A respeito do Escritório de Projetos, avalie as afirmações a seguir.\n" +
                "I. É uma unidade funcional responsável pela tomada de decisões\n" +
                "sobre quais projetos devem ser executados pela organização.\n" +
                "II. Tem como função prover recursos financeiros para os projetos\n" +
                "da organização.\n" +
                "III. É uma estrutura de gestão que padroniza os processos de\n" +
                "governança relacionados aos projetos.\n" +
                "IV. É responsável pela decisão de encerramento de projetos.\n" +
                "V. Facilita o compartilhamento de recursos, métodos, ferramentas e\n" +
                "técnicas nos projetos sob seu domínio. \n" +
                "É correto apenas o que se afirma em: \n", "Engenharia Química", "determinar o erro antes que qualquer ação de controle possa ser\n" +
                "realizada.\n", "relacionar a sequência de uma determinada produção, podendo\n" +
                "envolver uma ou mais variáveis.\n", "exercer uma influência direta no processo, promovendo a\n" +
                "mudança requerida na variável controlada.", "realizar a medida e a conversão de uma variável em termos de\n" +
                "uma informação elétrica ou pneumática.", "analisar a medida do erro e determinar a necessidade e o tipo de\n" +
                "ação a ser realizada para controlar o processo.", 2));



        for(Question q : questionDAO.Read())
            questions.add(q);

        courses = generateCourses();
    }
}
