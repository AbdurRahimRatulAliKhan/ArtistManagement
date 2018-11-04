package com.shajib.artistmanagement;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistViewHolder>{

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

    class ArtistViewHolder extends RecyclerView.ViewHolder{

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

    private void alert(int position){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you want to ? ");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }



}
