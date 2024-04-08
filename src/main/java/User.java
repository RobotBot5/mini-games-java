import jakarta.persistence.*;

@Entity
@Table(name = "users_records")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "snake")
    private Integer snakeHighScore = 0;
    @Column(name = "minesweeper")
    private Integer minesweeperHighScore = 0;
    @Column(name = "tetris")
    private Integer tetrisHighScore = 0;

    public String getPassword() {
        return password;
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

    public void setName(String name) {
        this.name = name;
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

    public void setPassword(String password) {
        this.password = password;
    }
}
