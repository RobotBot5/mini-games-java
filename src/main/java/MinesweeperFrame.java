import javax.swing.*;
import java.awt.*;

/** Фрейм игры "Сапёр" */
public class MinesweeperFrame extends JFrame {
    public MinesweeperFrame(MinesweeperGame.Difficulty difficulty) {
        setTitle("Minesweeper");
        Image img = new ImageIcon("images\\mine.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        add(new MinesweeperGame(difficulty, this));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
