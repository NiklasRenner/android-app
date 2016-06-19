package dk.catnip.android.android_app.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Question {

    private String question;
    private String answerA;
    private String answerB;
    private String answerC;
    private String answerD;
    private ButtonId correctAnswer;

    public Question(String question, String answerA, String answerB, String answerC, String answerD, ButtonId correctAnswer) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public ButtonId getCorrectAnswer() {
        return correctAnswer;
    }

    public static Question fromJsonObject(JSONObject json) {
        try {
            String questionStr = json.getString("question");
            String answerA = json.getString("answer_a");
            String answerB = json.getString("answer_b");
            String answerC = json.getString("answer_c");
            String answerD = json.getString("answer_d");
            String correctAnswer = json.getString("correct_answer");
            ButtonId correctButtonId = ButtonId.fromString(correctAnswer);

            return new Question(questionStr, answerA, answerB, answerC, answerD, correctButtonId);
        } catch (JSONException ex) {
            throw new RuntimeException(ex);
        }
    }
}
