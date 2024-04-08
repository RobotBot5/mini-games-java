import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;

/** Информационная панель игры "Тетрис" */
public class TetrisInfo extends JPanel {
    private class Tile extends JPanel {
        private int row;
        private int column;
        public Tile(int row, int column) {
            setBackground(Color.BLACK);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            this.row = row;
            this.column = column;
        }
    }
    public class NextBlocksPanel extends JPanel {
        private Tile[][] tiles = new Tile[21][6];
        public NextBlocksPanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 5, 420));
            setLayout(new GridLayout(21, 6));
            for(int i = 0; i < 21; i++) {
                for (int j = 0; j < 6; j++) {
                    var tile = new Tile(i, j);
                    tiles[i][j] = tile;
                    add(tile);
                }
            }
        }

        public void updateQueue(List<TetrisGame.SharpChoice> sharpsQueue) {
            for (int i = 0; i < 21; i++) {
                for (int j = 0; j < 6; j++) {
                    tiles[i][j].setBackground(Color.BLACK);
                }
            }
            for (int i = 0; i < 4; i++) {
                var sharp = sharpsQueue.get(i);
                var startRow = 1 + i * 5;
                Color color;

                switch (sharp) {
                    case T:
                        color = new Color(126, 0, 227);
                        tiles[startRow + 1][3].setBackground(color);
                        tiles[startRow + 2][2].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        tiles[startRow + 2][4].setBackground(color);
                        break;
                    case Z:
                        color = Color.RED;
                        tiles[startRow + 1][2].setBackground(color);
                        tiles[startRow + 1][3].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        tiles[startRow + 2][4].setBackground(color);
                        break;
                    case S:
                        color = Color.GREEN;
                        tiles[startRow + 1][4].setBackground(color);
                        tiles[startRow + 1][3].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        tiles[startRow + 2][2].setBackground(color);
                        break;
                    case O:
                        color = Color.YELLOW;
                        tiles[startRow + 1][2].setBackground(color);
                        tiles[startRow + 1][3].setBackground(color);
                        tiles[startRow + 2][2].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        break;
                    case L:
                        color = new Color(204, 102, 0);
                        tiles[startRow][2].setBackground(color);
                        tiles[startRow + 1][2].setBackground(color);
                        tiles[startRow + 2][2].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        break;
                    case J:
                        color = Color.BLUE;
                        tiles[startRow][3].setBackground(color);
                        tiles[startRow + 1][3].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        tiles[startRow + 2][2].setBackground(color);
                        break;
                    case I:
                        color = Color.CYAN;
                        tiles[startRow + 2][1].setBackground(color);
                        tiles[startRow + 2][2].setBackground(color);
                        tiles[startRow + 2][3].setBackground(color);
                        tiles[startRow + 2][4].setBackground(color);
                        break;
                }
            }
            repaint();
        }
    }
    private TetrisFrame frame;
    private JLabel scoreText = new JLabel("Score: 0");
    public void increaseScore(int score) {
        scoreText.setText("Score: " + score);
    }
    public final NextBlocksPanel nextBlocksPanel;

    public TetrisInfo(TetrisFrame frame) {
        this.frame = frame;
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 3, frame.DEFAULT_HEIGHT));
        scoreText.setPreferredSize(new Dimension(frame.DEFAULT_WIDTH / 3, 100));
        add(scoreText);
        scoreText.setForeground(Color.WHITE);
        nextBlocksPanel = new NextBlocksPanel();
        add(nextBlocksPanel);
    }


}
