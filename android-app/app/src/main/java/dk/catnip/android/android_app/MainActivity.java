package dk.catnip.android.android_app;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.catnip.android.android_app.model.Question;

public class MainActivity extends AppCompatActivity {

    private final int GREY = 0xFFDDDDDD;
    private final int RED = 0xFFFF0000;
    private final int GREEN = 0xFF00FF00;

    private TextView questionText;
    private Button buttonA;
    private Button buttonB;
    private Button buttonC;
    private Button buttonD;

    private List<Question> questions = new ArrayList<>();
    private int counter = 0;

    private int correctAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get refs to components
        buttonA = (Button) findViewById(R.id.button_a);
        buttonB = (Button) findViewById(R.id.button_b);
        buttonC = (Button) findViewById(R.id.button_c);
        buttonD = (Button) findViewById(R.id.button_d);
        questionText = (TextView) findViewById(R.id.text_question);

        //setup questions
        questions.add(new Question("How old is Daniel", "10", "20", "23", "30", 3));
        questions.add(new Question("How old is Niklas", "23", "5", "99", "30", 1));
        questions.add(new Question("Which one of theese activites is illegal", "Drinking", "Swimming", "Walk on the grass", "Smoke weed", 2));
        questions.add(new Question("Which letter i missing?", "t", "n", "s", "q", 3));
        questions.add(new Question("How many irishmen in a bar?", "1", "2", "3", "INFINITY!", 4));

        //set first question on view
        setupQuestion(questions.get(counter));
        counter++;
    }

    public void buttonClick(View v) {
        resetButtons();

        switch (v.getId()) {
            case R.id.button_a:
                if (correctAnswer == 1) {
                    setButtonColor(buttonA, GREEN);
                } else {
                    setButtonColor(buttonA, RED);
                }
                break;
            case R.id.button_b:
                if (correctAnswer == 2) {
                    setButtonColor(buttonB, GREEN);
                } else {
                    setButtonColor(buttonB, RED);
                }
                break;
            case R.id.button_c:
                if (correctAnswer == 3) {
                    setButtonColor(buttonC, GREEN);
                } else {
                    setButtonColor(buttonC, RED);
                }
                break;
            case R.id.button_d:
                if (correctAnswer == 4) {
                    setButtonColor(buttonD, GREEN);
                } else {
                    setButtonColor(buttonD, RED);
                }
                break;
        }
    }

    public void nextQuestion(View v) {
        if (counter >= questions.size()) {
            counter = 0;
        }
        setupQuestion(questions.get(counter));
        counter++;
        resetButtons();
    }

    private void setupQuestion(Question question) {
        questionText.setText(question.getQuestion());
        buttonA.setText(question.getAnswerA());
        buttonB.setText(question.getAnswerB());
        buttonC.setText(question.getAnswerC());
        buttonD.setText(question.getAnswerD());
        correctAnswer = question.getCorrectAnswer();
    }

    private void resetButtons() {
        setButtonColor(buttonA, GREY);
        setButtonColor(buttonB, GREY);
        setButtonColor(buttonC, GREY);
        setButtonColor(buttonD, GREY);
    }

    private void setButtonColor(Button button, int color) {
        button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

}
