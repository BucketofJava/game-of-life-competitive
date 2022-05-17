import processing.core.PApplet;

import javax.annotation.processing.ProcessingEnvironment;
import java.util.Arrays;

public class GameOfLifeCompetitive extends PApplet {
    public static int cellSideLength=10;
    public static int canvasHeight=600;
    public static int windowHeight=700;
    public static int canvasWidth=1300;
    public static int redMaxPlaceable=30;
    public static int blueMaxPlaceable=30;
    public static GameOfLifeCompetitive instance;
    public static int[][] allPossiblePositions;
    public static GameBoard board;
    public static int[] currentColor;
    public static boolean ranOnce=false;
    public static int drawcount=0;
    public static boolean gameStart=true;
    public static int repeatPeriod=15;
    public static boolean isRunning=false;
    public GameOfLifeCompetitive(){
        instance=this;
    }
    public void settings(){
        size(canvasWidth, windowHeight);
    }
    public void mouseClicked(){
        if(mouseY>canvasHeight){
            if(currentColor[2]==0){
                currentColor=new int[]{0, 0, 100};
            }else{
                currentColor=new int[]{100, 0, 0};
            }
            return;
        }
    int[] position=new int[]{(((int)mouseX)/cellSideLength)*cellSideLength, (((int)mouseY)/cellSideLength)*cellSideLength};
        if(currentColor[2]==0){
            if(redMaxPlaceable<=0){return;}
            redMaxPlaceable-=1;
        }else{
        if(blueMaxPlaceable<=0) return;
        blueMaxPlaceable-=1;}

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
        if((key=='t' || key=='T')&&gameStart){
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
        fill(currentColor[0], currentColor[1], currentColor[2]);
        rect(0, canvasHeight, canvasWidth, windowHeight-canvasHeight);
        fill(255);
        text("Current Speed: 1 round/"+String.valueOf(Math.max(repeatPeriod,1)/60.0)+" seconds",10, canvasHeight+10);
        text("Change Color: C; Start: T; Increase/Decrease Speed: A/D; Trigger one Round: B;", 10, canvasHeight+20);
        text("Red Remaining Placeable Cells: "+redMaxPlaceable+"; Blue Remaining Placeable Cells: "+blueMaxPlaceable, 10, canvasHeight+30);
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
            drawcount=((drawcount+1)%Math.max(repeatPeriod, 1));
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
