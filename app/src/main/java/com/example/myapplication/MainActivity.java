package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tellMeMore(View v)
    {
        Intent intent = new Intent(this, DetailsPage.class);
        startActivity(intent);
    }

    public void expenses(View v)
    {
        Intent intent = new Intent(this, ExpensesPage.class);
        startActivity(intent);
    }
}