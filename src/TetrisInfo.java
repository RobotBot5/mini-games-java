import javax.swing.*;
import java.awt.*;

public class TetrisInfo extends JPanel {
    private TetrisFrame frame;
    public TetrisInfo(TetrisFrame frame) {
        this.frame = frame;
        setBackground(Color.GREEN);
        setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 3, frame.DEFAULT_HEIGHT));
    }
}
