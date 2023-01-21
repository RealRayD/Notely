package com.android.notelyapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<Note> notes;

    Adapter(Context context, List<Note> notes){
        this.inflater = LayoutInflater.from(context);
        this.notes = notes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_list_view, viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String title = notes.get(i).getTitle();
        String date = notes.get(i).getDate();
        String time = notes.get(i).getTime();
        long id = notes.get(i).getId();
        viewHolder.noteTitle.setText(title);
        viewHolder.noteDate.setText(date);
        viewHolder.noteTime.setText(time);
        viewHolder.noteID.setText(String.valueOf(notes.get(i).getId()));

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle, noteDate, noteTime, noteID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            noteDate = itemView.findViewById(R.id.noteDate);
            noteTime = itemView.findViewById(R.id.noteTime);
            noteID = itemView.findViewById(R.id.listId);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(), Detail.class);
                    i.putExtra("ID", notes.get(getAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }
            });
        }
    }
}
