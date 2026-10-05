package io.github.some_example_name;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mcelrea.GameplayScreen;

import javax.swing.plaf.PanelUI;
import java.security.PublicKey;

public class GameBoard {

    private int[][] board;
    private int numBombs;
    private int numFlags;
    private static final int BOMB = -1;
    private com.mcelrea.GameplayScreen gameplayScreen;
    private Texture emptyTile;
    private Texture emptyFloorTile;
    private Texture oneTile, twoTile, threeTile, fourTile, fiveTile, sixTile, sevenTile, eightTile;
    private Texture bombTile;
    private Texture flagTile;


    public GameBoard(com.mcelrea.GameplayScreen gameplayScreen){
        this.gameplayScreen = gameplayScreen;
        board = new int[16][30];
        numBombs = 50;
        numFlags = numBombs;

    }

    public GameBoard(GameplayScreen gameplayScreen, int row, int col, int numBombs) {
        this.board = new int[row][col];
        this.numBombs = numBombs;
        this.numFlags = numBombs;
        this.gameplayScreen = gameplayScreen;
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

    public void draw(SpriteBatch spriteBatch){
        spriteBatch.draw(emptyTile, 110, 50);

    }



}
