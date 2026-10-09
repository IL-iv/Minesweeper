package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.some_example_name.GameplayScreen;

import javax.swing.plaf.PanelUI;
import java.security.PublicKey;
import java.util.ArrayList;

public class GameBoard {

    private int[][] board;
    private int numBombs;
    private int numFlags;
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


    public GameBoard(GameplayScreen gameplayScreen){
        this.gameplayScreen = gameplayScreen;
        board = new int[10][10];
        numBombs = 10;
        numFlags = numBombs;
        loadGraphics();
        testBoard();
    }

    public GameBoard(GameplayScreen gameplayScreen, int row, int col, int numBombs) {
        this.board = new int[row][col];
        this.numBombs = numBombs;
        this.numFlags = numBombs;
        this.gameplayScreen = gameplayScreen;
        loadGraphics();
        testBoard();
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


    public void draw(SpriteBatch spriteBatch){

        int yLen = board[0].length * tileSize;
        int xLen = board.length * tileSize;







        for(int row = 0; row < board.length; row++){
            for(int col = 0; col < board[0].length; col++){

                int xLoc = xOffset + (tileSize * col);
                int yLoc = yOffset + (tileSize * row);
                int value = board[row][col];
                placeAllBombs();
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
                } else if (value == 18) {
                    spriteBatch.draw(eightTile, xLoc, yLoc);
                } else if (value >= 19) {
                    spriteBatch.draw(flagTile, xLoc, yLoc);
                }

                //temp bomb show
                if(value == -1) spriteBatch.draw(bombTile, xLoc, yLoc);


            }
        }
    }

    private boolean isValid(Location loc){
        return (loc.getRow() < board.length) && (loc.getRow() > -1) && (loc.getColumn() < board[0].length) && (loc.getColumn() > -1);
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


    public void placeAllBombs(){

        for(int i =0; i < numBombs; i++) {
            boolean unique = false;

            while(!unique){
                int randRow = (int) (Math.random() * (board.length));
                int randCol = (int) (Math.random() * (board[0].length));

                unique = board[randRow][randCol] == -1;
                board[randRow][randCol] = -1;
            }
        }





    }



}
