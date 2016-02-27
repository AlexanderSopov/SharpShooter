package se.swedishcoffee.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import se.swedishcoffee.game.control.Controller;

/**
 * Created by Alex on 16-02-16.
 */
public class Player extends Actor {
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
    Body body;


    /*
    *
    * Constructors and inits
    *
    *
     */
    public Player(World world){
        this(30, 50, 5, 10, world);
    }

    public Player(float x, float y, float width, float height, World world){
        this(new Vector2(x, y), new Vector2(width, height), world);
    }

    public Player(Vector2 position, Vector2 size, World world){
        super(position, size, world);
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
    }

    protected void initBody(World world) {
        // Now create a BodyDefinition.  This defines the physics objects type
        //and position in the simulation
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        // We are going to use 1 to 1 dimensions.  Meaning 1 in physics engine
        //is 1 pixel
        // Set our body to the same position as our sprite
        bodyDef.position.set(position.x, position.y);

        // Create a body in the world using our definition
        body = world.createBody(bodyDef);

        // Now define the dimensions of the physics shape
        PolygonShape shape = new PolygonShape();
        /* We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensions
        as our sprite//*/
        shape.setAsBox(size.x, size.y);

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the
        //body
        // you also define it's properties like density, restitution and others
        //we will see shortly
        // If you are wondering, density and area are used to calculate over all
        //mass

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        // Shape is the only disposable of the lot, so get rid of it
        shape.dispose();
    }


    //update
    public void update(float delta){
        updateVelocity(delta);
        // Now update the spritee position accordingly to it's now updated
        //Physics body
        position = new Vector2(body.getPosition().x, body.getPosition().y);
        temporaryFallingFunction();
        //System.out.println(String.format("updating Player, position.x = %f, position.y = %f", position.x, position.y));

    }

    private void updateVelocity(float delta) {
        // Left/right movement
        if (moveLeft) 
            body.applyForceToCenter(-WALKSPEED * powerSpeed, 0, true);
        else if (moveRight)
            body.applyForceToCenter(WALKSPEED*powerSpeed, 0, true);
        else
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        //*/

        //jump mechanics
        if (jumping == Jump.JUMPING) {
            //System.out.println("character is " + jumping);
            jumpingTimer += delta;
            body.applyForceToCenter(0, jumpFunction(),true);
            if (jumpingTimer > 0.5) {
                jumping = Jump.FALLING;
                //System.out.println("Maxed out jumping, now " + jumping);
                jumpingTimer = 0;
            }
        }
        if (jumping == Jump.FALLING)
            temporaryFallingFunction();

    }



    @Override
    public void render(float delta, ShapeRenderer renderer) {
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(com.badlogic.gdx.graphics.Color.BLACK);
        renderer.rect(body.getPosition().x, body.getPosition().y, size.x, size.y);
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
        //velocity.y -= 5;
        if (position.y < 10){
            body.applyForceToCenter(0, 100f, true);
            jumping = Jump.GROUNDED;
            velocity.y=0;
            System.out.println("Landed, character is now " + jumping + " and bodyposition = " + body.getPosition().y);
        }
    }

    private enum Jump {
        GROUNDED, JUMPING, FALLING
    }

}
