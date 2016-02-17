package se.swedishcoffee.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import se.swedishcoffee.game.control.Controller;

/**
 * Created by Alex on 16-02-16.
 */
public class Player extends Entity {
    private Controller controller;

    //Constants
    private static final float JUMPSPEED = 50;
    private static final float WALKSPEED = 30;
    private static final float POWERSPEED = 2.2f;


    //flags
    private float powerSpeed = 1;
    private float jumpingTimer = 0;
    private Jump jumping = Jump.GROUNDED;
    private boolean moveLeft = false;
    private boolean moveRight = false;


    public Player(){
        this(30, 10, 5, 10);
    }

    public Player(float x, float y, float width, float height){
        this(new Vector2(x, y), new Vector2(width, height));
    }

    public Player(Vector2 position, Vector2 size){
        super(position, size);
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
    }



    //update
    public void update(float delta){
        updateMotion(delta);
        Vector2 tempVel = new Vector2(velocity);
        position.add(tempVel.scl(delta));


    }

    private void updateMotion(float delta) {


        // Left/right movement
        if (moveLeft)
            velocity.x= -WALKSPEED*powerSpeed;
        else if (moveRight)
            velocity.x = WALKSPEED*powerSpeed;
        else
            velocity.x = 0;


        //jump mechanics
        if (jumping == Jump.JUMPING) {
            System.out.println("character is " + jumping);
            jumpingTimer += delta;
            velocity.y = jumpFunction();

            if (jumpingTimer > 0.5) {
                jumping = Jump.FALLING;
                System.out.println("Maxed out jumping, now " + jumping);
                jumpingTimer = 0;
            }
        }
        if (jumping == Jump.FALLING)
            temporaryFallingFunction();

        checkForCollisions();
    }

    private void checkForCollisions() {

    }


    @Override
    public void render(float delta, ShapeRenderer renderer) {
        update(delta);
        renderer.rect(position.x, position.y, size.x, size.y);
    }





    //Movements
    @Override
    public void moveLeft(boolean pressed) {
        moveLeft = pressed;
    }

    @Override
    public void moveRight(boolean pressed) {
        moveRight = pressed;
    }

    @Override
    public void duck(boolean active) {
        if (active)
            size.y /=2;
        else
            size.y *=2;
    }

    @Override
    public void attack(Vector2 direction) {

    }

    @Override
    public void defensiveMove() {

    }

    @Override
    public void powerSpeed(boolean on) {
        if (on){
            powerSpeed = POWERSPEED;
            velocity.x *= POWERSPEED;
        }
        else {
            velocity.x /= POWERSPEED;
            powerSpeed = 1;
        }


    }


    @Override
    public void jump(boolean active) {
        if (active)
            if(jumping == Jump.GROUNDED)
                jumping = Jump.JUMPING;
        if (!active)
            if (jumping == Jump.JUMPING) {
                jumping = Jump.FALLING;
                jumpingTimer = 0;
                System.out.println("Released W, now character is " + jumping);
            }
    }

    private float jumpFunction() {
        return JUMPSPEED * (1+Math.abs(velocity.x)*0.01f) *(float)Math.sin(0.5+(jumpingTimer*3.14)/3);
    }


    private void temporaryFallingFunction() {
        velocity.y -= 5;
        if (position.y < 10){
            position.y = 10;
            jumping = Jump.GROUNDED;
            velocity.y=0;
            System.out.println("Landed, character is now " + jumping);
        }
    }

    private enum Jump {
        GROUNDED, JUMPING, FALLING
    }

}
