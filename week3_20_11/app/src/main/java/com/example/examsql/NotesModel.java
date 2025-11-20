package com.example.examsql;

import java.io.Serializable;

public class NotesModel implements Serializable {

    private int IdNote;
    private String NameNote;

    public NotesModel(int idNote, String nameNote) {
        IdNote = idNote;
        NameNote = nameNote;
    }

    public int getIdNote() {
        return IdNote;
    }

    public String getNameNote() {
        return NameNote;
    }

    public void setNameNote(String nameNote) {
        NameNote = nameNote;
    }
}
