package se.swedishcoffee.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Main extends com.badlogic.gdx.Game {
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1000, 700);
		this.screen = new GameScreen();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void pause () {
	}

	@Override

	public void resume () {
	}

	@Override
	public void dispose () {
	}
}
