package dk.catnip.android.android_app;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.catnip.android.android_app.model.ButtonId;
import dk.catnip.android.android_app.model.Question;

public class MainActivity extends AppCompatActivity {

    private final int GREY = 0xFFDDDDDD;
    private final int RED = 0xFFFF0000;
    private final int GREEN = 0xFF00FF00;

    private TextView questionText;
    private Button[] buttons = new Button[4];
    private TextView scoreText;

    private List<Question> questions = new ArrayList<>();
    private int counter = 0;

    private ButtonId correctAnswer;
    private boolean isAnswered = false;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get refs to components
        buttons[0] = (Button) findViewById(R.id.button_a);
        buttons[1] = (Button) findViewById(R.id.button_b);
        buttons[2] = (Button) findViewById(R.id.button_c);
        buttons[3] = (Button) findViewById(R.id.button_d);
        questionText = (TextView) findViewById(R.id.text_question);
        scoreText = (TextView) findViewById(R.id.text_pts);

        //setup questions
        questions.add(new Question("How old is Daniel", "10", "20", "23", "30", ButtonId.C));
        questions.add(new Question("How old is Niklas", "23", "5", "99", "30", ButtonId.A));
        questions.add(new Question("Which one of theese activites is illegal", "Drinking", "Swimming", "Walk on the grass", "Smoke weed", ButtonId.B));
        questions.add(new Question("Which letter i missing?", "t", "n", "s", "q", ButtonId.C));
        questions.add(new Question("How many irishmen in a bar?", "1", "2", "3", "INFINITY!", ButtonId.D));
        questions.add(new Question("Where is Christiania located?", "Sweden", "Norway", "Denmark", "Germany", ButtonId.C));
        questions.add(new Question("What is a strawberry?", "A fruit", "a nut art", "None of the aboove", "mE liKe Quiz!", ButtonId.B));
        questions.add(new Question("Do you sleep with teddys?", "Hell no!", "Maybe tihi", "YES", "Nah", ButtonId.D));
        questions.add(new Question("Spot the non-president guy", "James Madison", "John Quincy Adams", "Zachary Taylor", "I dont like presidents, and ehm a guy from the past, yea", ButtonId.D));
        questions.add(new Question("Ah my name is, who my name is", "Eminem", "Slim shady", "Thikathika", "looool", ButtonId.C));
        questions.add(new Question("How many legs does a spider have", "4", "8", "7", "9", ButtonId.B));
        questions.add(new Question("Once upon a time!", "a saw a squirrel", "i'll poop", "i ran into a tree", "I dont got time for stories,fool", ButtonId.D));
        


        //set first question on view
        setupQuestion(questions.get(counter));
        counter++;
    }

    public void buttonClick(View v) {
        if (!isAnswered) {
            resetButtons();

            ButtonId id = mapButtonId(v.getId());
            if (id == correctAnswer) {
                score += 50;
                setButtonColor(buttons[id.getId()], GREEN);
            } else {
                setButtonColor(buttons[id.getId()], RED);
                score -= 50;
            }

            scoreText.setText(String.format("%s", score));
            isAnswered = true;
        }
    }

    private ButtonId mapButtonId(int id) {
        switch (id) {
            case R.id.button_a:
                return ButtonId.A;
            case R.id.button_b:
                return ButtonId.B;
            case R.id.button_c:
                return ButtonId.C;
            case R.id.button_d:
                return ButtonId.D;
            default:
                return ButtonId.UNDEFINED;
        }
    }

    public void nextQuestion(View v) {
        if (isAnswered) {
            if (counter >= questions.size()) {
                counter = 0;
            }
            setupQuestion(questions.get(counter));
            counter++;
            resetButtons();
        }
    }

    private void setupQuestion(Question question) {
        questionText.setText(question.getQuestion());
        buttons[0].setText(question.getAnswerA());
        buttons[1].setText(question.getAnswerB());
        buttons[2].setText(question.getAnswerC());
        buttons[3].setText(question.getAnswerD());
        correctAnswer = question.getCorrectAnswer();
        isAnswered = false;
    }

    private void resetButtons() {
        for (Button button : buttons) {
            setButtonColor(button, GREY);
        }
    }

    private void setButtonColor(Button button, int color) {
        button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

}
