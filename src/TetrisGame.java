import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TetrisGame extends JPanel implements ActionListener {
    private class Tile extends JPanel {
        public Tile() {
            setBackground(Color.RED);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        }
    }

    private enum SharpChoice {
        O, I, S, Z, L, J, T
    }

    private class Sharp {
        private Direction direction = Direction.UP;
        private final SharpChoice sharpChoice;
        private int rowCenter;
        private int columnCenter;
        public Sharp(SharpChoice sc) {
            sharpChoice = sc;
            rowCenter = 2;
            columnCenter = 6;
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
                var tile = new Tile();
                tiles[i][j] = tile;
                if (i == 0 || i == TILES_ROW_QUANTITY - 1 || j == 0 || j == TILES_COLUMNS_QUANTITY - 1)
                    tile.setBackground(Color.LIGHT_GRAY);
                add(tile);
            }
        }
        createSharp(SharpChoice.I);
        paintSharp(false);
        gameTimer = new Timer(1000, this);
        gameTimer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        paintSharp(true);
        currentSharp.rowCenter += 1;
        paintSharp(false);
    }

    private void createSharp(SharpChoice sc) {
        currentSharp = new Sharp(sc);
    }

    private void paintSharp(boolean cancelPaint) {
        int rowCenter = currentSharp.rowCenter;
        int columnCenter = currentSharp.columnCenter;
        Color color;
        if(cancelPaint) color = Color.RED;
        else color = Color.GREEN;
        switch(currentSharp.sharpChoice) {
            case I:
                switch (currentSharp.direction) {
                    case UP:
                        tiles[rowCenter][columnCenter].setBackground(color);
                        tiles[rowCenter][columnCenter + 1].setBackground(color);
                        tiles[rowCenter][columnCenter - 1].setBackground(color);
                        tiles[rowCenter][columnCenter - 2].setBackground(color);
                        break;
                    case DOWN:

                        break;
                    case LEFT:

                        break;
                    case RIGHT:

                        break;
                }
                break;
            case J:

                break;
            case L:

                break;
            case O:

                break;
            case S:

                break;
            case T:

                break;
            case Z:

                break;
        }
    }
}
