package com.shajib.artistmanagement;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder> {

    private Context context;
    private List<Artist> artists;

    public ArtistAdapter(Context context, List<Artist> artists) {
        this.context = context;
        this.artists = artists;
    }

    @NonNull
    @Override
    public ArtistViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.design, null);
        return new ArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistViewHolder artistViewHolder, int i) {

        artistViewHolder.name.setText(artists.get(i).name);
        artistViewHolder.country.setText(artists.get(i).country);
        artistViewHolder.gender.setText(artists.get(i).gender);

    }

    @Override
    public int getItemCount() {
        return artists.size();
    }


    //*****************************************************

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        TextView name, country, gender;


        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name_id);
            country = itemView.findViewById(R.id.country_id);
            gender = itemView.findViewById(R.id.gender_id);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });
        }
    }

    private void alert(final int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you want to ? ");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Artist");
                reference.child(artists.get(position).id).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                artists.remove(position);
                notifyDataSetChanged();
            }
        });

        builder.setNegativeButton("No", null);
        builder.setNeutralButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog dialogUpdate = new Dialog(context);
                dialogUpdate.setContentView(R.layout.update_layout);

                final EditText  name, country, gender;

                name = dialogUpdate.findViewById(R.id.updateName);
                country = dialogUpdate.findViewById(R.id.updateCountry);
                gender = dialogUpdate.findViewById(R.id.updateGender);

                name.setText(artists.get(position).name);
                country.setText(artists.get(position).country);
                gender.setText(artists.get(position).gender);

                Button updateButton = dialogUpdate.findViewById(R.id.updateButton);

                updateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newName = name.getText().toString();
                        String newCountry = country.getText().toString();
                        String newGender = gender.getText().toString();

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Artist");

                        Artist artist = new Artist(artists.get(position).id, newName, newCountry, newGender);
                        reference.child(artists.get(position).id).setValue(artist);
                    }
                });

                dialogUpdate.show();
            }
        });

        builder.setCancelable(false);
        builder.show();

    }


}
