import javax.swing.*;
import java.awt.*;

public class TetrisFrame extends JFrame {
    public final int DEFAULT_WIDTH = 540;
    public final int DEFAULT_HEIGHT = 660;
    public TetrisFrame() {
        setTitle("Tetris");
        Image img = new ImageIcon("images\\tetris.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        add(new TetrisGame(this));
        add(new TetrisInfo(this));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(TetrisFrame::new);
    }
}