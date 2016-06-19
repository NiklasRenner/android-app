package dk.catnip.android.android_app.utils;

public class Constants {

    //data retrival constants
    public static final String PREFERENCES = "QUIZ_APP_V2";
    public static final String SCORE_DATA = "SCORE_DATA";
    public static final String NAME_DATA = "NAME_DATA";

    //default player name
    public static final String DEFAULT_NAME = "PLAYER";

    //call codes for activities
    public static final int QUIZ_CODE = 100;
    public static final int END_CODE = 105;

    //gameplay vars
    public static final int CORRECT_ANSWER_POINTS = 100;
    public static final int WRONG_ANSWER_POINTS = -50;
    public static final int STARTING_LIVES = 3;

    //colors
    public static final int GREY = 0xFFDDDDDD;
    public static final int RED = 0xFFFF0000;
    public static final int GREEN = 0xFF00FF00;

    private Constants() {
        //don't
    }

}
