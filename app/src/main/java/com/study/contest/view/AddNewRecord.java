package com.study.contest.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.study.contest.R;
import com.study.contest.db.UserRecordsHelper;

import java.time.LocalDate;

public class AddNewRecord extends AppCompatActivity {

    private EditText newRecordText;
    private EditText newRecordTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_record);

        newRecordTitle = findViewById(R.id.notTittleView);
        newRecordText = findViewById(R.id.noteTextView);
        FloatingActionButton btnNewRecord = findViewById(R.id.fltAddNote);

        btnNewRecord.setOnClickListener(view -> addNewRecord());

    }


    private void addNewRecord() {
        try (UserRecordsHelper userRecordsHelper = new UserRecordsHelper(AddNewRecord.this)) {
            AsyncTask.execute(() -> userRecordsHelper.addRecord(newRecordTitle.getText().toString(), newRecordText.getText().toString(), LocalDate.now()));
            finish();
        } catch (Exception e) {
            Log.getStackTraceString(e.fillInStackTrace());
        }
    }


}