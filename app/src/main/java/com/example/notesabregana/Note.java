package com.example.notesabregana;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Note {

    public static final String KEY_ID = "_id";
    public static final String KEY_NOTE_COLUMN = "NOTE_COLUMN";
    public static final String KEY_NOTE_CREATED_COLUMN = "NOTE_CREATED_COLUMN";
    public static final String KEY_NOTE_IMPORTANT_COLUMN = "NOTE_IMPORTANT_COLUMNS";

    int id;
    String note;
    Date created;
    boolean important;

    public Note(String note) {
        this.note = note;
        created = new Date(System.currentTimeMillis());
    }

    public String getNote() { return note;}
    public void setNote(String note) { this.note = note;}
    public Date getCreated() { return created;}
    public void setCreated(Date created) { this.created = created;}

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(created);
        return "(" + timeString + ") " + note;
    }
}
