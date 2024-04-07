import jakarta.persistence.Entity;

//@Entity

public class User {
    private final String name;
    private int snakeHighScore;
    private int minesweeperHighScore;
    private int tetrisHighScore;

    public User(String name) {
        this.name = name;
        snakeHighScore = 0;
        minesweeperHighScore = 0;
        tetrisHighScore = 0;
    }
    public String getName() {
        return name;
    }
    public int getMinesweeperHighScore() {
        return minesweeperHighScore;
    }

    public int getSnakeHighScore() {
        return snakeHighScore;
    }

    public int getTetrisHighScore() {
        return tetrisHighScore;
    }

    public void setMinesweeperHighScore(int minesweeperHighScore) {
        this.minesweeperHighScore = minesweeperHighScore;
    }

    public void setSnakeHighScore(int snakeHighScore) {
        this.snakeHighScore = snakeHighScore;
    }

    public void setTetrisHighScore(int tetrisHighScore) {
        this.tetrisHighScore = tetrisHighScore;
    }
}
