package dk.catnip.android.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import dk.catnip.android.android_app.R;
import dk.catnip.android.android_app.model.Entry;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "QUIZ_APP";
    private static final String DATA = "DATA_SHIT";

    private static final int QUIZ_CODE = 100;
    private static final int END_CODE = 105;

    private TextView nameText;
    private String name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameText = (TextView) findViewById(R.id.text_player);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case QUIZ_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    showScore(data);
                }
                break;
            case END_CODE:
                break;
        }
    }

    private void showScore(Intent data) {
        Intent i = new Intent(getApplicationContext(), EndActivity.class);
        int score = data.getIntExtra("score", 0);
        i.putExtra("score", saveScore(name, score));
        startActivityForResult(i, END_CODE);
    }

    public void onHighscore(View v) {
        Intent i = new Intent(getApplicationContext(), EndActivity.class);
        i.putExtra("score", Entry.toCacheFormat(loadScore()));
        startActivityForResult(i, END_CODE);
    }

    public void onStart(View v) {
        Intent i = new Intent(getApplicationContext(), QuizActivity.class);
        name = nameText.getText().toString();
        if (name.isEmpty()) {
            nameText.setText("Player");
            name = "Player";
        }
        startActivityForResult(i, QUIZ_CODE);
    }

    public String saveScore(String name, int score) {
        List<Entry> entries = loadScore();

        Entry entry = new Entry(name, score);
        entries.add(entry);
        Collections.sort(entries);

        String data = Entry.toCacheFormat(entries);
        SharedPreferences save = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = save.edit();
        editor.putString(DATA, data);
        editor.commit();

        return data;
    }

    public List<Entry> loadScore() {
        SharedPreferences save = getSharedPreferences(PREFS, MODE_PRIVATE);
        String data = save.getString(DATA, null);
        List<Entry> entries = Entry.fromCacheFormat(data);

        if (entries == null) {
            entries = Entry.createDefault();
        }

        return entries;
    }

}

