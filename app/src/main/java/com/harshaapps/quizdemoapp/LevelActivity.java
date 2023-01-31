package com.harshaapps.quizdemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LevelActivity extends AppCompatActivity {

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    Button btn_level1,btn_level2,btn_level3;
    TextView tv_level1score,tv_level2score,tv_level3score;

    int scorel1,scorel2,scorel3;

    SharedPreferences spref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        getSupportActionBar().setTitle("Select Level");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spref = getSharedPreferences("scorepref",MODE_PRIVATE);

        scorel1 = spref.getInt("level1",0);
        scorel2 = spref.getInt("level2",0);
        scorel3 = spref.getInt("level3",0);

        btn_level1 = findViewById(R.id.btn_level1);
        btn_level2 = findViewById(R.id.btn_level2);
        btn_level3 = findViewById(R.id.btn_level3);

        tv_level1score = findViewById(R.id.tv_level1score);
        tv_level2score = findViewById(R.id.tv_level2score);
        tv_level3score = findViewById(R.id.tv_level3score);

        tv_level1score.setText("High Score : "+scorel1);
        tv_level2score.setText("High Score : "+scorel2);
        tv_level3score.setText("High Score : "+scorel3);

        btn_level1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelActivity.this,QuizActivity.class);
                intent.putExtra("levelname","level1");
                startActivity(intent);
            }
        });

        btn_level2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelActivity.this,QuizActivity.class);
                intent.putExtra("levelname","level2");
                startActivity(intent);
            }
        });

        btn_level3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LevelActivity.this,QuizActivity.class);
                intent.putExtra("levelname","level3");
                startActivity(intent);
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