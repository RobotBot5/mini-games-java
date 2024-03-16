import javax.swing.*;
import java.awt.*;
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
                EventQueue.invokeLater(SnakeFrame::new);
            }
        });
        minesweeperButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EventQueue.invokeLater(MinesweeperFrame::new);
            }
        });
        tetrisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EventQueue.invokeLater(TetrisFrame::new);
            }
        });
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(Menu::new);
    }
}
