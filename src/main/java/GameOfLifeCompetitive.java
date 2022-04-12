import processing.core.PApplet;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.Arrays;

public class GameOfLifeCompetitive extends PApplet {
    public static int cellSideLength=10;
    public static int canvasHeight=700;
    public static int canvasWidth=1300;
    public static GameOfLifeCompetitive instance;
    public static int[][] allPossiblePositions;
    public static GameBoard board;
    public static int[] currentColor;
    public static boolean ranOnce=false;
    public static int drawcount=0;
    public static int repeatPeriod=15;
    public static boolean isRunning=false;
    public GameOfLifeCompetitive(){
        instance=this;
    }
    public void settings(){
        size(canvasWidth, canvasHeight);
    }
    public void mouseClicked(){
    int[] position=new int[]{(((int)mouseX)/cellSideLength)*cellSideLength, (((int)mouseY)/cellSideLength)*cellSideLength};
    board.spawnCell(position, new Cell(position, currentColor));
    }
    public void keyPressed(){
        if (key == 'b' || key == 'B') {
            if(!isRunning) board.timeStep();
        }
        if(key == 'c' || key == 'C'){
            if(currentColor[2]==0){
            currentColor=new int[]{0, 0, 100};
            }else{
                currentColor=new int[]{100, 0, 0};
            }
        }
        if(key=='t' || key=='T'){
            isRunning=!isRunning;
        }
        if(key=='a' || key=='A'){
            repeatPeriod-=1;
        }
        if(key=='d' || key=='D'){
            repeatPeriod+=1;
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

            rect(position[0], position[1], cellSideLength, cellSideLength);
            fill(255);
        }
        if(isRunning){
          //  System.out.println(drawcount);
            drawcount=((drawcount+1)%repeatPeriod);
            if(drawcount==0){
                board.timeStep();
            }
        }

    }
    public static void main(String[] args){
         currentColor=new int[]{100, 0, 0};
         allPossiblePositions=new int[(canvasHeight/cellSideLength)*(canvasWidth/cellSideLength)][2];
         for (int x=0; x<canvasWidth/cellSideLength; x++){
             for(int y=0; y<canvasHeight/cellSideLength; y++){
                 allPossiblePositions[(x*(canvasHeight/cellSideLength))+y]= new int[]{x*cellSideLength, y*cellSideLength};
             }
         }
         board=new GameBoard();

         PApplet.main("GameOfLifeCompetitive");

    }
}
