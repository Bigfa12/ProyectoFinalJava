package main;

public class GameData {
    private static int score;

    public static void setScore(int playerScore){
        score = playerScore;
    }

    public static int getScore() {
        return score;
    }
}
