package com.study.contest.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.study.contest.R;
import com.study.contest.db.UserRecordsHelper;
import com.study.contest.model.enums.ColumEnum;

import java.time.LocalDate;

public class EditNote extends AppCompatActivity {


    public static final String DELETE = "Удалить ";
    public static final String SUBMIT_YES = "Да";
    public static final String SUBMIT_NOT = "Нет";
    private EditText titleEditText;
    private EditText contentEditText;
    private String content;
    private String id;
    private String title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        FloatingActionButton fltEdit = findViewById(R.id.fltEditedUpdate);
        FloatingActionButton fltDelete = findViewById(R.id.fltDelete);

        titleEditText = findViewById(R.id.titleEditNote);
        contentEditText = findViewById(R.id.editNotContent);


        insertData();

        fltEdit.setOnClickListener(l -> new UpdateRecord().execute());

        fltDelete.setOnClickListener(l -> confirmDialog());

    }


    private void insertData() {

        id = getIntent().getStringExtra(ColumEnum.ID.getName());
        title = getIntent().getStringExtra(ColumEnum.TITLE.getName());
        content = getIntent().getStringExtra(ColumEnum.CONTENT.getName());

        titleEditText.setText(title);
        contentEditText.setText(content);

    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(DELETE + title + " ?");
        builder.setMessage(getString(R.string.submit_delete) + " " + title + " ?");
        builder.setPositiveButton(SUBMIT_YES, (dialogInterface, i) -> new DeleteNote().execute());

        builder.setNegativeButton(SUBMIT_NOT, (dialogInterface, i) -> {
        });
        builder.create().show();
    }

    private class UpdateRecord extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try (UserRecordsHelper userRecordsHelper = new UserRecordsHelper(EditNote.this)) {
                title = titleEditText.getText().toString().trim();
                content = contentEditText.getText().toString().trim();
                userRecordsHelper.updateData(id, title, content, LocalDate.now());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            Toast.makeText(EditNote.this, getString(R.string.update_successful), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private class DeleteNote extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try (UserRecordsHelper myDB = new UserRecordsHelper(EditNote.this)) {
                myDB.deleteOneRow(id);
                finish();
            }

            return null;
        }
    }
}