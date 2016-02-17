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
                player.moveLeft(true);
                break;
            case Keys.D:
                player.moveRight(true);
                break;
        }


        switch(keycode){
            case Keys.W:
                //System.out.println("got here");
                player.jump(true);
                break;
            case Keys.S:
                player.duck(true);
                break;
        }


        if (keycode == Keys.SPACE)
            player.powerSpeed(true);

        return true;
    }

    @Override
    public boolean keyUp (int keycode) {
        switch(keycode){
            case Keys.W:
                player.jump(false);
                break;
            case Keys.A:
                player.moveLeft(false);
                break;
            case Keys.D:
                player.moveRight(false);
                break;
            case Keys.S:
                player.duck(false);
                break;
            case Keys.SPACE:
                player.powerSpeed(false);
        }
        return true;
    }

}
