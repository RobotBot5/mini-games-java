import javax.swing.*;
import java.awt.*;

public class App {

    public static void main(String[] args) {
        EventQueue.invokeLater(GameFrame::new);
    }
}

class GameFrame extends JFrame {
    private static final int DEFAULT_WIDTH = 800;
    private static final int DEFAULT_HEIGHT = 600;
    public GameFrame() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int xPosition = (screenSize.width - DEFAULT_WIDTH) / 2;
        int yPosition = (screenSize.height - DEFAULT_HEIGHT) / 2;
        Image img = new ImageIcon("images\\snakeIcon.png").getImage();


        setLocation(xPosition, yPosition);
        setTitle("Snake");
        setIconImage(img);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

//        var snakeGame = new SnakeGame(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//        add(snakeGame);
//        snakeGame.requestFocus(true);

        var minesweeperGame = new MinesweeperGame(MinesweeperGame.Difficulty.MEDIUM);
        add(minesweeperGame);

        xPosition = (screenSize.width - minesweeperGame.panelWidth) / 2;
        yPosition = (screenSize.height - minesweeperGame.panelHeight) / 2;
        setLocation(xPosition, yPosition);

        pack();
        setVisible(true);
    }
}
