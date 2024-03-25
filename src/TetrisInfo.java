import javax.swing.*;
import java.awt.*;

public class TetrisInfo extends JPanel {
    private TetrisFrame frame;
    private JLabel scoreText = new JLabel("Score: 0");
    public void increaseScore(int score) {
        scoreText.setText("Score: " + score);
    }
    public TetrisInfo(TetrisFrame frame) {
        this.frame = frame;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 3, frame.DEFAULT_HEIGHT));
        add(scoreText);
        scoreText.setForeground(Color.WHITE);
    }
}
