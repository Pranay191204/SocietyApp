package com.database.society;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class Complaint extends AppCompatActivity {
    private CardView raise,view;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");

        raise=(CardView)findViewById(R.id.raisecomplain);
        view=(CardView)findViewById(R.id.viewcomplain);

        raise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Complaint.this,RaiseComplain.class);
                intent.putExtra("username",username);
                startActivity(intent);
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Complaint.this,ViewComplain.class);
                startActivity(i);
            }
        });
    }
}
