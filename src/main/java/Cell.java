import java.util.UUID;

public class Cell {
    protected String id;
    protected int[] position;
    protected int[] color;

    public Cell(int[] position, int[] color) {
        this.id = UUID.randomUUID().toString();
        this.position = position;
        this.color = color;
    }

    public boolean checkId(String cellId){
        return cellId.equals(id);
    }
    public String getId(){
        return id;
    }

    public int[] getPosition() {
        return position;
    }

    public int[] getColor() {
        return color;
    }
}
