import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    @Column(name = "minesweeperEasy")
    private Integer minesweeperEasyHighScore = Integer.MAX_VALUE;

    @Column(name = "minesweeperMedium")
    private Integer minesweeperMediumHighScore = Integer.MAX_VALUE;

    @Column(name = "minesweeperHard")
    private Integer minesweeperHardHighScore = Integer.MAX_VALUE;
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

    public Integer getMinesweeperEasyHighScore() {
        return minesweeperEasyHighScore;
    }

    public Integer getMinesweeperMediumHighScore() {
        return minesweeperMediumHighScore;
    }

    public Integer getMinesweeperHardHighScore() {
        return minesweeperHardHighScore;
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

    public void setMinesweeperEasyHighScore(Integer minesweeperEasyHighScore) {
        this.minesweeperEasyHighScore = minesweeperEasyHighScore;
    }

    public void setMinesweeperMediumHighScore(Integer minesweeperMediumHighScore) {
        this.minesweeperMediumHighScore = minesweeperMediumHighScore;
    }

    public void setMinesweeperHardHighScore(Integer minesweeperHardHighScore) {
        this.minesweeperHardHighScore = minesweeperHardHighScore;
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
