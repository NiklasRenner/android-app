package dk.catnip.android.android_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {

    private TextView endScoreText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        endScoreText = (TextView) findViewById(R.id.text_score);
        endScoreText.setText(String.format("SCORE: %s", getIntent().getIntExtra("score", 0)));
    }

    public void onRestart(View v) {
        finish();
    }

}
