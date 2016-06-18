package dk.catnip.android.android_app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dk.catnip.android.android_app.R;

public class MainActivity extends AppCompatActivity {

    private static final int QUIZ_CODE = 100;
    private static final int END_CODE = 105;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case QUIZ_CODE:
                Intent i = new Intent(getApplicationContext(), EndActivity.class);
                int score = data.getIntExtra("score", 0);
                i.putExtra("score", score);
                startActivityForResult(i, END_CODE);
                break;
            case END_CODE:
                setContentView(R.layout.activity_main);
                break;
        }
    }

    public void onStart(View v) {
        Intent i = new Intent(getApplicationContext(), QuizActivity.class);
        startActivityForResult(i, QUIZ_CODE);
    }

}
