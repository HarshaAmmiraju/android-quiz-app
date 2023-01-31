package com.harshaapps.quizdemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView tv_question,tv_option1,tv_option2,tv_option3,tv_option4,tv_timer,tv_score;


    ArrayList<Integer> ranques = new ArrayList<Integer>();

    ArrayList<String> Question = new ArrayList<String>();
    ArrayList<String> Option1 = new ArrayList<String>();
    ArrayList<String> Option2 = new ArrayList<String>();
    ArrayList<String> Option3 = new ArrayList<String>();
    ArrayList<String> Option4 = new ArrayList<String>();
    ArrayList<String> Answer = new ArrayList<String>();

    Button btn_validate,btn_next;

    int option = 0;
    int qno = 1;
    int index = 0;
    int score = 0;

    String answer,selectedoption,levelname;

    CountDownTimer timer = null;

    SharedPreferences spref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        getSupportActionBar().setTitle("Quiz");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startTimer();

        spref = getSharedPreferences("scorepref",MODE_PRIVATE);

        levelname = getIntent().getStringExtra("levelname");
        edit = spref.edit();

        tv_question = findViewById(R.id.tv_question);
        tv_option1 = findViewById(R.id.tv_option1);
        tv_option2 = findViewById(R.id.tv_option2);
        tv_option3 = findViewById(R.id.tv_option3);
        tv_option4 = findViewById(R.id.tv_option4);

        tv_timer = findViewById(R.id.tv_timer);
        tv_score = findViewById(R.id.tv_score);

        tv_score.setText(score+" / 10");

        Disableclick();

        btn_validate = findViewById(R.id.btn_validate);

        btn_next = findViewById(R.id.btn_next);

        for(int i = 0 ; i < 10; i++)
        {
            ranques.add(i);
        }
        Collections.shuffle(ranques);

        databaseReference = FirebaseDatabase.getInstance("https://fir-quiz-68d4b-default-rtdb.firebaseio.com/").getReference(levelname);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for ( DataSnapshot ds : snapshot.getChildren())
                {
                    Question.add(ds.child("Question").getValue(String.class));
                    Option1.add(ds.child("Option 1").getValue(String.class));
                    Option2.add(ds.child("Option 2").getValue(String.class));
                    Option3.add(ds.child("Option 3").getValue(String.class));
                    Option4.add(ds.child("Option 4").getValue(String.class));
                    Answer.add(ds.child("Answer").getValue(String.class));
                }
                CreateQuestion();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        tv_option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 1;
                selectedoption = tv_option1.getText().toString();
                Disableclick();
                Highlightoption(option);
            }
        });

        tv_option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 2;
                selectedoption = tv_option2.getText().toString();
                Disableclick();
                Highlightoption(option);
            }
        });

        tv_option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 3;
                selectedoption = tv_option3.getText().toString();
                Disableclick();
                Highlightoption(option);
            }
        });

        tv_option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                option = 4;
                selectedoption = tv_option4.getText().toString();
                Disableclick();
                Highlightoption(option);
            }
        });

        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option != 0)
                {
                    if(selectedoption.equals(answer))
                    {
                        score++;
                        tv_score.setText(score+" / 10");
                        Correctoption(option);
                    }
                    else
                    {
                        Wrongoption(option);
                        if(tv_option1.getText().toString().equals(answer))
                        {
                            tv_option1.setBackgroundResource(android.R.color.holo_green_dark);
                        }
                        else if(tv_option2.getText().toString().equals(answer))
                        {
                            tv_option2.setBackgroundResource(android.R.color.holo_green_dark);
                        }
                        else if(tv_option3.getText().toString().equals(answer))
                        {
                            tv_option3.setBackgroundResource(android.R.color.holo_green_dark);
                        }
                        else if(tv_option4.getText().toString().equals(answer))
                        {
                            tv_option4.setBackgroundResource(android.R.color.holo_green_dark);
                        }
                    }
                }

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(index<9) {
                    index++;
                    qno++;
                    RemoveColor();
                    CreateQuestion();
                }
                else
                {
                    cancelTimer();
                    finishquiz();
                }
            }
        });

    }

    void startTimer()
    {
        timer = new CountDownTimer(600000,1000) {
            @Override
            public void onTick(long l) {
                String minsec = String.format("%02d:%02d",TimeUnit.MILLISECONDS.toMinutes(l),
                        TimeUnit.MILLISECONDS.toSeconds(l)-
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));

                tv_timer.setText(minsec);

            }

            @Override
            public void onFinish() {

                tv_timer.setText("done!");
                finishquiz();
            }
        };
        timer.start();
    }

    void cancelTimer()
    {
        if(timer != null)
        {
            timer.cancel();
        }
    }

    void Disableclick()
    {
        tv_option1.setClickable(false);
        tv_option2.setClickable(false);
        tv_option3.setClickable(false);
        tv_option4.setClickable(false);
    }

    @SuppressLint("SetTextI18n")
    void CreateQuestion()
    {
        tv_option1.setClickable(true);
        tv_option2.setClickable(true);
        tv_option3.setClickable(true);
        tv_option4.setClickable(true);

        tv_question.setText(qno+".)"+Question.get(ranques.get(index)));
        tv_option1.setText(Option1.get(ranques.get(index)));
        tv_option2.setText(Option2.get(ranques.get(index)));
        tv_option3.setText(Option3.get(ranques.get(index)));
        tv_option4.setText(Option4.get(ranques.get(index)));

        answer = Answer.get(ranques.get(index));
    }

    void RemoveColor()
    {
        tv_option1.setBackgroundResource(android.R.color.transparent);
        tv_option2.setBackgroundResource(android.R.color.transparent);
        tv_option3.setBackgroundResource(android.R.color.transparent);
        tv_option4.setBackgroundResource(android.R.color.transparent);
    }

    void Highlightoption(int option)
    {
        switch(option)
        {
            case 1 : tv_option1.setBackgroundResource(android.R.color.holo_blue_dark);
                     break;

            case 2 : tv_option2.setBackgroundResource(android.R.color.holo_blue_dark);
                break;

            case 3 : tv_option3.setBackgroundResource(android.R.color.holo_blue_dark);
                break;

            case 4 : tv_option4.setBackgroundResource(android.R.color.holo_blue_dark);
                break;

        }
    }

    void Correctoption(int option)
    {
        switch(option)
        {
            case 1 : tv_option1.setBackgroundResource(android.R.color.holo_green_dark);
                break;

            case 2 : tv_option2.setBackgroundResource(android.R.color.holo_green_dark);
                break;

            case 3 : tv_option3.setBackgroundResource(android.R.color.holo_green_dark);
                break;

            case 4 : tv_option4.setBackgroundResource(android.R.color.holo_green_dark);
                break;

        }
    }

    void Wrongoption(int option)
    {
        switch(option)
        {
            case 1 : tv_option1.setBackgroundResource(android.R.color.holo_red_dark);
                break;

            case 2 : tv_option2.setBackgroundResource(android.R.color.holo_red_dark);
                break;

            case 3 : tv_option3.setBackgroundResource(android.R.color.holo_red_dark);
                break;

            case 4 : tv_option4.setBackgroundResource(android.R.color.holo_red_dark);
                break;

        }
    }

    void finishquiz()
    {
        if(score > spref.getInt(levelname,0)) {
            edit.putInt(levelname, score);
            edit.apply();
        }
        Intent intent = new Intent(QuizActivity.this,ScoreActivity.class);
        intent.putExtra("score",score);
        intent.putExtra("levelname",levelname);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home:
                cancelTimer();
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        cancelTimer();
        finish();
    }
}