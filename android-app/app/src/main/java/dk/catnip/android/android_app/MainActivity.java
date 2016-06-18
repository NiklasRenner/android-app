package dk.catnip.android.android_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private TextView questionText;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonA = (Button) findViewById(R.id.button_a);
        buttonB = (Button) findViewById(R.id.button_b);
        buttonC = (Button) findViewById(R.id.button_c);
        buttonD = (Button) findViewById(R.id.button_d);

        questionText = (TextView) findViewById(R.id.text_question);

        questionText.setText("7*2");

        buttonA.setText("10");
        buttonB.setText("22");
        buttonC.setText("19");
        buttonD.setText("14");
    }

}
