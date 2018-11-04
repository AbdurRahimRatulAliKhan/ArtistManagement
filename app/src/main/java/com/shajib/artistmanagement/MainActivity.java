package com.shajib.artistmanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText etname, etcountry, etgender;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initVariable();
    }

    private void initVariable() {

        reference = FirebaseDatabase.getInstance().getReference("Artist");
    }

    private void initView() {

        etname = findViewById(R.id.etnameid);
        etcountry = findViewById(R.id.etcountryid);
        etgender = findViewById(R.id.etgenderid);

    }

    public void save(View view) {

        String name, country, gender;

        name = etname.getText().toString().trim();
        country = etcountry.getText().toString().trim();
        gender = etgender.getText().toString().trim();

        if(name.isEmpty()){
            etname.setError("Field can not be empty");
            etname.requestFocus();
            return;
        } if(country.isEmpty()){
            etcountry.setError("Field can not be empty");
            etcountry.requestFocus();
            return;
        } if(gender.isEmpty()){
           etgender.setError("Field can not be empty");
            etgender.requestFocus();
            return;
        }

        String id = reference.push().getKey();

        Artist artist = new Artist(id, name, country, gender);

        reference.child(id).setValue(artist).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();

                    etname.setText("");
                    etcountry.setText("");
                    etgender.setText("");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void view(View view) {

        startActivity(new Intent(this,ViewActivity.class));

    }
}
