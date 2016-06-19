package dk.catnip.android.android_app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

import dk.catnip.android.android_app.R;
import dk.catnip.android.android_app.control.DataAccessor;
import dk.catnip.android.android_app.model.ButtonId;
import dk.catnip.android.android_app.model.Player;
import dk.catnip.android.android_app.model.Question;
import dk.catnip.android.android_app.utils.Constants;

public class QuizActivity extends AppCompatActivity {

    private TextView questionText;
    private TextView livesText;
    private TextView scoreText;
    private Button nextButton;
    private Button[] buttons;

    private DataAccessor dao;

    private Player player;

    private Iterator<Question> questions;
    private Question question;
    private boolean isAnswered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //init view
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //get refs to components
        questionText = (TextView) findViewById(R.id.text_question);
        livesText = (TextView) findViewById(R.id.text_lives);
        scoreText = (TextView) findViewById(R.id.text_pts);
        nextButton = (Button) findViewById(R.id.button_next);

        buttons = new Button[4];
        buttons[0] = (Button) findViewById(R.id.button_a);
        buttons[1] = (Button) findViewById(R.id.button_b);
        buttons[2] = (Button) findViewById(R.id.button_c);
        buttons[3] = (Button) findViewById(R.id.button_d);

        //create player
        dao = new DataAccessor(getApplicationContext());
        player = new Player(dao.loadName());
        updateLives();

        //setup questions
        questions = dao.loadQuestions(this, R.raw.default_questions);
        goToNextQuestion();
    }

    public void answerClick(View v) {
        if (!isAnswered) {
            isAnswered = true;
            resetButtons();

            ButtonId selectedAnswer = mapButtonId(v.getId());
            ButtonId correctAnswer = question.getCorrectAnswer();
            if (selectedAnswer == correctAnswer) {
                player.answeredCorrect();
                setButtonColor(buttons[selectedAnswer.getId()], Constants.GREEN);
            } else {
                setButtonColor(buttons[selectedAnswer.getId()], Constants.RED);
                setButtonColor(buttons[correctAnswer.getId()], Constants.GREEN);
                player.answeredWrong();
            }

            updateScore();
            updateLives();
            updateNextButtonText();
        }
    }

    public void nextClick(View v) {
        if (isAnswered) {
            if (!player.isAlive() || !questions.hasNext()) {
                dao.updateHighscores(player);
                showMainMenu();
            } else {
                goToNextQuestion();
                resetButtons();
            }
        }
    }

    private void goToNextQuestion() {
        question = questions.next();

        questionText.setText(question.getQuestion());
        buttons[0].setText(question.getAnswerA());
        buttons[1].setText(question.getAnswerB());
        buttons[2].setText(question.getAnswerC());
        buttons[3].setText(question.getAnswerD());
        isAnswered = false;
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

    private void showMainMenu() {
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    private void resetButtons() {
        for (Button button : buttons) {
            setButtonColor(button, Constants.GREY);
        }
    }

    private void updateScore() {
        scoreText.setText(String.format("score: %s", player.getScore()));
    }

    private void updateLives() {
        livesText.setText(String.format("lives: %s", player.getLives()));
    }

    private void updateNextButtonText() {
        if (!player.isAlive()) {
            nextButton.setText("Game over");
        } else if (!questions.hasNext()) {
            nextButton.setText("Go to highscores");
        }
    }

    private void setButtonColor(Button button, int color) {
        button.getBackground().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
    }

}
