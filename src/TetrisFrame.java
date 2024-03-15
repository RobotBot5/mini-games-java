import javax.swing.*;
import java.awt.*;

public class TetrisFrame extends JFrame {
    public final int DEFAULT_WIDTH = 800;
    public final int DEFAULT_HEIGHT = 800;
    public TetrisFrame() {
        setTitle("Tetris");
        Image img = new ImageIcon("images\\tetris.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        add(new TetrisGame(this));
        add(new TetrisInfo(this));
        System.out.println(getLayout());
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TetrisFrame();
    }
}