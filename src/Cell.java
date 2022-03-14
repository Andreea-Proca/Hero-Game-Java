public class Cell{
    int x;
    int y;
    Enum type;
    CellElement elem;
    boolean visited;

    public Cell(Enum type, int x, int y){
        this.x = x;
        this.y = y;
        this.type = type;
        visited = false;
    }
    public Enum getType() {return type;}
    public void setType(Enum type) {this.type = type;}
    public boolean isVisited() {return visited;}
    public void setVisited(boolean visited) {this.visited = visited;}
    public CellElement getElem() {return elem;}
    public void setElem(CellElement elem) {this.elem = elem;}
}