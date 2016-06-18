package dk.catnip.android.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dk.catnip.android.android_app.R;
import dk.catnip.android.android_app.control.DataAccessor;
import dk.catnip.android.android_app.model.ButtonId;
import dk.catnip.android.android_app.model.Question;

public class QuizActivity extends AppCompatActivity {

    private static final int GREY = 0xFFDDDDDD;
    private static final int RED = 0xFFFF0000;
    private static final int GREEN = 0xFF00FF00;

    private TextView questionText;
    private Button[] buttons = new Button[4];
    private TextView scoreText;
    private DataAccessor dao;

    private List<Question> questions = new ArrayList<>();
    private int counter = 0;

    private ButtonId correctAnswer;
    private boolean isAnswered = false;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //get refs to components
        dao = new DataAccessor(getApplicationContext());
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
        questions.add(new Question("Spot the non-president guy", "James Madison", "John Quincy Adams", "Zachary Taylor", "William McKinley", ButtonId.D));
        questions.add(new Question("Ah my name is, who my name is", "Eminem", "Slim shady", "Thikathika", "looool", ButtonId.C));
        questions.add(new Question("How many legs does a spider have", "4", "8", "7", "9", ButtonId.B));
        questions.add(new Question("46 * 77 =? Multiply Baby!", "2942", "3542", "4277", "3842", ButtonId.B));
        questions.add(new Question("What animal is strongest", "a mice", "The bat", "Snakes!", "Le SnailBoy", ButtonId.D));
        questions.add(new Question("Now push the secend button", "first", "secend", "secend", "Last", ButtonId.B));
        questions.add(new Question("What is a meme", "Cartoon", "Books", "Interweb stuff", "Cheeseburger", ButtonId.C));
        questions.add(new Question("Who invented the TV?", "Harry Styles", "Michael Jackson", "Philo Farnsworth", "Ann Wilson", ButtonId.C));
        questions.add(new Question("Who like donoughts most", "Biever", "Homer", "Mickey", "Batman", ButtonId.B));
        questions.add(new Question("What does a wooodchopper", "Fly", "Chop wood", "Wearing big pants", "i dont know", ButtonId.A));
        questions.add(new Question("What came first", "The egg", "The chicken", "Aliens", "Fishes!", ButtonId.B));
        questions.add(new Question("What will kill you fastest?", "Smoking", "Eat McD", "be 50 Cent", "Drink 15L at once", ButtonId.D));
        questions.add(new Question("What is the capitol of Burkino Faso", "mozambique", "Ouagadougou", "sierra leone", "Nigeria", ButtonId.B));
        questions.add(new Question("In Denmark we sounds like..", "Everyone else", "wolves", "Potatoes", "Old men", ButtonId.C));

        //set first question on view
        setupQuestion(questions.get(counter));
        counter++;
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

    public void buttonClick(View v) {
        if (!isAnswered) {
            resetButtons();

            ButtonId id = mapButtonId(v.getId());
            if (id == correctAnswer) {
                score += 500;
                setButtonColor(buttons[id.getId()], GREEN);
            } else {
                setButtonColor(buttons[id.getId()], RED);
                setButtonColor(buttons[correctAnswer.getId()], GREEN);
                score -= 500;
            }

            scoreText.setText(String.format("score: %s", score));
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
                saveScore();
                showMainMenu();
                return;
            }
            setupQuestion(questions.get(counter));
            counter++;
            resetButtons();
        }
    }

    private void saveScore() {
        String name = dao.loadName();
        dao.saveHighScore(name, score);
    }

    private void showMainMenu() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
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
