import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Grid extends ArrayList<ArrayList<Cell>>{
    int length;
    int width;
    Character character;
    Cell current_cell;
    Cell previous_cell;

    private Grid(int length, int width) {
        this.length = length;
        this.width = width;
    }
    public void setCharacter(Character character) {
        this.character = character;
    }
    public void setCurrentCell(Cell current_cell) {
        this.current_cell = current_cell;
    }
    public void setPreviousCell(Cell current_cell) {
        this.previous_cell = current_cell;
    }

    static Grid generateMap(int length, int width) {
        Grid map = new Grid(length, width);
        Random random = new Random();
        int rand_Shops = random.nextInt(7 - 2) + 2;
        int rand_Enemies = random.nextInt(9 - 4) + 4;

        for(int i = 0; i < width; i++) {
            ArrayList<Cell> cells = new ArrayList<>();
            map.add(i, cells);
            for (int j = 0; j < length; j++) {
                Cell cell = new Cell(Enum.EMPTY, i, j);
                map.current_cell = cell;
                map.get(i).add(j, cell);
                Empty empty = new Empty();
                map.current_cell.setElem(empty);
                cells.add(cell);
            }
        }

        for(int i = 0; i < rand_Shops; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(length);
            map.current_cell = map.get(x).get(y);
            if (map.current_cell.type == Enum.EMPTY && (x != 0 || y != 0)) {
                map.current_cell.setType(Enum.SHOP);
                map.current_cell.setElem(new Shop());
            }
            else
                i--;
        }
        for(int i = 0; i < rand_Enemies; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(length);
            map.current_cell = map.get(x).get(y);
            if (map.current_cell.type == Enum.EMPTY && (x != 0 || y != 0)) {
                map.current_cell.setType(Enum.ENEMY);
                map.current_cell.setElem(new Enemy());
            }
            else
                i--;
        }
        int x = random.nextInt(width);
        int y = random.nextInt(length);
        while(map.current_cell.type != Enum.EMPTY && (x != 0 || y != 0)){
            x = random.nextInt(width);
            y = random.nextInt(length);
            map.current_cell = map.get(x).get(y);
        }
        map.current_cell.setType(Enum.FINISH);
        map.current_cell.setElem(new Finish());

        map.setCurrentCell(map.get(0).get(0));
        map.setPreviousCell(map.current_cell);
        map.current_cell.setVisited(true);
        return map;
    }

    void goNorth(){
        if( current_cell.x > 0) {
            setPreviousCell(current_cell);
            setCurrentCell(get(current_cell.x - 1).get(current_cell.y));
        }
        else {
            setPreviousCell(current_cell);
            System.out.println("Invalid move");
        }
    }
    void goSouth(){
        if( current_cell.x < length - 1) {
            setPreviousCell(current_cell);
            setCurrentCell(get(current_cell.x + 1).get(current_cell.y));
        }
        else {
            setPreviousCell(current_cell);
            System.out.println("Invalid move");
        }
    }
    void goWest(){
        if( current_cell.y > 0) {
            setPreviousCell(current_cell);
            setCurrentCell(get(current_cell.x).get(current_cell.y - 1));
        }
        else {
            setPreviousCell(current_cell);
            System.out.println("Invalid move");
        }
    }
    void goEast(){
        if( current_cell.y < width - 1) {
            setPreviousCell(current_cell);
            setCurrentCell(get(current_cell.x).get(current_cell.y + 1));
        }
        else {
            setPreviousCell(current_cell);
            System.out.println("Invalid move");
        }
    }

    void printGrid(){
        for(int i = 0; i < width; i++) {
            String s = "";
            for (int j = 0; j < length; j++) {
                if(get(i).get(j).isVisited() && get(i).get(j) != current_cell)
                    s += " " + get(i).get(j).getElem().toCharacter() + " ";
                else {
                    if (get(i).get(j) == current_cell)
                        s += " P ";
                    else
                        s += " ? ";
                }
            }
            System.out.println(s);
        }
    }

    JSplitPane printGridGUI(JFrame f, JButton north, JButton south, JButton east, JButton west, JPanel text){
        JPanel nsew = new JPanel(new BorderLayout());
        final JPanel gui = new JPanel(new BorderLayout());
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT, gui, nsew);
        f.add(sp);

        JButton[][] gameBoardSquares = new JButton[length][width];
        JPanel gameBoard;

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);

        gameBoard = new JPanel(new GridLayout(0, width));
        gameBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(gameBoard);

        // create the game board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < width; j++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                b.setIcon(getImage(i, j));
                if ((j % 2 == 1 && i % 2 == 1) || (j % 2 == 0 && i % 2 == 0)) {
                    b.setBackground(new Color(102,255,102));
                } else {
                    b.setBackground(new Color(0,153,0));
                }
                gameBoardSquares[j][i] = b;
                gameBoard.add(b);
            }
        }

        nsew.add(north, BorderLayout.NORTH);
        nsew.add(south, BorderLayout.SOUTH);
        nsew.add(east, BorderLayout.EAST);
        nsew.add(west, BorderLayout.WEST);
        nsew.add(text, BorderLayout.CENTER);
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
        return sp;
    }

    public ImageIcon getImage(int i, int j){
        String filename = "";
        if(get(i).get(j).isVisited() && get(i).get(j) != current_cell){
            if( get(i).get(j).getElem().toCharacter() == 'S')
                filename = "images/shop1.png";
            if( get(i).get(j).getElem().toCharacter() == 'E')
                filename = "images/enemy2.png";
            if( get(i).get(j).getElem().toCharacter() == '-')
                filename = "images/empty.png";
        }
        else {
            if (get(i).get(j) == current_cell)
                filename = "images/character1.png";
            else
                filename = "images/questionmark.png";
        }
        ImageIcon img = new ImageIcon(filename);
        return img;
    }
}