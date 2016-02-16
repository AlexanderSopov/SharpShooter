package se.swedishcoffee.game.model;

import com.badlogic.gdx.math.Vector2;

import se.swedishcoffee.game.control.Controller;

/**
 * Created by Alex on 16-02-16.
 */
public class Player extends Entity {
    private Controller controller;

    //Constants
    private static final float WALKSPEED = 10;
    private static final float POWERSPEED = 3;


    //flags
    private float powerSpeed = 1;


    public Player(){
        this(0, 0, 10, 10);
    }

    public Player(float x, float y, float width, float height){
        this(new Vector2(x, y), new Vector2(width, height));
    }

    public Player(Vector2 position, Vector2 size){
        super(position, size);
        controller = new Controller(this);


    }




    @Override
    public void moveLeft() {
        velocity.x= -WALKSPEED*powerSpeed;
    }

    @Override
    public void moveRight() {

    }

    @Override
    public void jump() {

    }

    @Override
    public void duck() {

    }

    @Override
    public void attack(Vector2 direction) {

    }

    @Override
    public void defensiveMove() {

    }

    @Override
    public void powerSpeed(boolean on) {
        if (on)
            powerSpeed=POWERSPEED;
        else
            powerSpeed=1;
    }
}
