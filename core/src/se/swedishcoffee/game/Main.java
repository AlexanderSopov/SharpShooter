package se.swedishcoffee.game;

import com.badlogic.gdx.Gdx;

import se.swedishcoffee.game.view.GameScreen;

public class Main extends com.badlogic.gdx.Game {
	
	@Override
	public void create () {
		Gdx.graphics.setWindowedMode(1000, 700);
		setScreen(new GameScreen());
	}

}
