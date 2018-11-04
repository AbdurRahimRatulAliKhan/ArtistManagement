package com.shajib.artistmanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private DatabaseReference reference;
    private List<Artist> artists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        initView();
        loadData();
    }

    private void initView() {

        recyclerView = findViewById(R.id.rvid);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);
        reference = FirebaseDatabase.getInstance().getReference("Artist");
    }


    private void loadData(){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                artists.clear();
                Toast.makeText(ViewActivity.this, "Updated", Toast.LENGTH_SHORT).show();

                Iterable<DataSnapshot> dataSnapshots = dataSnapshot.getChildren();

                for ( DataSnapshot snapshot : dataSnapshots ){

                    Artist artist = snapshot.getValue(Artist.class);
                    artists.add(artist);
                }

                ArtistAdapter adapter = new ArtistAdapter(ViewActivity.this, artists);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
