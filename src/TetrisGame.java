import javax.swing.*;
import java.awt.*;

public class TetrisGame extends JPanel {
    private TetrisFrame frame;
    public TetrisGame(TetrisFrame frame) {
        this.frame = frame;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 3 * 2 - 6, frame.DEFAULT_HEIGHT));
    }
}
