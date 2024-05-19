import com.formdev.flatlaf.FlatDarculaLaf;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import java.awt.*;

/** Класс меню приложения. Точка входа в приложение */
public class Menu extends JFrame {
    private JPanel rootPanel;
    private JButton minesweeperButton;
    private JButton tetrisButton;
    private JButton snakeButton;
    private JButton rulesButton;
    private JButton accountButton;
    private JLabel appTitle;
    /** Поле, хранящее объект класса Menu*/
    public static Menu menu;
    private User user;
    private PasswordChooser dialog = null;

    /** Конструктор */

    public Menu() {
        HibernateUtil.getSessionFactory();
        menu = this;
        setContentPane(rootPanel);
        setTitle("Java Minigames");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
//        setSize(800, 600);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        snakeButton.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(menu, "Войдите в аккаунт",
                        "Вход в аккаунт", JOptionPane.WARNING_MESSAGE);
                return;
            }
            setVisible(false);
            EventQueue.invokeLater(SnakeFrame::new);
        });
        minesweeperButton.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(menu, "Войдите в аккаунт",
                        "Вход в аккаунт", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int option = JOptionPane.showOptionDialog(menu,
                    "Выберете сложность", "Выбор сложности", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    new ImageIcon("images\\mine.png"),
                    new String[] {"Легкий", "Средний", "Сложный"}, "Средний");
            MinesweeperGame.Difficulty difficulty = null;
            switch (option) {
                case 0:
                    difficulty = MinesweeperGame.Difficulty.EASY;
                    break;
                case 1:
                    difficulty = MinesweeperGame.Difficulty.MEDIUM;
                    break;
                case 2:
                    difficulty = MinesweeperGame.Difficulty.HARD;
                    break;
            }
            setVisible(false);
            MinesweeperGame.Difficulty finalDifficulty = difficulty;
            EventQueue.invokeLater(() -> new MinesweeperFrame(finalDifficulty));
        });
        tetrisButton.addActionListener(e -> {
            if (user == null) {
                JOptionPane.showMessageDialog(menu, "Войдите в аккаунт",
                        "Вход в аккаунт", JOptionPane.WARNING_MESSAGE);
                return;
            }
            setVisible(false);
            EventQueue.invokeLater(TetrisFrame::new);
        });
        accountButton.addActionListener(e -> {
            dialog = new PasswordChooser(this);

            if (dialog.showDialog(Menu.this, "Connect")) {
                user = dialog.getUser();
            }
        });
        rulesButton.addActionListener(e -> new RulesDialog(this).setVisible(true));

//        String userName = JOptionPane.showInputDialog(null,
//                "Name:", "User", JOptionPane.QUESTION_MESSAGE);
//        user = new User();
//        user.setName(userName);
    }

    /**
     * Функция получения значения поля user
     * @return возвращает пользователя
     */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Точка входа в приложение
     * @param args - аргументы
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        EventQueue.invokeLater(Menu::new);
    }
}

class PasswordChooser extends JPanel {
    private JLabel errorMessageLabelLogin;
    private JLabel errorMessageLabelRegister;
    private CardLayout cardLayout;
    private JPanel cards;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginOkButton;
    private JButton registerOkButton;

    private JButton logoutButton;
    private boolean ok;
    private JDialog dialog;
    private JTextField newUsernameField;
    private JPasswordField newPasswordField;
    private User user;

    public PasswordChooser(Menu menu) {
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        if (menu.getUser() != null) {
            logoutButton = new JButton("Выйти из аккаунта");
            logoutButton.addActionListener(e -> {
                menu.setUser(null);
                cardLayout.show(cards, "init");
            });
            JPanel logoutPanel = new JPanel(new GridLayout(2, 1));
            logoutPanel.add(new JLabel("Имя пользователя: " + menu.getUser().getName()));
            logoutPanel.add(logoutButton);
            cards.add(logoutPanel, "logout");
        }

        loginOkButton = new JButton("Ok");
        loginOkButton.addActionListener(event -> {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                User existingUser = session.byId(User.class).load(usernameField.getText());
                if (existingUser != null && existingUser.getPassword().equals(new String(passwordField.getPassword()))) {
                    user = existingUser;
                    ok = true;
                    dialog.setVisible(false);
                } else {
                    errorMessageLabelLogin.setText("Неверные данные!");
                    errorMessageLabelLogin.setForeground(Color.RED);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        registerOkButton = new JButton("Ok");
        registerOkButton.addActionListener(event -> {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                User existingUser = session.byId(User.class).load(newUsernameField.getText());
                if (existingUser == null) {
                    Transaction transaction = session.beginTransaction();
                    User u = new User();
                    u.setPassword(new String(newPasswordField.getPassword()));
                    u.setName(newUsernameField.getText());

                    session.save(u);
                    transaction.commit();
                    user = u;
                    ok = true;
                    dialog.setVisible(false);
                }
                else {
                    errorMessageLabelRegister.setText("Логин занят!");
                    errorMessageLabelRegister.setForeground(Color.RED);
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        var loginCancelButton = new JButton("Отмена");
        loginCancelButton.addActionListener(event -> dialog.setVisible(false));

        var registerCancelButton = new JButton("Отмена");
        registerCancelButton.addActionListener(event -> dialog.setVisible(false));

        // Panel for login
        JPanel loginPanel = new JPanel(new GridLayout(4, 1));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        errorMessageLabelLogin = new JLabel();
        loginPanel.add(new JLabel("Имя пользователя:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Пароль:"));
        loginPanel.add(passwordField);
        loginPanel.add(loginOkButton);
        loginPanel.add(loginCancelButton);
        loginPanel.add(errorMessageLabelLogin);

        // Panel for registration
        JPanel registerPanel = new JPanel(new GridLayout(4, 1));
        newUsernameField = new JTextField();
        newPasswordField = new JPasswordField();
        errorMessageLabelRegister = new JLabel();
        registerPanel.add(new JLabel("Новое имя пользователя:"));
        registerPanel.add(newUsernameField);
        registerPanel.add(new JLabel("Пароль:"));
        registerPanel.add(newPasswordField);
        registerPanel.add(registerOkButton);
        registerPanel.add(registerCancelButton);
        registerPanel.add(errorMessageLabelRegister);

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
        if (menu.getUser() != null) {
            cardLayout.show(cards, "logout");
        }
        else cardLayout.show(cards, "init");
        add(cards);
    }

    public User getUser()
    {
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
        dialog.setLocationRelativeTo(owner);
        dialog.setVisible(true);
        return ok;
    }
}
class RulesDialog extends JDialog {
    public RulesDialog(JFrame parent) {
        super(parent, "Правила игр", true);
        setLayout(new BorderLayout());

        JTextPane rulesText = new JTextPane();
        rulesText.setContentType("text/html");
        rulesText.setText("<html>"
                + "<h2 style='text-align:center;'>Тетрис</h2>"
                + "<ol>"
                + "<li>Фигуры падают сверху игрового поля.</li>"
                + "<li>Игрок может перемещать и вращать фигуры, чтобы они заполнили горизонтальные линии без пробелов.</li>"
                + "<li>Когда линия полностью заполняется, она исчезает, и игрок получает очки.</li>"
                + "<li>Скорость падения фигур постепенно увеличивается.</li>"
                + "<li>Игра заканчивается, когда фигуры выходят за игровое поле.</li>"
                + "<li>Цель: набрать как можно больше очков.</li>"
                + "</ol>"
                + "<h2 style='text-align:center;'>Змейка</h2>"
                + "<ol>"
                + "<li>Игрок управляет змейкой, которая движется по игровому полю.</li>"
                + "<li>Змейка увеличивается в длину, когда съедает яблоко.</li>"
                + "<li>Игрок должен избегать столкновения с собственным хвостом.</li>"
                + "<li>Игра продолжается до тех пор, пока змейка не врежется.</li>"
                + "<li>Цель: съесть как можно больше яблок.</li>"
                + "</ol>"
                + "<h2 style='text-align:center;'>Сапер</h2>"
                + "<ol>"
                + "<li>Игровое поле состоит из скрытых клеток, под которыми могут находиться мины или пустые пространства.</li>"
                + "<li>Игрок открывает клетки, стараясь не попасть на мины.</li>"
                + "<li>Открытые клетки могут содержать числа, которые указывают количество мин в соседних клетках.</li>"
                + "<li>Игрок может отмечать клетки, где, по его мнению, находятся мины.</li>"
                + "<li>Игра заканчивается, если игрок открывает клетку с миной.</li>"
                + "<li>Цель: открыть все безопасные клетки как можно быстрее.</li>"
                + "</ol>"
                + "</html>");
        rulesText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(rulesText);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        add(scrollPane, BorderLayout.CENTER);

        JButton closeButton = new JButton("Закрыть");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);

        // Настройка прокрутки наверх при открытии
        rulesText.setCaretPosition(0);

        setSize(450, 350);
        setLocationRelativeTo(parent);
    }
}
