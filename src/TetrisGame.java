import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class TetrisGame extends JPanel implements ActionListener, KeyListener {

    private class Tile extends JPanel {
        private boolean isWall = false;
        private int row;
        private int column;
        public Tile(int row, int column) {
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            this.row = row;
            this.column = column;
        }
    }

    private enum SharpChoice {
        O, I, S, Z, L, J, T;

        private static final Random random = new Random();

        public static SharpChoice randomSharp()  {
            SharpChoice[] sharpChoices = values();
            return sharpChoices[random.nextInt(sharpChoices.length)];
        }
    }

    private class Sharp {
        private Direction direction = Direction.UP;
        private final SharpChoice sharpChoice;
        private int rowCenter;
        private int columnCenter;
        private Tile[] sharpTiles = new Tile[4];
        private Color color;
        public Sharp(SharpChoice sc) {
            sharpChoice = sc;
            rowCenter = 2;
            columnCenter = 6;

            switch(sharpChoice) {
                case I:
                    columnCenter = 5;
                    color = Color.CYAN;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter + 2]);
                    break;
                case J:
                    color = Color.BLUE;
                    columnCenter = 5;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 1]);
                    break;
                case L:
                    color = new Color(204, 102, 0);
                    columnCenter = 5;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[1] = (tiles[rowCenter - 1][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter - 1]);
                    break;
                case O:
                    color = Color.YELLOW;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter - 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 1]);
                    break;
                case S:
                    color = Color.GREEN;
                    rowCenter = 1;
                    columnCenter = 5;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter + 1][columnCenter - 1]);
                    break;
                case T:
                    color = new Color(126, 0, 227);
                    columnCenter = 5;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter - 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter + 1]);
                    break;
                case Z:
                    color = Color.RED;
                    rowCenter = 1;
                    columnCenter = 5;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter + 1][columnCenter + 1]);
                    break;
            }
        }

        public void rotateSharp() {
            switch(sharpChoice) {
                case I:
                    switch (direction) {
                        case UP:
                            if (!(tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 2][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall)) {
                                direction = Direction.RIGHT;
                                sharpTiles[0] = (tiles[rowCenter + 2][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter - 1][columnCenter]);
                            }
                            break;
                        case RIGHT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter][columnCenter + 2].isWall)) {
                                direction = Direction.DOWN;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter + 2]);
                            }
                            break;
                        case DOWN:
                            if (!(tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 2][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall)) {
                                direction = Direction.LEFT;
                                sharpTiles[0] = (tiles[rowCenter + 2][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter - 1][columnCenter]);
                            }
                            break;
                        case LEFT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter][columnCenter + 2].isWall)) {
                                direction = Direction.UP;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter + 2]);
                                break;
                            }
                    }
                    break;
                case J:
                    switch (direction) {
                        case UP:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall ||
                                    tiles[rowCenter - 1][columnCenter + 1].isWall || tiles[rowCenter + 1][columnCenter].isWall)) {
                                direction = Direction.RIGHT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter - 1][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter]);
                            }
                            break;
                        case RIGHT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter + 1][columnCenter + 1].isWall || tiles[rowCenter][columnCenter - 1].isWall)) {
                                direction = Direction.DOWN;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter - 1]);
                            }
                            break;
                        case DOWN:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter + 1][columnCenter].isWall ||
                                    tiles[rowCenter - 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter - 1].isWall)) {
                                direction = Direction.LEFT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter - 1]);
                            }
                            break;
                        case LEFT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter - 1][columnCenter - 1].isWall)) {
                                direction = Direction.UP;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 1]);
                            }
                            break;
                    }
                    break;
                case L:
                    switch (direction) {
                        case UP:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall ||
                                    tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter + 1].isWall)) {
                                direction = Direction.RIGHT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter + 1]);
                            }
                            break;
                        case RIGHT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                                    tiles[rowCenter + 1][columnCenter - 1].isWall || tiles[rowCenter][columnCenter + 1].isWall)) {
                                direction = Direction.DOWN;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter - 1]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter + 1]);
                            }
                            break;
                        case DOWN:
                            if (!(tiles[rowCenter - 1][columnCenter].isWall || tiles[rowCenter - 1][columnCenter - 1].isWall ||
                                    tiles[rowCenter][columnCenter].isWall || tiles[rowCenter + 1][columnCenter].isWall)) {
                                direction = Direction.LEFT;
                                sharpTiles[0] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter - 1]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter]);
                            }
                            break;
                        case LEFT:
                            if (!(tiles[rowCenter][columnCenter + 1].isWall || tiles[rowCenter - 1][columnCenter + 1].isWall ||
                                    tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall)) {
                                direction = Direction.UP;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter - 1]);
                            }
                            break;
                    }
                    break;
                case O:

                    break;
                case S:
                    switch (direction) {
                        case UP:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter + 1].isWall || tiles[rowCenter + 1][columnCenter + 1].isWall)) {
                                direction = Direction.RIGHT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter + 1]);
                            }
                            break;
                        case RIGHT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter - 1].isWall)) {
                                direction = Direction.DOWN;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter - 1]);
                            }
                            break;
                        case DOWN:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter + 1].isWall || tiles[rowCenter + 1][columnCenter + 1].isWall)) {
                                direction = Direction.LEFT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter + 1]);
                            }
                            break;
                        case LEFT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter - 1].isWall)) {
                                direction = Direction.UP;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter - 1]);
                            }
                            break;
                    }
                    break;
                case T:
                    switch (direction) {
                        case UP:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter - 1][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter + 1].isWall || tiles[rowCenter + 1][columnCenter].isWall)) {
                                direction = Direction.RIGHT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter]);
                            }
                            break;
                        case RIGHT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter + 1][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter][columnCenter + 1].isWall)) {
                                direction = Direction.DOWN;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter + 1]);
                            }
                            break;
                        case DOWN:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter + 1][columnCenter].isWall ||
                                    tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter - 1][columnCenter].isWall)) {
                                direction = Direction.LEFT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[3] = (tiles[rowCenter - 1][columnCenter]);
                            }
                            break;
                        case LEFT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                                    tiles[rowCenter - 1][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall)) {
                                direction = Direction.UP;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[2] = (tiles[rowCenter - 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter][columnCenter + 1]);
                            }
                            break;
                    }
                    break;
                case Z:
                    switch (direction) {
                        case UP:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter - 1][columnCenter + 1].isWall || tiles[rowCenter + 1][columnCenter].isWall)) {
                                direction = Direction.RIGHT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter - 1][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter]);
                            }
                            break;
                        case RIGHT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                                    tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter + 1].isWall)) {
                                direction = Direction.DOWN;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter + 1]);
                            }
                            break;
                        case DOWN:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                                    tiles[rowCenter - 1][columnCenter + 1].isWall || tiles[rowCenter + 1][columnCenter].isWall)) {
                                direction = Direction.LEFT;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                                sharpTiles[2] = (tiles[rowCenter - 1][columnCenter + 1]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter]);
                            }
                            break;
                        case LEFT:
                            if (!(tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                                    tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter + 1].isWall)) {
                                direction = Direction.UP;
                                sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                                sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                                sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                                sharpTiles[3] = (tiles[rowCenter + 1][columnCenter + 1]);
                            }
                            break;
                    }
                    break;
            }
        }

        public void moveSharp(Direction dir) {
            switch (dir) {
                case DOWN:
                    for(int i = 0; i < 4; i++) {
                        var tile = sharpTiles[i];
                        sharpTiles[i] = tiles[tile.row + 1][tile.column];
                    }
                    break;
                case LEFT:
                    for(int i = 0; i < 4; i++) {
                        var tile = sharpTiles[i];
                        sharpTiles[i] = tiles[tile.row][tile.column - 1];
                    }
                    break;
                case RIGHT:
                    for(int i = 0; i < 4; i++) {
                        var tile = sharpTiles[i];
                        sharpTiles[i] = tiles[tile.row][tile.column + 1];
                    }
                    break;
            }
        }

        public void paintSharp(boolean cancelPaint) {
            Color color;
            if (cancelPaint) color = Color.BLACK;
            else color = currentSharp.color;
            for (var tile : sharpTiles) {
                tile.setBackground(color);
            }
        }

        public boolean checkWall(Direction dir) {
            switch (dir) {
                case DOWN:
                    for (var tile : sharpTiles) {
                        if (tiles[tile.row + 1][tile.column].isWall) {
                            return true;
                        }
                    }
                    return false;
                case LEFT:
                    for (var tile : sharpTiles) {
                        if (tiles[tile.row][tile.column - 1].isWall) {
                            return true;
                        }
                    }
                    return false;
                case RIGHT:
                    for (var tile : sharpTiles) {
                        if (tiles[tile.row][tile.column + 1].isWall) {
                            return true;
                        }
                    }
                    return false;
                default:
                    return false;
            }
        }

        public void doWall() {
            for (var tile: sharpTiles) {
                tile.isWall = true;
            }
        }
    }
    private TetrisFrame frame;
    private final int TILES_COLUMNS_QUANTITY = 12;
    private final int TILES_ROW_QUANTITY = 22;
    private Tile[][] tiles;
    private Sharp currentSharp;
    private Timer gameTimer;
    private final int GAME_SPEED = 500;
    public TetrisGame(TetrisFrame frame) {
        this.frame = frame;
        setFocusable(true);
        addKeyListener(this);
        setBackground(Color.BLUE);
        setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 3 * 2, frame.DEFAULT_HEIGHT));
        tiles = new Tile[TILES_ROW_QUANTITY][TILES_COLUMNS_QUANTITY];
        setLayout(new GridLayout(TILES_ROW_QUANTITY, TILES_COLUMNS_QUANTITY));


        for(int i = 0; i < TILES_ROW_QUANTITY; i++) {
            for (int j = 0; j < TILES_COLUMNS_QUANTITY; j++) {
                var tile = new Tile(i, j);
                tiles[i][j] = tile;
                if (i == 0 || i == TILES_ROW_QUANTITY - 1 || j == 0 || j == TILES_COLUMNS_QUANTITY - 1) {
                    tile.setBackground(Color.LIGHT_GRAY);
                    tile.isWall = true;
                }
                add(tile);
            }
        }
        createSharp(SharpChoice.randomSharp());
//        currentSharp.paintSharp(false);
//        createSharp(SharpChoice.I);
        gameTimer = new Timer(GAME_SPEED, this);
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!currentSharp.checkWall(Direction.DOWN)) {
            currentSharp.paintSharp(true);
            currentSharp.rowCenter += 1;
            currentSharp.moveSharp(Direction.DOWN);
            currentSharp.paintSharp(false);
        }
        else {
            currentSharp.doWall();
            createSharp(SharpChoice.randomSharp());
        }
        repaint();
    }

    private void createSharp(SharpChoice sc) {
        currentSharp = new Sharp(sc);
        currentSharp.paintSharp(false);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if(!currentSharp.checkWall(Direction.RIGHT)) {
                currentSharp.paintSharp(true);
                currentSharp.columnCenter += 1;
                currentSharp.moveSharp(Direction.RIGHT);
                currentSharp.paintSharp(false);
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if(!currentSharp.checkWall(Direction.LEFT)) {
                currentSharp.paintSharp(true);
                currentSharp.columnCenter -= 1;
                currentSharp.moveSharp(Direction.LEFT);
                currentSharp.paintSharp(false);
                repaint();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            gameTimer.setDelay(50);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentSharp.paintSharp(true);
            currentSharp.rotateSharp();
            currentSharp.paintSharp(false);
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            gameTimer.setDelay(GAME_SPEED);
        }
    }
}
