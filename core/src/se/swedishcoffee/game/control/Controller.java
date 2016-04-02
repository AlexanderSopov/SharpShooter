package se.swedishcoffee.game.control;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;

import se.swedishcoffee.game.model.AimCross;
import se.swedishcoffee.game.model.Player;

/**
 * Created by Alex on 16-02-16.
 */
public class Controller extends InputAdapter
{
    private Player player;
    private AimCross aimCross;

    public Controller(Player player,AimCross aimCross){
        this.player=player;
        this.aimCross = aimCross;
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
                break;
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (Input.Buttons.LEFT == button){
            player.attack(new Vector3(screenX, screenY,0), true);
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        aimCross.move(screenX,screenY);
        return false;
    }

}
