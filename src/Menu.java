import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JFrame{
    private JPanel rootPanel;
    private JButton minesweeperButton;
    private JButton tetrisButton;
    private JButton snakeButton;
    private JButton settingsButton;
    private JButton rulesButton;
    private JButton accountButton;
    public static Menu menu;

    public Menu() {
        menu = this;
        setContentPane(rootPanel);
        setTitle("Java Minigames");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(800, 600);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        snakeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new SnakeFrame();
            }
        });
        minesweeperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new MinesweeperFrame();
            }
        });
        tetrisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new TetrisFrame();
            }
        });
    }

    public static void main(String[] args) {
        new Menu();
    }
}
