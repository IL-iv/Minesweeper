package io.github.some_example_name;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Timer.Task;

import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.Color;
import io.github.some_example_name.GameBoard;


import java.util.SplittableRandom;
import java.util.Timer;


public class GameplayScreen implements Screen {

    private static final float WORLD_WIDTH = 1280;
    private static final float WORLD_HEIGHT = 720;

    //Object that allows us to draw all our graphics
    private SpriteBatch spriteBatch;

    //Object that allwos us to draw shapes
    private ShapeRenderer shapeRenderer;

    //Camera to view our virtual world
    private Camera camera;

    //control how the camera views the world
    //zoom in/out? Keep everything scaled?
    private Viewport viewport;

    //sounds
    private Sound dig;
    private Sound dig2;
    private Sound dig3;
    private Sound dig4;
    private Sound boom;
    private Sound boom2;
    private Sound boom3;
    private Sound boom4;

    private Sound flag;
    private Sound flag2;
    private Sound flag3;

    private Sound stone;
    private Sound stone2;
    private Sound stone3;
    private Sound stone4;

    private FreeTypeFontGenerator mainFont;
    private BitmapFont titleFont;
    private BitmapFont infoFont;

    //Game Variables
    GameBoard board;

    //runs one time, at the very beginning
    //all setup should happen here
    @Override
    public void show() {
        camera = new OrthographicCamera(); //2D camera
        camera.position.set(WORLD_WIDTH/2, WORLD_HEIGHT/2,0);//move the camera
        camera.update();

        //freeze my view to 800x600, no matter the window size
        viewport = new FitViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);

        spriteBatch = new SpriteBatch();

        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setAutoShapeType(true); //???, I just know that this was the solution to an annoying problem

        mainFont = new FreeTypeFontGenerator(Gdx.files.internal("GalaferaMedium-V4xze.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
            new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2f;
        infoFont = mainFont.generateFont(parameter);

        parameter.size = 48;
        parameter.borderWidth = 0;
        titleFont = mainFont.generateFont(parameter);

        board = new GameBoard(this);

        dig = Gdx.audio.newSound(Gdx.files.internal("Grass_dig1.wav"));
        dig2 = Gdx.audio.newSound(Gdx.files.internal("Grass_dig2.wav"));
        dig3 = Gdx.audio.newSound(Gdx.files.internal("Grass_dig3.wav"));
        dig4 = Gdx.audio.newSound(Gdx.files.internal("Grass_dig4.wav"));

        boom = Gdx.audio.newSound(Gdx.files.internal("Explosion1.wav"));
        boom2 = Gdx.audio.newSound(Gdx.files.internal("Explosion2.wav"));
        boom3 = Gdx.audio.newSound(Gdx.files.internal("Explosion3.wav"));
        boom4 = Gdx.audio.newSound(Gdx.files.internal("Explosion4.wav"));

        flag = Gdx.audio.newSound(Gdx.files.internal("Cloth_dig1.ogg.mp3"));
        flag2 = Gdx.audio.newSound(Gdx.files.internal("Cloth_dig2.ogg.mp3"));
        flag3 = Gdx.audio.newSound(Gdx.files.internal("Cloth_jump1.wav.mp3"));

        stone = Gdx.audio.newSound(Gdx.files.internal("Stone_hit1.ogg"));
        stone2 = Gdx.audio.newSound(Gdx.files.internal("Stone_hit5.ogg"));
        stone3 = Gdx.audio.newSound(Gdx.files.internal("Stone_dig4.ogg.mp3"));
        stone4 = Gdx.audio.newSound(Gdx.files.internal("Stone_mining5.ogg.mp3"));

    }

    public void clearScreen() {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }




    private void handleMouseClick(){
        if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
            //System.out.println("left click at (" + Gdx.input.getX() + ", " + Gdx.input.getY() + ")");
            //System.out.println(board.getTileAt(Gdx.input.getX(), Gdx.input.getY()));
            Location spot = board.getTileAt(Gdx.input.getX(), Gdx.input.getY());

            int pickSound = (int) (Math.random() * 4) + 1;

            //fix this, make a get valuse method
            if(board.board[spot.getRow()][spot.getColumn()] > -1 && board.board[spot.getRow()][spot.getColumn()] < 2){
                if(pickSound == 1) dig.play();
                else if(pickSound == 2) dig2.play();
                else if(pickSound == 3) dig3.play();
                else if(pickSound == 4) dig4.play();
                //maybe make higher value tiles make a stone / different sound
            }

            else if(board.board[spot.getRow()][spot.getColumn()] > 1 && board.board[spot.getRow()][spot.getColumn()] < 4){
                if(pickSound == 1) stone.play();
                else if(pickSound == 2) stone2.play();
                else if(pickSound == 3) stone3.play();
                else if(pickSound == 4) stone4.play();
                //maybe make higher value tiles make a stone / different sound
            }

            else if(board.board[spot.getRow()][spot.getColumn()] == -1){

                boom.play();

                com.badlogic.gdx.utils.Timer.instance().scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        boom2.play();
                    }
                }, 1.0f); //1.0 second delay

                com.badlogic.gdx.utils.Timer.instance().scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        boom4.play();
                    }
                }, 0.75f);

                com.badlogic.gdx.utils.Timer.instance().scheduleTask(new com.badlogic.gdx.utils.Timer.Task() {
                    @Override
                    public void run() {
                        boom3.play();




                    }
                }, 0.5f);
            }

            board.uncoverTile(spot);

        }
        if(Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)){
            int pickSound = (int) (Math.random() * 3) + 1;
            board.changeFlagStatus(board.getTileAt(Gdx.input.getX(), Gdx.input.getY()));
            if(pickSound == 1) flag.play();
            else if(pickSound == 2) flag2.play();
            else if(pickSound == 3) flag3.play();

        }


    }







    //this method runs as fast as it can, repeatedly, constantly looped
    @Override
    public void render(float delta) {
        clearScreen();

        //all user inputs

        handleMouseClick();




        //all drawing of shapes MUST be in between begin/end
        shapeRenderer.begin();


        shapeRenderer.end();

        //all drawing of graphics MUST be in between begin/end
        spriteBatch.begin();
        board.draw(spriteBatch);


        board.draw(spriteBatch);
        titleFont.draw(spriteBatch, "Minesweeper", 120, 610);
        infoFont.draw(spriteBatch, "Flags left: " + (board.numFlags-board.flagCount), 550, 550);

        if(!board.gameOn){


            infoFont.draw(spriteBatch, "Press on the board to replay!", 550, 500);

        }




        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
}
