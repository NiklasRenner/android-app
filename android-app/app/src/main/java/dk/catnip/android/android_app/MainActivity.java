package dk.catnip.android.android_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private Switch zwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        zwitch = (Switch) findViewById(R.id.switch1);
    }

    public void doStuff(View v) {
        textView.setText("o shit waddup");
    }

    public void doSwitch(View v) {
        boolean isOn = zwitch.isChecked();

        if (isOn) {
            textView.setText("ON");
        } else {
            textView.setText("OFF");
        }

    }
}
