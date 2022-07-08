package com.example.notesabregana;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {

    int resource;

    public NotesAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //This will encapsulate the initial linearlayout
        LinearLayout noteView;
        Note note = getItem(position);
        String act_note = note.getNote();
        Date act_created = note.getCreated();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(act_created);

        //Convert view is the existing view of the item
        if (convertView == null) {
            //In order to create own convert view, you need to inflate it
            noteView = new LinearLayout(getContext());
            //Inflater shows the unopened or the not yet shown layout
            String inflater = Context.LAYOUT_INFLATER_SERVICE;

            LayoutInflater li = (LayoutInflater) getContext().getSystemService(inflater);
            li.inflate(resource, noteView, true);
        } else {
            //Otherwise, update the existing View
            noteView = (LinearLayout) convertView;
        }

        TextView tvNote = noteView.findViewById(R.id.tvNote);
        TextView tvTime = noteView.findViewById(R.id.tvTime);
        tvNote.setText(act_note);   tvTime.setText(timeString);
        return noteView;

        //return super.getView(position, convertView, parent);
    }
}
