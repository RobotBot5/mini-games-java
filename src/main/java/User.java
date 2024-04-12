import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

/** Класс пользователя. Поля связываются с базой данных с помощью Hibernate */
@Entity
@Table(name = "users_records")
public class User {
    @Id
    @Column(name = "name", unique = true)
    private String name;
    /** Пароль пользователя. */
    @Column(name = "password")
    private String password;
    /** Рекорд пользователя в игре "Змейка". */
    @Column(name = "snake")
    private Integer snakeHighScore = 0;
    /** Рекорд пользователя в игре "Сапёр". */
    @Column(name = "minesweeper")
    private Integer minesweeperHighScore = 0;
    /** Рекорд пользователя в игре "Тетрис". */
    @Column(name = "tetris")
    private Integer tetrisHighScore = 0;

    /**
     * Функция получения значения поля {@link User#password}
     * @return возвращает пароль пользователя
     */
    public String getPassword() {
        return password;
    }
    /**
     * Функция получения значения поля {@link User#name}
     * @return возвращает имя пользователя
     */
    public String getName() {
        return name;
    }
    /**
     * Функция получения значения поля {@link User#minesweeperHighScore}
     * @return возвращает рекорд пользователя в игре "Сапёр"
     */
    public int getMinesweeperHighScore() {
        return minesweeperHighScore;
    }
    /**
     * Функция получения значения поля {@link User#snakeHighScore}
     * @return возвращает рекорд пользователя в игре "Змейка"
     */

    public int getSnakeHighScore() {
        return snakeHighScore;
    }
    /**
     * Функция получения значения поля {@link User#tetrisHighScore}
     * @return возвращает рекорд пользователя в игре "Тетрис"
     */

    public int getTetrisHighScore() {
        return tetrisHighScore;
    }
    /**
     * Функция определения поля {@link User#name}
     * @param name - имя пользователя
     */

    public void setName(String name) {
        this.name = name;
    }
    /**
     * Функция определения поля {@link User#minesweeperHighScore}
     * @param minesweeperHighScore - рекорд пользователя в игре "Сапёр"
     */

    public void setMinesweeperHighScore(int minesweeperHighScore) {
        this.minesweeperHighScore = minesweeperHighScore;
    }
    /**
     * Функция определения поля {@link User#snakeHighScore}
     * @param snakeHighScore - рекорд пользователя в игре "Змейка"
     */

    public void setSnakeHighScore(int snakeHighScore) {
        this.snakeHighScore = snakeHighScore;
    }
    /**
     * Функция определения поля {@link User#tetrisHighScore}
     * @param tetrisHighScore - рекорд пользователя в игре "Тетрис"
     */

    public void setTetrisHighScore(int tetrisHighScore) {
        this.tetrisHighScore = tetrisHighScore;
    }
    /**
     * Функция определения поля {@link User#password}
     * @param password - пароль пользователя
     */

    public void setPassword(String password) {
        this.password = password;
    }
}
