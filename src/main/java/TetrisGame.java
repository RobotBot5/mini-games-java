import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/** Панель игры "Тетрис" */
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

        public void copyOf(Tile otherTile) {
            isWall = otherTile.isWall;
            setBackground(otherTile.getBackground());
        }
    }

    /** Перечисление видов фигур в игре "Тетрис" */
    public enum SharpChoice {
        O, I, S, Z, L, J, T;

        private static final Random random = new Random();

        public static SharpChoice randomSharp()  {
            SharpChoice[] sharpChoices = values();
            return sharpChoices[random.nextInt(sharpChoices.length)];
        }
    }

    private class Sharp {
        private Direction direction = Direction.UP;
        private boolean endGame;
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
                    if (tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                            tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter][columnCenter + 2].isWall)
                        this.endGame = true;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter + 2]);
                    break;
                case J:
                    color = Color.BLUE;
                    columnCenter = 5;
                    if (tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                            tiles[rowCenter][columnCenter - 1].isWall || tiles[rowCenter - 1][columnCenter - 1].isWall)
                        this.endGame = true;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 1]);
                    break;
                case L:
                    color = new Color(204, 102, 0);
                    columnCenter = 5;
                    if (tiles[rowCenter][columnCenter + 1].isWall || tiles[rowCenter - 1][columnCenter + 1].isWall ||
                            tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall)
                        this.endGame = true;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[1] = (tiles[rowCenter - 1][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter - 1]);
                    break;
                case O:
                    color = Color.YELLOW;
                    if (tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                            tiles[rowCenter - 1][columnCenter].isWall || tiles[rowCenter - 1][columnCenter - 1].isWall)
                        this.endGame = true;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter - 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 1]);
                    break;
                case S:
                    color = Color.GREEN;
                    rowCenter = 1;
                    columnCenter = 5;
                    if (tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall ||
                            tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter - 1].isWall)
                        this.endGame = true;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter + 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter + 1][columnCenter - 1]);
                    break;
                case T:
                    color = new Color(126, 0, 227);
                    columnCenter = 5;
                    if (tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                            tiles[rowCenter - 1][columnCenter].isWall || tiles[rowCenter][columnCenter + 1].isWall)
                        this.endGame = true;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter - 1][columnCenter]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter + 1]);
                    break;
                case Z:
                    color = Color.RED;
                    rowCenter = 1;
                    columnCenter = 5;
                    if (tiles[rowCenter][columnCenter].isWall || tiles[rowCenter][columnCenter - 1].isWall ||
                            tiles[rowCenter + 1][columnCenter].isWall || tiles[rowCenter + 1][columnCenter + 1].isWall)
                        this.endGame = true;
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
    private int score = 0;
    private Queue<SharpChoice> sharpsQueue = new LinkedList<>();
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
        for (int i = 0; i < 4; i++) {
            sharpsQueue.add(SharpChoice.randomSharp());
        }
        frame.tetrisInfo.nextBlocksPanel.updateQueue((List<SharpChoice>) sharpsQueue);
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
            var rowsToCheck = new PriorityQueue<>(Arrays.stream(currentSharp.sharpTiles)
                    .map(tile -> tile.row).distinct().toList());
            int clearLineCount = 0;
            while (!rowsToCheck.isEmpty()) {
                var row = rowsToCheck.remove();
                boolean clearLine = true;
                for (int j = 1; j < TILES_COLUMNS_QUANTITY - 1; j++) {
                    if(!tiles[row][j].isWall) {
                        clearLine = false;
                        break;
                    }
                }
                if(clearLine) {
                    clearLineCount++;
                    for (int i = row; i >= 2; i--) {
                        for (int j = 1; j < TILES_COLUMNS_QUANTITY - 1; j++) {
                            tiles[i][j].copyOf(tiles[i - 1][j]);
                        }
                    }
                    for (int j = 1; j < TILES_COLUMNS_QUANTITY - 1; j++) {
                        tiles[1][j].isWall = false;
                        tiles[1][j].setBackground(Color.BLACK);
                    }
                }
            }
            increaseScore(clearLineCount);
            frame.tetrisInfo.increaseScore(score);
            createSharp(sharpsQueue.remove());
            sharpsQueue.add(SharpChoice.randomSharp());
            frame.tetrisInfo.nextBlocksPanel.updateQueue((List<SharpChoice>) sharpsQueue);

//            createSharp(SharpChoice.I);
        }
        repaint();
    }

    private void increaseScore(int tetrisCombo) {
        var sharpChoice = currentSharp.sharpChoice;
        if (tetrisCombo == 0) {
            if (sharpChoice == SharpChoice.Z || sharpChoice == SharpChoice.S) {
                score += 16;
            }
            else if (sharpChoice == SharpChoice.J || sharpChoice == SharpChoice.L ||
                    sharpChoice == SharpChoice.O || sharpChoice == SharpChoice.T) {
                score += 17;
            }
            else if (sharpChoice == SharpChoice.I) {
                score += 18;
            }
        }
        else if (tetrisCombo == 1) score += 100;
        else if (tetrisCombo == 2) score += 300;
        else if (tetrisCombo == 3) score += 700;
        else if (tetrisCombo == 4) score += 1500;
    }

    private void createSharp(SharpChoice sc) {
        var sharp = new Sharp(sc);
        if (sharp.endGame) endGame();
        else {
            currentSharp = new Sharp(sc);
            currentSharp.paintSharp(false);
        }
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
    private void endGame() {
//        System.exit(0);
        gameTimer.stop();
        String message;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User existingUser = session.byId(User.class).load(Menu.menu.getUser().getName());
            if(score > existingUser.getTetrisHighScore()) {
                existingUser.setTetrisHighScore(score);
                Transaction transaction = session.beginTransaction();
                session.update(existingUser);
                transaction.commit();
                message = "Game over!\nYour score: " + score +
                        "\nNew Highscore!";
            }
            else {
                message = "Game over!\nYour score: " + score +
                        "\nHigh score: " + existingUser.getTetrisHighScore();
            }
        }

        int option = JOptionPane.showOptionDialog(this,
                message, "End game", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("images\\tetrisIcon.png"),
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
        for(int i = 0; i < TILES_ROW_QUANTITY; i++) {
            for (int j = 0; j < TILES_COLUMNS_QUANTITY; j++) {
                var tile = tiles[i][j];

                if (!(i == 0 || i == TILES_ROW_QUANTITY - 1 || j == 0 || j == TILES_COLUMNS_QUANTITY - 1)) {
                    tile.setBackground(Color.BLACK);
                    tile.isWall = false;
                }
            }
        }
        score = 0;
        sharpsQueue.clear();
        for (int i = 0; i < 4; i++) {
            sharpsQueue.add(SharpChoice.randomSharp());
        }
        createSharp(SharpChoice.randomSharp());
//        currentSharp.paintSharp(false);
//        createSharp(SharpChoice.I);
        repaint();
        frame.tetrisInfo.increaseScore(score);
        frame.tetrisInfo.nextBlocksPanel.updateQueue((List<SharpChoice>) sharpsQueue);
        gameTimer = new Timer(GAME_SPEED, this);
        gameTimer.start();
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
