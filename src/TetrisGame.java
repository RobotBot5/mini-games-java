import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TetrisGame extends JPanel {
    private class Tile extends JButton {
        public Tile() {
            setBackground(Color.RED);
        }
    }
    private TetrisFrame frame;
    private final int TILES_COLUMNS_QUANTITY = 12;
    private final int TILES_ROW_QUANTITY = 22;
    private Tile[][] tiles;
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
                add(tile);
            }
        }
    }
}
