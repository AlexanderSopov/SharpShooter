package se.swedishcoffee.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

import se.swedishcoffee.game.model.Player;

/**
 * Created by Alex on 16-02-16.
 */
public class GameScreen implements Screen {

    private static final float WORLD_SIZE = 100f;
    ShapeRenderer renderer;
    ExtendViewport viewport;
    Player player;



    @Override
    public void show () {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        player = new Player();
        viewport = new ExtendViewport(WORLD_SIZE, WORLD_SIZE);
    }

    @Override
    public void render(float delta){
        viewport.apply(true);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.setProjectionMatrix(viewport.getCamera().combined);
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        player.render(delta, renderer);
        renderer.end();
    }

    @Override
    public void resize (int width, int height) {
        // TODO: Update the viewport
        viewport.update(width, height, true);

        // TODO: Move the viewport's camera to the center of the face
        viewport.getCamera().position.set(WORLD_SIZE/2, WORLD_SIZE/2, 0);
    }


    @Override
    public void hide () {
    }

    @Override
    public void pause () {
    }

    @Override
    public void resume () {
    }

    @Override
    public void dispose () {
        renderer.dispose();
    }

}
