package com.example.examsql;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NoteClickListener {

    DatabaseHandler databaseHandler;
    ListView listView;
    EditText edtNote;
    ArrayList<NotesModel> arrayList;
    NotesAdapter adapter;

    NotesModel selectedNote = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        edtNote = findViewById(R.id.edtNote);
        listView = findViewById(R.id.listView1);

        databaseSQLite();
        arrayList = new ArrayList<>();
        adapter = new NotesAdapter(this, R.layout.row_note, arrayList, this);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedNote = arrayList.get(position);
            edtNote.setText(selectedNote.getNameNote());
            Toast.makeText(this, "Đã chọn: " + selectedNote.getNameNote(), Toast.LENGTH_SHORT).show();
        });

        loadDataFromSQLite();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, ins) -> {
            Insets sys = ins.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom);
            return ins;
        });
    }

    // ========================= SQLite ==============================
    private void databaseSQLite() {
        databaseHandler = new DatabaseHandler(this, "notes.sqlite", null, 1);
        databaseHandler.QueryData(
                "CREATE TABLE IF NOT EXISTS Notes(Id INTEGER PRIMARY KEY AUTOINCREMENT, NameNotes VARCHAR(200))"
        );
    }

    private void loadDataFromSQLite() {
        Cursor cursor = databaseHandler.GetData("SELECT * FROM Notes");
        arrayList.clear();
        while (cursor.moveToNext()) {
            arrayList.add(new NotesModel(cursor.getInt(0), cursor.getString(1)));
        }
        cursor.close();
        adapter.notifyDataSetChanged();
    }

    // ========================= MENU ==============================
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.menuAdd) {
            openAddDialog();
        } else if (item.getItemId() == R.id.menuEdit) {
            openEditDialog();
        } else if (item.getItemId() == R.id.menuDelete) {
            openDeleteDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    // ========================= DIALOG ADD ==============================
    private void openAddDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_note);

        EditText name = dialog.findViewById(R.id.editTextName);
        Button add = dialog.findViewById(R.id.buttonAdd);
        Button cancel = dialog.findViewById(R.id.buttonHuy);

        add.setOnClickListener(v -> {
            String text = name.getText().toString().trim();
            if (text.isEmpty()) return;

            databaseHandler.QueryData(
                    "INSERT INTO Notes VALUES (null, '" + text.replace("'", "''") + "')"
            );

            loadDataFromSQLite();
            dialog.dismiss();
        });

        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // ========================= DIALOG EDIT ==============================
    private void openEditDialog() {
        if (selectedNote == null) {
            Toast.makeText(this, "Hãy chọn ghi chú để cập nhật!", Toast.LENGTH_SHORT).show();
            return;
        }

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_edit_note);

        EditText edit = dialog.findViewById(R.id.editTextNameEdit);
        Button update = dialog.findViewById(R.id.buttonCapNhat);
        Button cancel = dialog.findViewById(R.id.buttonHuyEdit);

        edit.setText(selectedNote.getNameNote());

        update.setOnClickListener(v -> {
            String newName = edit.getText().toString().trim();
            if (newName.isEmpty()) return;

            databaseHandler.QueryData(
                    "UPDATE Notes SET NameNotes = '" + newName.replace("'", "''") +
                            "' WHERE Id = " + selectedNote.getIdNote()
            );

            loadDataFromSQLite();
            dialog.dismiss();
        });

        cancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    // ========================= DELETE ==============================
    private void openDeleteDialog() {
        if (selectedNote == null) {
            Toast.makeText(this, "Hãy chọn ghi chú để xóa!", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xóa ghi chú")
                .setMessage("Bạn có chắc muốn xóa: " + selectedNote.getNameNote() + " ?")
                .setPositiveButton("Xóa", (d, w) -> {
                    databaseHandler.QueryData(
                            "DELETE FROM Notes WHERE Id = " + selectedNote.getIdNote()
                    );
                    selectedNote = null;
                    edtNote.setText("");
                    loadDataFromSQLite();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    // ========================= CALLBACK TỪ ADAPTER ==============================
    @Override
    public void onEdit(NotesModel note) {
        selectedNote = note;
        openEditDialog();
    }

    @Override
    public void onDelete(NotesModel note) {
        selectedNote = note;
        openDeleteDialog();
    }
}
