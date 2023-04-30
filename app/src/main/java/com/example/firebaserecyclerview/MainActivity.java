package com.example.firebaserecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnInsert, btnView;
    EditText username;
    EditText sessionnr;

    DatabaseReference databaseUsers;
    private String meetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInsert = findViewById(R.id.btn_insert);
        btnView = findViewById(R.id.btn_view);
        username = findViewById(R.id.editname);
        sessionnr = findViewById(R.id.editnumber);
        databaseUsers = FirebaseDatabase.getInstance().getReference("/Sessions/7/Participants");

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertData();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserlistActivity.class));
                finish();
            }
        });
    }

    private void InsertData() {
        String name = username.getText().toString();
        String id = databaseUsers.push().getKey();

        Model user = new Model(name);
        databaseUsers.child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "User details inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}