import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

/** Класс меню приложения. Точка входа в приложение */
public class Menu extends JFrame {
    private JPanel rootPanel;
    private JButton minesweeperButton;
    private JButton tetrisButton;
    private JButton snakeButton;
    private JButton settingsButton;
    private JButton rulesButton;
    private JButton accountButton;
    /** Поле, хранящее объект класса Menu*/
    public static Menu menu;
    private User user;
    private PasswordChooser dialog = null;

    /** Конструктор */

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

//        String userName = JOptionPane.showInputDialog(null,
//                "Name:", "User", JOptionPane.QUESTION_MESSAGE);
//        user = new User();
//        user.setName(userName);


        if (dialog == null) dialog = new PasswordChooser();

        if (dialog.showDialog(Menu.this, "Connect")) {
            User u = dialog.getUser();
            user = u;
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            properties.put(Environment.URL, "jdbc:mysql://localhost:3306/trpp_project_db");
            properties.put(Environment.USER, "root");
            properties.put(Environment.PASS, "178197rVr!");

            SessionFactory sessionFactory = new Configuration()
                    .setProperties(properties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.save(user);
                transaction.commit();
            }
        }
    }

    /**
     * Функция получения значения поля user
     * @return возвращает пользователя
     */
    public User getUser() {
        return user;
    }

    /**
     * Точка входа в приложение
     * @param args - аргументы
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(Menu::new);
    }
}

class PasswordChooser extends JPanel {
    private CardLayout cardLayout;
    private JPanel cards;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton okButton;
    private boolean ok;
    private JDialog dialog;
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;

    public PasswordChooser() {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        okButton = new JButton("Ok");
        okButton.addActionListener(event -> {
            ok = true;
            dialog.setVisible(false);
        });

        var cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dialog.setVisible(false));

        // Panel for login
        JPanel loginPanel = new JPanel(new GridLayout(3, 1));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginPanel.add(new JLabel("Имя пользователя:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Пароль:"));
        loginPanel.add(passwordField);
        loginPanel.add(okButton);
        loginPanel.add(cancelButton);

        // Panel for registration
        JPanel registerPanel = new JPanel(new GridLayout(3, 1));
        newUsernameField = new JTextField();
        newPasswordField = new JPasswordField();
        registerPanel.add(new JLabel("Новое имя пользователя:"));
        registerPanel.add(newUsernameField);
        registerPanel.add(new JLabel("Пароль:"));
        registerPanel.add(newPasswordField);
        registerPanel.add(okButton);
        registerPanel.add(cancelButton);

        cards.add(loginPanel, "login");
        cards.add(registerPanel, "register");

        loginButton = new JButton("Войти");
        loginButton.addActionListener(e -> cardLayout.show(cards, "login"));
        loginButton.setPreferredSize(new Dimension(80, 30));

        registerButton = new JButton("Зарегистрироваться");
        registerButton.addActionListener(e -> cardLayout.show(cards, "register"));
        registerButton.setPreferredSize(new Dimension(160, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        cards.add(buttonPanel, "init");
        cardLayout.show(cards, "init");
        add(cards);
    }

    public User getUser()
    {
        var user = new User();
        user.setName(newUsernameField.getText());
        user.setPassword(new String(newPasswordField.getPassword()));
        return user;
    }

    public boolean showDialog(Component parent, String title)
    {
        ok = false;

        Frame owner;
        if (parent instanceof Frame)
            owner = (Frame) parent;
        else
            owner = (Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);


        if (dialog == null || dialog.getOwner() != owner)
        {
            dialog = new JDialog(owner, true);
            dialog.add(this);
            dialog.pack();
        }

        dialog.setTitle("Вход в аккаунт");
        dialog.setVisible(true);
        return ok;
    }
}
