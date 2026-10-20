package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.GameplayScreen;

import javax.swing.plaf.PanelUI;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;

public class GameBoard {

    private int[][] board;
    private int numBombs;
    private int numFlags;
    private int flagCount;
    private static final int BOMB = -1;
    private GameplayScreen gameplayScreen;
    private Texture emptyTile;
    private Texture emptyFloorTile;
    private Texture oneTile, twoTile, threeTile, fourTile, fiveTile, sixTile, sevenTile, eightTile;
    private Texture bombTile;
    private Texture flagTile;

    private int tileSize = 25;
    private int xOffset = 50;
    private int yOffset = 120;

    private int YSCREEN = 720;
    private int XSCREEN = 1280;
    private boolean firstClick;


    public GameBoard(GameplayScreen gameplayScreen){
        this.gameplayScreen = gameplayScreen;
        board = new int[17][17];
        numBombs = 43;
        numFlags = numBombs;
        flagCount = 0;
        loadGraphics();
        firstClick = true;

    }

    public GameBoard(GameplayScreen gameplayScreen, int row, int col, int numBombs, int numFlags) {
        this.board = new int[row][col];
        this.numBombs = numBombs;
        this.numFlags = numFlags;
        this.flagCount = 0;
        this.gameplayScreen = gameplayScreen;
        loadGraphics();
        firstClick = true;

    }

    public void loadGraphics(){
        emptyTile = new Texture("emptyTile.jpg");
        emptyFloorTile = new Texture("empty floor.jpg");

        oneTile = new Texture("oneTile.jpg");
        twoTile = new Texture("twoTile.jpg");
        threeTile = new Texture("threeTile.jpg");
        fourTile = new Texture("fourTile.jpg");
        fiveTile = new Texture("fiveTile.jpg");
        sixTile = new Texture("sixTile.jpg");
        sevenTile = new Texture("sevenTile.jpg");
        eightTile = new Texture("eightTile.jpg");

        bombTile = new Texture("bomb.jpg");
        flagTile = new Texture("flagTile.jpg");



    }

    private void testBoard(){
        board[0][0] = 11;
        board[0][1] = 12;
        board[0][2] = 13;
        board[0][3] = 14;
        board[0][4] = 15;
        board[0][5] = 16;
        board[0][6] = 17;
        board[0][7] = 18;

        board[1][2] = 9;
        board[2][0] = 21;
        board[3][0] = 20;

    }


    public Location getTileAt(int mouseX, int mouseY){

        //all tiles are 25 X 25 pixles
        //(xOffset, yOffset) is origin

        int row = 18 - (((mouseY - yOffset) / tileSize));  //idk why but the rows are off by 2 so uh- that's getting hardcoded
        int col = ((mouseX - xOffset) / tileSize);
        return new Location(row, col);
    }

    public void draw(SpriteBatch spriteBatch){

        int yLen = board[0].length * tileSize;
        int xLen = board.length * tileSize;







        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){

                //(xOffset, yOffset) is origin for the board

                int xLoc = xOffset + (tileSize * col);
                int yLoc = yOffset + (tileSize * row);
                int value = board[row][col];

                if(value <= 8){
                    spriteBatch.draw(emptyTile, xLoc, yLoc);
                } else if (value == 9) {
                    spriteBatch.draw(bombTile, xLoc, yLoc);
                } else if (value == 10) {
                    spriteBatch.draw(emptyFloorTile, xLoc, yLoc);
                } else if (value == 11) {
                    spriteBatch.draw(oneTile, xLoc, yLoc);
                } else if (value == 12) {
                    spriteBatch.draw(twoTile, xLoc, yLoc);
                } else if (value == 13) {
                    spriteBatch.draw(threeTile, xLoc, yLoc);
                } else if (value == 14) {
                    spriteBatch.draw(fourTile, xLoc, yLoc);
                } else if (value == 15) {
                    spriteBatch.draw(fiveTile, xLoc, yLoc);
                } else if (value == 16) {
                    spriteBatch.draw(sixTile, xLoc, yLoc);
                } else if (value == 17) {
                    spriteBatch.draw(sevenTile, xLoc, yLoc);
                } else if (value == 18) {
                    spriteBatch.draw(eightTile, xLoc, yLoc);
                } else if (value >= 19) {
                    spriteBatch.draw(flagTile, xLoc, yLoc);
                }

            }
        }
    }

    public void uncoverTile(Location loc){
        int tileVal = board[loc.getRow()][loc.getColumn()];

        if(firstClick) loadBoard(loc);
        else if(isValid(loc) && tileVal >= -1 && tileVal <= 8) {
            if(tileVal == 0) floodFillBoard(loc);
            else board[loc.getRow()][loc.getColumn()] += 10;
        }

    }

    private void loadBoard(Location loc){
        int inRow = loc.getRow(), inCol = loc.getColumn();
        Location[] startStar = new Location[5];

        Location locRowUp = new Location(inRow+1, inCol), locRowDown = new Location(inRow-1, inCol);
        Location locColUp = new Location(inRow, inCol+1), locColDown = new Location(inRow, inCol-1);

        if(isValid(locRowUp)) startStar[0] = locRowUp;
        if(isValid(locRowDown)) startStar[1] = locRowDown;
        if(isValid(locColUp)) startStar[2] = locColUp;
        if(isValid(locColDown)) startStar[3] = locColDown;
        startStar[4] = loc;

        placeAllBombs(startStar);
        numberBoard();

        firstClick = false;

    }

    private void floodFillBoard(Location loc){
        int inRow = loc.getRow(), inCol = loc.getColumn();

        Location locRowUp = new Location(inRow+1, inCol), locRowDown = new Location(inRow-1, inCol);
        Location locColUp = new Location(inRow, inCol+1), locColDown = new Location(inRow, inCol-1);

        if(board[inRow][inCol] == 0){
            board[inRow][inCol] += 10;
            if(isValid(locRowUp)) floodFillBoard(locRowUp);
            if(isValid(locRowDown)) floodFillBoard(locRowDown);
            if(isValid(locColUp)) floodFillBoard(locColUp);
            if(isValid(locColDown)) floodFillBoard(locColDown);
        }
        else if(board[inRow][inCol] <= 8 ) {
            board[inRow][inCol] += 10;
            if(isValid(locRowUp) && board[inRow+1][inCol] <= 8 && board[inRow+1][inCol] > 0) board[inRow+1][inCol] += 10;
            if(isValid(locRowDown) && board[inRow-1][inCol] <= 8 && board[inRow-1][inCol] > 0) board[inRow-1][inCol] += 10;
            if(isValid(locColUp) && board[inRow][inCol+1] <= 8 && board[inRow][inCol+1] > 0) board[inRow][inCol+1] += 10;
            if(isValid(locColDown) && board[inRow][inCol-1] <= 8 && board[inRow][inCol-1] > 0) board[inRow][inCol-1] += 10;
        }
        return;
    }

    public void changeFlagStatus(Location loc){
        if(isValid(loc)){
            int tileVal = board[loc.getRow()][loc.getColumn()];
            if(tileVal >= 19) {
                board[loc.getRow()][loc.getColumn()] -= 20;
                flagCount--;
            }else if (tileVal <= 8 && flagCount < numFlags){
                board[loc.getRow()][loc.getColumn()] += 20;
                flagCount++;
            }

        }

    }



    //bombs
    private void numberBoard(){

        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){
                ArrayList<Location> spots = getNeighbors(new Location(row, col));

                if(board[row][col] != -1){
                    for(int i = 0 ; i < spots.size(); i++){
                        Location loc = spots.get(i);

                        if(board[loc.getRow()][loc.getColumn()] == -1){
                            board[row][col]++;


                        }


                    }



                }


            }
        }



    }


    private boolean isValid(Location loc){
        return  (loc.getRow() > -1) && (loc.getRow() < board.length) && (loc.getColumn() > -1) && (loc.getColumn() < board[0].length);
    }

    private ArrayList<Location> getNeighbors(Location loc){
        ArrayList<Location> rtnList = new ArrayList<>();

        for(int i = loc.getRow()-1; i < loc.getRow() +2; i++){
            for(int j = loc.getColumn() -1; j < loc.getColumn() +2; j++) {
                Location nLoc = new Location(i, j);
                if (isValid(nLoc)) rtnList.add(nLoc);
            }
        }

        return rtnList;
    }


    public void placeAllBombs(Location[] locs){

        for(int i =0; i < numBombs; i++) {
            boolean unique = false;

            while(!unique){
                int randRow = (int) (Math.random() * (board.length));
                int randCol = (int) (Math.random() * (board[0].length));

                unique = (board[randRow][randCol] != -1) && !(Arrays.asList(locs).contains(new Location(randRow, randCol)));
                if(unique) board[randRow][randCol] = -1;
                if(unique) System.out.println("should work lol" + Arrays.stream(locs).noneMatch(l -> l.equals(new Location(randRow, randCol))));
            }
        }





    }



}
