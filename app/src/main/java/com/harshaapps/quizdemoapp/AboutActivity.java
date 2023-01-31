package com.harshaapps.quizdemoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    TextView tv_udemy,tv_skillshare1,tv_skillshare2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_udemy = findViewById(R.id.tv_udemy);
        tv_skillshare1 = findViewById(R.id.tv_skillshare1);
        tv_skillshare2 = findViewById(R.id.tv_skillshare2);

        databaseReference = FirebaseDatabase.getInstance("https://fir-quiz-68d4b-default-rtdb.firebaseio.com/").getReference("Course");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tv_udemy.setText(snapshot.child("Udemy").getValue().toString());
                tv_skillshare1.setText(snapshot.child("Skillshare1").getValue().toString());
                tv_skillshare2.setText(snapshot.child("Skillshare2").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home : finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}