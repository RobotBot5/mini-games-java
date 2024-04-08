import javax.swing.*;
import java.awt.*;

/** Фрейм игры "Сапёр" */
public class MinesweeperFrame extends JFrame {
    public MinesweeperFrame() {
        setTitle("Minesweeper");
        Image img = new ImageIcon("images\\mine.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        add(new MinesweeperGame(MinesweeperGame.Difficulty.MEDIUM, this));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
