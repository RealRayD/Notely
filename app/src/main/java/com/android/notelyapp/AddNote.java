package com.android.notelyapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Calendar;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle, noteDetails;
    Calendar cal;
    String currentDate;
    String currentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New note");
        noteDetails = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);
        noteTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

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

        cal = Calendar.getInstance();
        currentDate = cal.get(Calendar.DAY_OF_MONTH)+ "/"+ (cal.get(Calendar.MONTH)+1)+ "/" +cal.get(Calendar.YEAR);
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
            if(noteTitle.getText().length() != 0){
                Note note = new Note(noteTitle.getText().toString(), noteDetails.getText().toString(), currentDate, currentTime);
                NotelyDB sDB = new NotelyDB(this);
                long id = sDB.addNote(note);
                Note check = sDB.getNote(id);
                onBackPressed();

                Toast.makeText(this, "Note was saved!", Toast.LENGTH_SHORT).show();
            } else {
                noteTitle.setError("You must enter a title");
            }

        } else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Canceled!", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
