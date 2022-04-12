import java.util.ArrayList;
import java.util.HashMap;

public class GameBoard {
    HashMap<String, Cell> allCells=new HashMap<>();
    public GameBoard(){
        int[] position=new int[]{0, 0};
        spawnCell(position, new Cell(position, new int[]{100, 0, 0}));
    }
    public void spawnCell(int[] position, Cell cell){
        allCells.put(convertCoordinateForm(position), cell);
    }
    public void killCell(int[] position){
        allCells.remove(convertCoordinateForm(position));
    }
    public static String convertCoordinateForm(int[] position){
        return String.valueOf(position[0])+","+String.valueOf(position[1]);
    }
    public static int[] convertCoordinateForm(String position){
        return new int[]{Integer.parseInt(position.split(",")[0]), Integer.parseInt(position.split(",")[1])};
    }
    public ArrayList<Cell> getAdjacentCells(int[] cellPosition){
        ArrayList<Cell> adjacentCells=new ArrayList<>();
        for(String positionString:allCells.keySet()){
            int[] position=convertCoordinateForm(positionString);
            if(positionString.equals(convertCoordinateForm(cellPosition))){
                continue;
            }
            //if(Math.pow((double) ((position[0]-cellPosition[0])*(position[0]-cellPosition[0]))+((position[1]-cellPosition[1])*(position[1]-cellPosition[1])), (1/2.0))<=GameOfLifeCompetitive.cellSideLength){
            if(Math.abs((position[0]-cellPosition[0]))<=GameOfLifeCompetitive.cellSideLength&&Math.abs((position[1]-cellPosition[1]))<=GameOfLifeCompetitive.cellSideLength){
                adjacentCells.add(allCells.get(positionString));
            }
        }

        return adjacentCells;
    }
    public void timeStep(){
    ArrayList<int[]> redToSpawn=new ArrayList<>();
    ArrayList<int[]> blueToSpawn=new ArrayList<>();
    ArrayList<int[]> toKill=new ArrayList<>();
    for (int[] position:GameOfLifeCompetitive.allPossiblePositions){
        int redCellNum=0;
        int blueCellNum=0;
        for (Cell cell:getAdjacentCells(position)){
            if(cell.getColor()[2]==0){
                redCellNum+=1;
                continue;
            }
            blueCellNum+=1;
        }

        if(redCellNum>=4||blueCellNum>=4){
        toKill.add(position);
        continue;
        }
        boolean cellExists=(allCells.get(convertCoordinateForm(position))!=null);
        if(redCellNum+blueCellNum<=1){


            toKill.add(position);
            continue;
        }

        if(redCellNum>=2&&redCellNum>blueCellNum&&cellExists){
            redToSpawn.add(position);
            continue;
        }
        if(blueCellNum>=2&&blueCellNum>redCellNum&&cellExists){
            blueToSpawn.add(position);
            continue;
        }
        if(blueCellNum==3&&blueCellNum>redCellNum){
            blueToSpawn.add(position);
            continue;
        }
        if(redCellNum==3&&blueCellNum<redCellNum){
            redToSpawn.add(position);

        }




    }
    for(int[] position:toKill){
        killCell(position);
    }
    for(int[] position:redToSpawn){
        spawnCell(position, new Cell(position, new int[]{100, 0, 0}));
    }
    for(int[] position:blueToSpawn){
        spawnCell(position, new Cell(position, new int[]{0, 0, 100}));
    }
    }
}
