import javax.swing.*;
import java.awt.*;

/** Фрейм игры "Змейка" */
public class SnakeFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;

    public SnakeFrame() {
        setTitle("Snake");
        Image img = new ImageIcon("images\\snakeIcon.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        var snakeGame = new SnakeGame(DEFAULT_WIDTH, DEFAULT_HEIGHT, this);
        add(snakeGame);
        snakeGame.requestFocus(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
