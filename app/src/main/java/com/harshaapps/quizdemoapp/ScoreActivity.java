package com.harshaapps.quizdemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ScoreActivity extends AppCompatActivity {

    TextView tv_finalscore;
    Button btn_retake,btn_finish;

    int score;
    String levelname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        getSupportActionBar().setTitle("Final Score");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        score = getIntent().getIntExtra("score",0);
        levelname = getIntent().getStringExtra("levelname");

        tv_finalscore = findViewById(R.id.tv_finalscore);

        btn_retake = findViewById(R.id.btn_retake);
        btn_finish = findViewById(R.id.btn_finish);

        tv_finalscore.setText(String.valueOf(score));

        btn_retake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ScoreActivity.this,QuizActivity.class);
                intent.putExtra("levelname",levelname);
                startActivity(intent);
                finish();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId())
        {
            case android.R.id.home: finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }
}