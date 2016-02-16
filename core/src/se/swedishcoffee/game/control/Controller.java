package se.swedishcoffee.game.control;

import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.InputAdapter;

import se.swedishcoffee.game.model.Player;

/**
 * Created by Alex on 16-02-16.
 */
public class Controller extends InputAdapter
{
    private Player player;

    public Controller(Player player){
        this.player=player;
    }

    @Override
    public boolean keyDown (int keycode) {
        switch (keycode){
            case Keys.A:
                player.moveLeft();
                break;
            case Keys.D:
                player.moveLeft();
                break;
            case Keys.W:
                player.jump();
                break;
            case Keys.S:
                player.duck();

        }

        return true;
    }

    @Override
    public boolean keyUp (int keycode) {
        return true;
    }

}
