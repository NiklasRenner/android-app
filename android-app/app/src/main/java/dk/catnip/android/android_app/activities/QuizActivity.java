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
import dk.catnip.android.android_app.control.JsonMapper;
import dk.catnip.android.android_app.model.ButtonId;
import dk.catnip.android.android_app.model.Question;
import dk.catnip.android.android_app.utils.Constants;

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
        questions = JsonMapper.loadQuestions(this, R.raw.default_questions);

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
                score += Constants.CORRECT_ANSWER_POINTS;
                setButtonColor(buttons[id.getId()], GREEN);
            } else {
                setButtonColor(buttons[id.getId()], RED);
                setButtonColor(buttons[correctAnswer.getId()], GREEN);
                score += Constants.WRONG_ANSWER_POINTS;
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
