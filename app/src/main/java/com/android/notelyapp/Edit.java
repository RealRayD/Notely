package com.android.notelyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class Edit extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteContent;
    Calendar cal;
    String currentDate;
    String currentTime;
    long noteID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        Intent i = getIntent();
        noteID = i.getLongExtra("ID",0);
        NotelyDB db = new NotelyDB(this);
        Note note = db.getNote(noteID);
        final String title = note.getTitle();
        String content = note.getContent();
        noteTitle = findViewById(R.id.noteTitle);
        noteContent = findViewById(R.id.noteDetails);
        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                getSupportActionBar().setTitle(title);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        noteTitle.setText(title);
        noteContent.setText(content);
        cal = Calendar.getInstance();
        currentDate = cal.get(Calendar.DAY_OF_MONTH)+ "/" +(cal.get(Calendar.MONTH)+1)+ "/" +cal.get(Calendar.YEAR);
        currentTime = pad(cal.get(Calendar.HOUR))+ ":" +pad(cal.get(Calendar.MINUTE));
    }

    private String pad(int time) {
        if(time < 10)
            return "0" +time;
        return String.valueOf(time);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            Note note = new Note(noteID, noteTitle.getText().toString(), noteContent.getText().toString(), currentDate, currentTime);
            NotelyDB sDB = new NotelyDB(getApplicationContext());
            long id = sDB.editNote(note);
            goToMain();
            Toast.makeText(this, "Note was edited!", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Edit cancelled!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


}
