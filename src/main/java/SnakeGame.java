import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;


/** Панель игры "Змейка" */
public class SnakeGame extends JPanel implements ActionListener, KeyListener {

    private class Tile {
        private int x;
        private int y;
        Tile(int x, int y){
            try {
                if (x >= TILES_X_QUANTITY || y >= TILES_Y_QUANTITY) throw new TileOutOfBoundesException("This tile doesn't exist");
            }
            catch (TileOutOfBoundesException e) {
                System.err.println(e.getMessage());
            }
            this.x = x;
            this.y = y;
        }

        public double getX() {
            return x * tileLength;
        }

        public double getY() {
            return y * tileLength - 1;
        }

        private void moveX(int xSpeed) {
            if (this.x + xSpeed >= TILES_X_QUANTITY) this.x = 0;
            else if (this.x + xSpeed < 0) this.x = TILES_X_QUANTITY - 1;
            else this.x += xSpeed;
        }

        private void moveY(int ySpeed) {
            if (this.y + ySpeed >= TILES_Y_QUANTITY) this.y = 0;
            else if (this.y + ySpeed < 0) this.y = TILES_Y_QUANTITY - 1;
            else this.y += ySpeed;
        }

        public void move() {
            for(int i = snakeBody.size() - 1; i > 0; i--) {
                snakeBody.get(i).copyTile(snakeBody.get(i - 1));
            }
            moveX(xSpeed);
            moveY(ySpeed);
        }

        public void copyTile(Tile tile) {
            this.x = tile.x;
            this.y = tile.y;
        }

        public boolean collision(Tile tile) {
            return Math.floorMod(this.x + xSpeed, TILES_X_QUANTITY) == tile.x && Math.floorMod(this.y + ySpeed, TILES_Y_QUANTITY) == tile.y;
        }
        public boolean collision(int x, int y) {
            return this.x == x && this.y == y;
        }

        @Override
        public String toString() {
            return "Tile[x = " + x + ", y = " + y + "]";
        }
    }
    private static final int TILES_X_QUANTITY = 20;
    private static final int INITIALLY_TAIL_SIZE = 4;
    private final int TILES_Y_QUANTITY;
    private final int panelWidth;
    private final int panelHeight;
    private final double tileLength;
    private Tile snakeHead;
    private ArrayList<Tile> snakeBody;
    private Tile food;
    private int score = 0;
    private int xSpeed = 0;
    private int ySpeed = -1;
    private Direction previousKey = Direction.UP;
    private final Timer gameTimer;
    private SnakeFrame frame;
    Random random;
    public SnakeGame(int width, int height, SnakeFrame frame) {
        this.frame = frame;
        panelWidth = width;
        tileLength = (double) panelWidth / TILES_X_QUANTITY;
        TILES_Y_QUANTITY = height / (int) tileLength;
        panelHeight = (int) (TILES_Y_QUANTITY * tileLength);
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        snakeHead = new Tile(10, 10);
        snakeBody = new ArrayList<>();

        random = new Random();

        food = new Tile(random.nextInt(TILES_X_QUANTITY), random.nextInt(TILES_Y_QUANTITY));
        snakeBody.add(snakeHead);

        for (int i = 1; i < INITIALLY_TAIL_SIZE + 1; i++) {
            snakeBody.add(new Tile(10, 10 + i));
        }

        //50
        gameTimer = new Timer(50, this);
        gameTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2 = (Graphics2D) g;

        drawGrid(g2);

        var square = new Rectangle2D.Double(snakeHead.getX(), snakeHead.getY(), tileLength, tileLength);
        g2.setPaint(new Color(0, 255, 0));
        g2.fill(square);
        g2.draw(square);

        double lengthOfEye = tileLength * 0.2;
        double xEye = tileLength * 0.2;
        double yEye = tileLength * 0.2;
        Rectangle2D.Double leftEye = null;
        Rectangle2D.Double rightEye = null;


        
        switch (previousKey) {
            case UP:
                leftEye = new Rectangle2D.Double(snakeHead.getX() + xEye - lengthOfEye / 2,
                        snakeHead.getY() + yEye - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                rightEye = new Rectangle2D.Double(snakeHead.getX() + tileLength - xEye - lengthOfEye / 2,
                        snakeHead.getY() + yEye - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                break;
            case LEFT:
                leftEye = new Rectangle2D.Double(snakeHead.getX() + yEye - lengthOfEye / 2,
                        snakeHead.getY() - xEye + tileLength - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                rightEye = new Rectangle2D.Double(snakeHead.getX() + yEye - lengthOfEye / 2,
                        snakeHead.getY() + xEye - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                break;
            case DOWN:
                leftEye = new Rectangle2D.Double(snakeHead.getX() + xEye - lengthOfEye / 2,
                        snakeHead.getY() + tileLength - yEye - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                rightEye = new Rectangle2D.Double(snakeHead.getX() + tileLength - xEye - lengthOfEye / 2,
                        snakeHead.getY() + tileLength - yEye - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                break;
            case RIGHT:
                leftEye = new Rectangle2D.Double(snakeHead.getX() + tileLength - yEye - lengthOfEye / 2,
                        snakeHead.getY() - xEye + tileLength - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                rightEye = new Rectangle2D.Double(snakeHead.getX() + tileLength - yEye - lengthOfEye / 2,
                        snakeHead.getY() + xEye - lengthOfEye / 2, lengthOfEye, lengthOfEye);
                break;
        }
        
        g2.setPaint(Color.BLACK);
        g2.fill(leftEye);
        g2.fill(rightEye);

        for (int i = 1; i < snakeBody.size(); i++) {
            var square1 = new Rectangle2D.Double(snakeBody.get(i).getX(), snakeBody.get(i).getY(), tileLength, tileLength);
            g2.setPaint(new Color(0, 204, 0));
            g2.fill(square1);
            g2.draw(square1);
        }
        var squareFood = new Rectangle2D.Double(food.getX(), food.getY(), tileLength, tileLength);
        g2.setPaint(Color.RED);
        g2.fill(squareFood);
        g2.draw(squareFood);

        g2.setFont(new Font("SansSerif", Font.BOLD, 17));
        g2.drawString("Score: " + score, 10, 30);
    }

    private void drawGrid(Graphics2D g2) {
        double tileLength  = (double) panelWidth / TILES_X_QUANTITY;

        var verticalLines = new Line2D.Double[TILES_X_QUANTITY - 1];
        for (int i = 1; i < TILES_X_QUANTITY; i++) {
            verticalLines[i - 1] = new Line2D.Double(i * tileLength, 0, i * tileLength, panelHeight);
        }

        var horizontalLines = new Line2D.Double[TILES_Y_QUANTITY - 1];
        for (int i = 1; i < TILES_Y_QUANTITY; i++) {
            horizontalLines[i - 1] = new Line2D.Double(0, i * tileLength - 1, panelWidth, i * tileLength - 1);
        }

        g2.setPaint(new Color(105, 105, 105, 169));
        for (var line : verticalLines) {
            g2.draw(line);
        }

        for (var line : horizontalLines) {
            g2.draw(line);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        System.out.println("================");
        for (var tile : snakeBody) {
//            System.out.println(tile);
            if (snakeHead.collision(tile)) {
                endGame();
                return;
            }
        }

        int xFood = random.nextInt(TILES_X_QUANTITY);
        int yFood = random.nextInt(TILES_Y_QUANTITY);

        boolean isNewFoodAdded = snakeHead.collision(food);
        if(isNewFoodAdded) {
            score++;
            snakeBody.add(new Tile(0, 0));
            food = new Tile(xFood, yFood);
        }

        snakeHead.move();

        if (isNewFoodAdded) {
            boolean isCollision = true;
            while (isCollision) {
                isCollision = false;
                for (var snakePart : snakeBody) {
                    if (snakePart.collision(xFood, yFood)) {
                        isCollision = true;
                        xFood = random.nextInt(TILES_X_QUANTITY);
                        yFood = random.nextInt(TILES_Y_QUANTITY);
                        food = new Tile(xFood, yFood);
                        break;
                    }
                }
            }
        }

        if (xSpeed == -1) {
            previousKey = Direction.LEFT;
        }
        else if (xSpeed == 1) {
            previousKey = Direction.RIGHT;
        }
        else if (ySpeed == -1) {
            previousKey = Direction.UP;
        }
        else {
            previousKey = Direction.DOWN;
        }

        repaint();
    }

    private void endGame() {
//        System.exit(0);
        gameTimer.stop();
        String message;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User existingUser = session.byId(User.class).load(Menu.menu.getUser().getName());
            if(score > existingUser.getSnakeHighScore()) {
                existingUser.setSnakeHighScore(score);
                Transaction transaction = session.beginTransaction();
                session.update(existingUser);
                transaction.commit();
                message = "You died!\nYour score: " + score +
                        "\nNew Highscore!";
            }
            else {
                message = "You died!\nYour score: " + score +
                        "\nHigh score: " + existingUser.getSnakeHighScore();
            }
        }

        int option = JOptionPane.showOptionDialog(this,
                message, "End game", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("images\\snakeIcon.png"),
                new String[] {"Restart", "Menu"}, "Restart");

        switch (option) {
            case 0:
                restartGame();
                break;
            case 1:
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                Menu.menu.setVisible(true);
                break;
        }
    }
    private void restartGame() {
        snakeHead = new Tile(10, 10);
        snakeBody = new ArrayList<>();

        random = new Random();

        food = new Tile(random.nextInt(TILES_X_QUANTITY), random.nextInt(TILES_Y_QUANTITY));
        snakeBody.add(snakeHead);

        for (int i = 1; i < INITIALLY_TAIL_SIZE + 1; i++) {
            snakeBody.add(new Tile(10, 10 + i));
        }

        score = 0;
        xSpeed = 0;
        ySpeed = -1;
        previousKey = Direction.UP;
        repaint();
        gameTimer.start();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ((e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) && previousKey != Direction.DOWN) {
            xSpeed = 0;
            ySpeed = -1;
        }
        else if((e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) && previousKey != Direction.UP) {
            xSpeed = 0;
            ySpeed = 1;
        }
        else if((e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) && previousKey != Direction.RIGHT) {
            xSpeed = -1;
            ySpeed = 0;
        }
        else if((e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) && previousKey != Direction.LEFT) {
            xSpeed = 1;
            ySpeed = 0;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
