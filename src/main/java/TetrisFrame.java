import javax.swing.*;
import java.awt.*;

/** Фрейм игры "Тетрис" */
public class TetrisFrame extends JFrame {
    /** Ширина фрейма по умолчанию */
    public final int DEFAULT_WIDTH = 540;
    /** Длина фрейма по умолчанию */
    public final int DEFAULT_HEIGHT = 660;
    public final TetrisInfo tetrisInfo;
    public TetrisFrame() {
        setTitle("Tetris");
        Image img = new ImageIcon("images\\tetris.png").getImage();
        setIconImage(img);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        tetrisInfo = new TetrisInfo(this);
        var tetrisGame = new TetrisGame(this);
        add(tetrisGame);
        add(tetrisInfo);
        tetrisGame.requestFocus();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(TetrisFrame::new);
    }
}