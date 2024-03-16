import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class TetrisGame extends JPanel implements ActionListener {
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
                    color = Color.CYAN;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter + 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter - 2]);
                    break;
                case J:
                    color = Color.BLUE;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 2]);
                    sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 2]);
                    break;
                case L:
                    color = new Color(204, 102, 0);
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter - 1][columnCenter]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter - 2]);
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
                    sharpTiles[0] = (tiles[rowCenter - 1][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter - 1][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter - 2]);
                    break;
                case T:
                    color = new Color(126, 0, 227);
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter - 1][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter][columnCenter - 2]);
                    break;
                case Z:
                    color = Color.RED;
                    sharpTiles[0] = (tiles[rowCenter][columnCenter]);
                    sharpTiles[1] = (tiles[rowCenter][columnCenter - 1]);
                    sharpTiles[2] = (tiles[rowCenter - 1][columnCenter - 1]);
                    sharpTiles[3] = (tiles[rowCenter - 1][columnCenter - 2]);
                    break;
            }
        }

        public void fallSharp() {
            for(int i = 0; i < 4; i++) {
                var tile = sharpTiles[i];
                sharpTiles[i] = tiles[tile.row + 1][tile.column];
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

        public boolean checkWall() {
            for (var tile : sharpTiles) {
                if (tiles[tile.row + 1][tile.column].isWall) {
                    return true;
                }
            }
            return false;
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
    public TetrisGame(TetrisFrame frame) {
        this.frame = frame;
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
        gameTimer = new Timer(300, this);
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(!currentSharp.checkWall()) {
            currentSharp.paintSharp(true);
            currentSharp.rowCenter += 1;
            currentSharp.fallSharp();
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

//    private void paintSharp(boolean cancelPaint) {
//        Color color;
//        if (cancelPaint) color = Color.BLACK;
//        else color = Color.GREEN;
//        for (var tile : currentSharp.sharpTiles) {
//            tile.setBackground(color);
//        }
//    }
}
