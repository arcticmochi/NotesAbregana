package com.example.notesabregana;

import static com.example.notesabregana.Note.*;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotesAdapter extends ArrayAdapter<Note> {

    int resource;
    List<Note> notes;
    FragmentManager fm;
    Note current;
    NotesOpenHelper helper;

    public NotesAdapter(@NonNull Context context, int resource, @NonNull List<Note> objects, FragmentManager fm, NotesOpenHelper helper) {
        super(context, resource, objects);
        this.resource = resource;
        this.notes = objects;
        this.fm = fm;
        this.helper = helper;
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
        if (note.important) {
            tvNote.setText(act_note + "★");
        } else {
            tvNote.setText(act_note);
        }
        tvTime.setText(timeString);

        ImageButton btnDelete = noteView.findViewById(R.id.btnDelete);
        //setting btnDelete programmatically cuz it didn't work in the layout ; - ;
        btnDelete.setImageResource(android.R.drawable.ic_delete);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = note.id;
                String selector = KEY_ID + "=" + id;
                String selectorArgs[] = null;
                /*
                Selecting a set of arguments
                String selector = KEY_ID + "=?";
                String selectorArgs[] = {id+""};
                 */

//                SQLiteDatabase db = helper.getWritableDatabase();
//                db.delete(NotesOpenHelper.DATABASE_TABLE, selector, selectorArgs);
                ContentResolver cr = getContext().getContentResolver();
                cr.delete(NotesContentProvider.CONTENT_URI, selector, selectorArgs);

                //remove the note in the particular note position
                notes.remove(note);
                //Method from ArrayAdapter()
                notifyDataSetChanged();
                //Toast.makeText(getContext(), "Delete called", Toast.LENGTH_SHORT).show();
            }
        });

        noteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditNoteDialogFragment dialog = new EditNoteDialogFragment(act_note);
                dialog.show(fm, "Laurence");
                current = note;
            }
        });
        return noteView;
        //return super.getView(position, convertView, parent);
    }

    public void onEditListenerMethod(DialogFragment dialog) {
        EditText etEdit = dialog.getDialog().findViewById(R.id.etEdit);
        String new_note = etEdit.getText().toString();
        current.setNote(new_note);
        notifyDataSetChanged();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTE_COLUMN, new_note);

        String selector = KEY_ID + "=" + current.id;
        String selectorArgs[] = null;

        ContentResolver cr = getContext().getContentResolver();

        Uri rowUri = ContentUris.withAppendedId(NotesContentProvider.CONTENT_URI, current.id);
        cr.update(rowUri, cv, null, null);
//        SQLiteDatabase db = helper.getWritableDatabase();
//        db.update(NotesOpenHelper.DATABASE_TABLE, cv, selector, selectorArgs);
        current = null;
    }

    public void onCancelListenerMethod(DialogFragment dialog) {
        current = null;
    }
}
