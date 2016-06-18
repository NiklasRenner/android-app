package dk.catnip.android.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import dk.catnip.android.android_app.R;
import dk.catnip.android.android_app.control.DataAccessor;
import dk.catnip.android.android_app.utils.Constants;

public class MainActivity extends AppCompatActivity {

    private DataAccessor dao;

    private TextView nameText;
    private Button highscoreButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup logo
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        //get refs
        dao = new DataAccessor(getApplicationContext());
        nameText = (TextView) findViewById(R.id.text_player);
        highscoreButton = (Button) findViewById(R.id.button_main_menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constants.QUIZ_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    showHighscores(highscoreButton);
                }
                break;
            case Constants.END_CODE:
                break;
        }
    }

    public void showHighscores(View v) {
        Intent i = new Intent(getApplicationContext(), EndActivity.class);
        startActivityForResult(i, Constants.END_CODE);
    }

    public void showQuiz(View v) {
        Intent i = new Intent(getApplicationContext(), QuizActivity.class);
        saveName();
        startActivityForResult(i, Constants.QUIZ_CODE);
    }

    private void saveName() {
        String input = nameText.getText().toString().trim();
        String name = input.isEmpty() ? Constants.DEFAULT_NAME : input;
        dao.saveName(name);
    }
}

