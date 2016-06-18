package dk.catnip.android.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import dk.catnip.android.android_app.R;
import dk.catnip.android.android_app.control.DataAccessor;
import dk.catnip.android.android_app.model.Entry;

public class EndActivity extends AppCompatActivity {

    private DataAccessor dao;

    private TextView[] entryTexts = new TextView[10];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        dao = new DataAccessor(getApplicationContext());
        entryTexts[0] = (TextView) findViewById(R.id.highscore_1_text);
        entryTexts[1] = (TextView) findViewById(R.id.highscore_2_text);
        entryTexts[2] = (TextView) findViewById(R.id.highscore_3_text);
        entryTexts[3] = (TextView) findViewById(R.id.highscore_4_text);
        entryTexts[4] = (TextView) findViewById(R.id.highscore_5_text);
        entryTexts[5] = (TextView) findViewById(R.id.highscore_6_text);
        entryTexts[6] = (TextView) findViewById(R.id.highscore_7_text);
        entryTexts[7] = (TextView) findViewById(R.id.highscore_8_text);
        entryTexts[8] = (TextView) findViewById(R.id.highscore_9_text);
        entryTexts[9] = (TextView) findViewById(R.id.highscore_10_text);

        List<Entry> entries = dao.loadHighScore();

        for (int i = 0; i < entryTexts.length; i++) {
            Entry entry = entries.get(i);
            entryTexts[i].setText(String.format("%s: %s", entry.getName(), entry.getScore()));
        }
    }

    public void showMainMenu(View v) {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

}
