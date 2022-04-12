import processing.core.PApplet;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.Arrays;

public class GameOfLifeCompetitive extends PApplet {
    public static int cellSideLength=20;
    public static int canvasHeight=700;
    public static int canvasWidth=1300;
    public static GameOfLifeCompetitive instance;
    public static int[][] allPossiblePositions;
    public static GameBoard board;
    public static int[] currentColor;
    public static boolean ranOnce=false;
    public GameOfLifeCompetitive(){
        instance=this;
    }
    public void settings(){
        size(canvasWidth, canvasHeight);
    }
    public void mouseClicked(){
    int[] position=new int[]{(((int)mouseX)/20)*20, (((int)mouseY)/20)*20};
    board.spawnCell(position, new Cell(position, currentColor));
    }
    public void keyPressed(){
        if (key == 'b' || key == 'B') {
            if(!ranOnce) board.timeStep();

        }
        if(key == 'c' || key == 'C'){
            if(currentColor[2]==0){
            currentColor=new int[]{0, 0, 100};
            }else{
                currentColor=new int[]{100, 0, 0};
            }
        }
    }
    public void draw(){

        background(255);
        //System.out.println(board.allCells.keySet());
        for (int[] position:allPossiblePositions){
//            if(position[0]==0&&position[1]==0){
//                System.out.println(board.allCells.get(position));
//            }
            if(board.allCells.get(GameBoard.convertCoordinateForm(position))!=null){
             //   System.out.println(Arrays.toString(position));
                int[] color=board.allCells.get(GameBoard.convertCoordinateForm(position)).getColor();
                fill(color[0], color[1] ,color[2]);
            }

            rect(position[0], position[1], 20, 20);
            fill(255);
        }

    }
    public static void main(String[] args){
         currentColor=new int[]{100, 0, 0};
         allPossiblePositions=new int[(canvasHeight/20)*(canvasWidth/20)][2];
         for (int x=0; x<canvasWidth/20; x++){
             for(int y=0; y<canvasHeight/20; y++){
                 allPossiblePositions[(x*(canvasHeight/20))+y]= new int[]{x*20, y*20};
             }
         }
         board=new GameBoard();

         PApplet.main("GameOfLifeCompetitive");

    }
}
