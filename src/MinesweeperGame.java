import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Random;

public class MinesweeperGame extends JPanel {
    private class Tile extends JButton {
        private boolean flagged = false;
        private boolean mine = false;
        private int mineCounts = 0;
        private boolean mark = false;
        private Color numColor;
        public boolean isFlagged() {
            return flagged;
        }

        public void setFlagged(boolean flagged) {
            this.flagged = flagged;
        }

        public boolean isMine() {
            return mine;
        }

        public void setMine(boolean mine) {
            this.mine = mine;
        }
        public void resetTile() {
            mineCounts = 0;
            flagged = false;
            mine = false;
            mark = false;
            setEnabled(true);
            setBackground(new ColorUIResource(238, 238, 238));
            setIcon(null);
            setText(null);
        }

        public void incrMineCounts() {
            mineCounts++;
            switch (mineCounts) {
                case 1:
                    numColor = Color.BLUE;
                    break;
                case 2:
                    numColor = Color.GREEN;
                    break;
                case 3:
                    numColor = Color.ORANGE;
                    break;
                case 4:
                    numColor = Color.MAGENTA;
                    break;
                case 5:
                    numColor = Color.RED;
                    break;
                case 6:
                    numColor = Color.PINK;
                    break;
                case 7:
                    numColor = Color.ORANGE;
                    break;
                case 8:
                    numColor = Color.BLACK;
                    break;
            }

            setUI(new MetalButtonUI() {
                protected Color getDisabledTextColor() {
                    return numColor;
                }
            });
        }

        public int getMineCounts() {
            return mineCounts;
        }
        public void marked() {
            this.mark = true;
        }

        public boolean isMarked() {
            return mark;
        }

        public Color getNumColor() {
            return numColor;
        }

        @Override
        public String toString() {
            return "Tile[flagged = " + this.flagged + ", mine = " + this.mine + ", mineCounts = " + this.mineCounts + "]";
        }
    }

    public enum Difficulty {
        EASY,
        MEDIUM,
        HARD,
    }
    private class Coords {
        private final int row;
        private final int column;
        public Coords(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject) return true;
            if (otherObject == null) return false;
            if (!(otherObject instanceof Coords)) return false;
            Coords other = (Coords) otherObject;
            return row == other.row && column == other.column;
        }
    }

    private int tilesRowQuantity;
    private int tilesColumnQuantity;
    private int minesQuantity;
    public final int panelWidth;
    public final int panelHeight;
    private final int tileLength = 40;
    private final Random random;
    private final HashSet<Coords> mines = new HashSet<>();
    private Tile[][] tiles;
    private int clearedTiles = 0;
    private MinesweeperFrame frame;

    public MinesweeperGame(Difficulty difficulty, MinesweeperFrame frame) {
        this.frame = frame;
        switch (difficulty) {
            case EASY:
                tilesRowQuantity = 9;
                tilesColumnQuantity = 9;
                minesQuantity = 10;
                break;
            case MEDIUM:
                tilesRowQuantity = 16;
                tilesColumnQuantity = 16;
                minesQuantity = 40;
                break;
            case HARD:
                tilesRowQuantity = 16;
                tilesColumnQuantity = 30;
                minesQuantity = 99;
                break;
        }
        tiles = new Tile[tilesRowQuantity][tilesColumnQuantity];
        random = new Random();
        while(mines.size() != minesQuantity) {
            mines.add(new Coords(random.nextInt(tilesRowQuantity), random.nextInt(tilesColumnQuantity)));
        }


        panelWidth = tileLength * tilesColumnQuantity;
        panelHeight = tileLength * tilesRowQuantity;
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setBackground(Color.BLACK);
//        setFocusable(true);

        setLayout(new GridLayout(tilesRowQuantity, tilesColumnQuantity));
        for (int i = 0; i < tilesRowQuantity; i++) {
            for (int j = 0; j < tilesColumnQuantity; j++) {
                var tile = new Tile();
                for (Coords coordsMine : mines) {
                    if (coordsMine.equals(new Coords(i, j))) {
                        tile.setMine(true);
//                        tile.setIcon(new ImageIcon("images\\mine.png"));
                    }
                }
                tiles[i][j] = tile;
                add(tile);
            }
        }

        for (int i = 0; i < tilesRowQuantity; i++) {
            for (int j = 0; j < tilesColumnQuantity; j++) {
                var tile = tiles[i][j];
                if(tile.isMine()) {
                    for (int iCounts = i - 1; iCounts <= i + 1; iCounts++) {
                        if(iCounts < 0 || iCounts >= tilesRowQuantity) continue;
                        for (int jCounts = j - 1; jCounts <= j + 1; jCounts++) {
                            if(jCounts < 0 || jCounts >= tilesColumnQuantity) continue;
                            if (!tiles[iCounts][jCounts].isMine()) {
                                tiles[iCounts][jCounts].incrMineCounts();
                            }
                        }
                    }
                }

                int finalI = i;
                int finalJ = j;
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(tile.isFlagged()) {
                            tile.setIcon(null);
                            tile.setFlagged(false);
                        }
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            tile.setIcon(new ImageIcon("images\\flag.png"));
                            tile.setFlagged(true);
                        }
                        else {
                            if (tile.isMine()) {
                                for(var coords : mines) {
                                    tiles[coords.getRow()][coords.getColumn()].setBackground(Color.WHITE);
                                    tiles[coords.getRow()][coords.getColumn()].setIcon(new ImageIcon("images\\mine.png"));
                                }
                                tile.setBackground(Color.RED);
                                gameOverLose();
                            }
                            else if (tile.getMineCounts() == 0) {
                                findIsland(tile, finalI, finalJ);
                            }
                            else {
                                tile.setEnabled(false);
                                tile.setBackground(Color.WHITE);
                                tile.setText(Integer.toString(tile.getMineCounts()));
                                tile.marked();
                                clearedTiles++;
                            }
                        }
                        boolean isVictory = true;
                        loop:
                        for (var tile1 : tiles) {
                            for (var tile : tile1) {
                                if(tile.isEnabled() && !tile.isMine()) {
                                    isVictory = false;
                                    break loop;
                                }
                            }
                        }
                        if(isVictory) gameOverVictory();
//                        if(clearedTiles == tilesColumnQuantity * tilesRowQuantity - minesQuantity) gameOverVictory();
//                        System.out.println(clearedTiles);
                    }
                });
            }
        }
    }

    private void findIsland(Tile tile, int row, int column) {
        tile.setBackground(Color.WHITE);
        tile.setText("");
        tile.setEnabled(false);
        tile.marked();
        clearedTiles++;
        if (row - 1 != -1) {
            setTileBlankOrNum(row - 1, column);
            if (column - 1 != -1) {
                setTileBlankOrNum(row - 1, column - 1);
            }
            if (column + 1 != tilesColumnQuantity) {
                setTileBlankOrNum(row - 1, column + 1);
            }
        }
        if (row + 1 != tilesRowQuantity){
            setTileBlankOrNum(row + 1, column);
            if (column - 1 != -1) {
                setTileBlankOrNum(row + 1, column - 1);
            }
            if (column + 1 != tilesColumnQuantity) {
                setTileBlankOrNum(row + 1, column + 1);
            }
        }
        if (column - 1 != -1) {
            setTileBlankOrNum(row, column - 1);
        }
        if (column + 1 != tilesColumnQuantity) {
            setTileBlankOrNum(row, column + 1);
        }
    }
    private void setTileBlankOrNum(int row, int column) {
        if (tiles[row][column].isMarked()) return;
        if (tiles[row][column].getMineCounts() == 0) {
            findIsland(tiles[row][column], row, column);
        }
        else {
            clearedTiles++;
            tiles[row][column].marked();
            tiles[row][column].setText(Integer.toString(tiles[row][column].getMineCounts()));
            tiles[row][column].setEnabled(false);
            tiles[row][column].setBackground(Color.WHITE);
        }
    }

//    private void setCountsIsland(int row, int column, boolean vertical) {
//        if(!tiles[row][column].isMarked()) {
//            tiles[row][column].setText(Integer.toString(tiles[row][column].getMineCounts()));
//            tiles[row][column].setEnabled(false);
//            tiles[row][column].setBackground(Color.WHITE);
//        }
//        if (vertical) {
//            if(row - 1 != -1 && !tiles[row - 1][column].isMarked()){
//                tiles[row - 1][column].setText(Integer.toString(tiles[row - 1][column].getMineCounts()));
//                tiles[row - 1][column].setEnabled(false);
//                tiles[row - 1][column].setBackground(Color.WHITE);
//            }
//            if(row + 1 != tilesRowQuantity && !tiles[row + 1][column].isMarked()) {
//                tiles[row + 1][column].setText(Integer.toString(tiles[row + 1][column].getMineCounts()));
//                tiles[row + 1][column].setEnabled(false);
//                tiles[row + 1][column].setBackground(Color.WHITE);
//            }
//        }
//        else {
//            if(column - 1 != -1 && !tiles[row][column - 1].isMarked()) {
//                tiles[row][column - 1].setText(Integer.toString(tiles[row][column - 1].getMineCounts()));
//                tiles[row][column - 1].setEnabled(false);
//                tiles[row][column - 1].setBackground(Color.WHITE);
//            }
//            if(column + 1 != tilesColumnQuantity && !tiles[row][column + 1].isMarked()) {
//                tiles[row][column + 1].setText(Integer.toString(tiles[row][column + 1].getMineCounts()));
//                tiles[row][column + 1].setEnabled(false);
//                tiles[row][column + 1].setBackground(Color.WHITE);
//            }
//        }
//    }

    private void gameOverLose() {
        int option = JOptionPane.showOptionDialog(this,
                "You lose!",
                "End game", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("images\\mine.png"),
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

    private void gameOverVictory() {
        for(var coords : mines) {
            tiles[coords.getRow()][coords.getColumn()].setIcon(new ImageIcon("images\\flag.png"));
        }
        int option = JOptionPane.showOptionDialog(this,
                "You won!",
                "End game", JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                new ImageIcon("images\\mine.png"),
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
//        tiles = new Tile[tilesRowQuantity][tilesColumnQuantity];
//        random = new Random();
        mines.clear();
        while(mines.size() != minesQuantity) {
            mines.add(new Coords(random.nextInt(tilesRowQuantity), random.nextInt(tilesColumnQuantity)));
        }


//        setLayout(new GridLayout(tilesRowQuantity, tilesColumnQuantity));
        for (int i = 0; i < tilesRowQuantity; i++) {
            for (int j = 0; j < tilesColumnQuantity; j++) {
                var tile = tiles[i][j];
                tile.resetTile();

                for (Coords coordsMine : mines) {
                    if (coordsMine.equals(new Coords(i, j))) {
                        tile.setMine(true);
//                        tile.setIcon(new ImageIcon("images\\mine.png"));
                    }
                }
//                tiles[i][j] = tile;
//                add(tile);
            }
        }

        for (int i = 0; i < tilesRowQuantity; i++) {
            for (int j = 0; j < tilesColumnQuantity; j++) {
                var tile = tiles[i][j];
                if(tile.isMine()) {
                    for (int iCounts = i - 1; iCounts <= i + 1; iCounts++) {
                        if(iCounts < 0 || iCounts >= tilesRowQuantity) continue;
                        for (int jCounts = j - 1; jCounts <= j + 1; jCounts++) {
                            if(jCounts < 0 || jCounts >= tilesColumnQuantity) continue;
                            if (!tiles[iCounts][jCounts].isMine()) {
                                tiles[iCounts][jCounts].incrMineCounts();
                            }
                        }
                    }
                }

                int finalI = i;
                int finalJ = j;
                tile.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if(tile.isFlagged()) {
                            tile.setIcon(null);
                            tile.setFlagged(false);
                        }
                        else if (e.getButton() == MouseEvent.BUTTON3) {
                            tile.setIcon(new ImageIcon("images\\flag.png"));
                            tile.setFlagged(true);
                        }
                        else {
                            if (tile.isMine()) {
                                for(var coords : mines) {
                                    tiles[coords.getRow()][coords.getColumn()].setBackground(Color.WHITE);
                                    tiles[coords.getRow()][coords.getColumn()].setIcon(new ImageIcon("images\\mine.png"));
                                }
                                tile.setBackground(Color.RED);
                                gameOverLose();
                            }
                            else if (tile.getMineCounts() == 0) {
                                findIsland(tile, finalI, finalJ);
                            }
                            else {
                                tile.setEnabled(false);
                                tile.setBackground(Color.WHITE);
                                tile.setText(Integer.toString(tile.getMineCounts()));
                                tile.marked();
                                clearedTiles++;
                            }
                        }
                        boolean isVictory = true;
                        loop:
                        for (var tile1 : tiles) {
                            for (var tile : tile1) {
                                if(tile.isEnabled() && !tile.isMine()) {
                                    isVictory = false;
                                    break loop;
                                }
                            }
                        }
                        if(isVictory) gameOverVictory();
//                        if(clearedTiles == tilesColumnQuantity * tilesRowQuantity - minesQuantity) gameOverVictory();
//                        System.out.println(clearedTiles);
                    }
                });
            }
        }
    }
}
