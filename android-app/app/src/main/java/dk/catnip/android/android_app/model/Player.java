package dk.catnip.android.android_app.model;


import dk.catnip.android.android_app.utils.Constants;

public class Player {

    private int lives;
    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
        this.lives = Constants.STARTING_LIVES;
        this.score = 0;
    }

    public void answeredWrong(){
        score += Constants.WRONG_ANSWER_POINTS;
        if (lives >0){
            lives--;
        }

    }

    public boolean isAlive() {
        return lives > 0;
    }


    public void answeredCorrect(){
        score += Constants.CORRECT_ANSWER_POINTS;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;

    }

}
