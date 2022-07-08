package com.example.notesabregana;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Note> notes;
    NotesAdapter notes_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListAdapterMethod();
        btnAddListenerMethod();
        etNoteEnterListenerMethod();
    }

    private void etNoteEnterListenerMethod() {
        EditText etNote = findViewById(R.id.etNote);
        etNote.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER
                        || keyEvent.getAction() == KeyEvent.KEYCODE_NUMPAD_ENTER) {

                    //Same code as onClick method in button listener
                    String note = etNote.getText().toString();
                    notes.add(new Note(note));
                    notes_adapter.notifyDataSetChanged();
                    etNote.setText("");
                    return true;
                } return false;
            }
        });
    }

    private void btnAddListenerMethod() {
        Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNote = findViewById(R.id.etNote);

                //Get the notes and make it into a string
                String note = etNote.getText().toString();

                //Add the notes into the notes array
                notes.add(new Note(note));
                notes_adapter.notifyDataSetChanged();
                etNote.setText("");

                //Log.d("Dude", "on click works");
                //Toast.makeText(getBaseContext(), "Click Add", Toast.LENGTH_SHORT);
            }
        });
    }

    private void setListAdapterMethod() {
        ListView lvList = findViewById(R.id.lvList);
        notes = new ArrayList<>();

        //Change string parameters in notes.add into new Notes(String)
        notes.add(new Note("First Note"));
        notes.add(new Note("Second Note"));

        notes_adapter = new NotesAdapter(getBaseContext(), R.layout.note_layout, notes);
        lvList.setAdapter(notes_adapter);

        notes.add(new Note("Laurence Abregana"));
    }

}