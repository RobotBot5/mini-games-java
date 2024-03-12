import javax.swing.*;

public class Menu extends JFrame{
    private JPanel rootPanel;
    private JButton minesweeperButton;
    private JButton emptyButton;
    private JButton snakeButton;
    private JButton settingsButton;
    private JButton rulesButton;
    private JButton accountButton;

    public Menu() {
        setContentPane(rootPanel);
        setTitle("Java Minigames");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(800, 600);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new Menu();
    }
}
