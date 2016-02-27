package se.swedishcoffee.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
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
    private static final float JUMPSPEED = 150;
    private static final float WALKSPEED = 30;
    private static final float POWERSPEED = 2.2f;


    //flags
    private float powerSpeed = 1;
    private float jumpingTimer = 0;
    private Jump jumpState = Jump.GROUNDED;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private Body body;
    Fixture standingfixture;
    Fixture duckingfixture;


    //Fields
    Box2DDebugRenderer debugRenderer;


    /*
    *
    * Constructors and inits
    *
    *
     */
    public Player(World world){
        this(30, 50, 2, 4, world);
    }

    public Player(float x, float y, float width, float height, World world){
        this(new Vector2(x, y), new Vector2(width, height), world);
    }

    public Player(Vector2 position, Vector2 size, World world){
        super(position, size, world);
        controller = new Controller(this);
        Gdx.input.setInputProcessor(controller);
        debugRenderer = new Box2DDebugRenderer();
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
        PolygonShape shapeLarge = new PolygonShape();
        PolygonShape shapeSmall = new PolygonShape();
        /* We are a box, so this makes sense, no?
        // Basically set the physics polygon to a box with the same dimensions
        as our sprite//*/
        shapeLarge.setAsBox(size.x, size.y);
        shapeSmall.setAsBox(size.x, size.y/2);

        // FixtureDef is a confusing expression for physical properties
        // Basically this is where you, in addition to defining the shape of the
        //body
        // you also define it's properties like density, restitution and others
        //we will see shortly
        // If you are wondering, density and area are used to calculate over all
        //mass

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shapeLarge;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0;

        standingfixture = body.createFixture(fixtureDef);



        FixtureDef newFixtureDef = new FixtureDef();
        newFixtureDef.shape = shapeSmall;
        newFixtureDef.density = 2f;
        fixtureDef.friction = 0;

        duckingfixture = body.createFixture(newFixtureDef);
        body.getFixtureList().removeValue(duckingfixture, true);

        // Shape is the only disposable of the lot, so get rid of it
        shapeSmall.dispose();//*/
        // Shape is the only disposable of the lot, so get rid of it
        shapeLarge.dispose();
    }


    //update
    public void update(float delta){
        updateVelocity(delta);
        // Now update the spritee position accordingly to it's now updated
        //Physics body
        //position = new Vector2(body.getPosition());
        //System.out.println(String.format("updating Player, position.x = %f, position.y = %f", position.x, position.y));

    }

    private void updateVelocity(float delta) {
        // Left/right movement

        Vector2 pos = body.getPosition();
        if (moveRight && moveLeft && jumpState == Jump.GROUNDED)
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        else if (moveLeft)
            body.applyLinearImpulse(-WALKSPEED * powerSpeed, 0, pos.x, pos.y, true);
        else if (moveRight)
            body.applyLinearImpulse(WALKSPEED * powerSpeed, 0, pos.x, pos.y, true);
        else
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        //*/

        //jump mechanics

        if (jumpState == Jump.JUMPING) {
            //System.out.println("character is " + jumping);
            jumpingTimer += delta;
            //body.applyForceToCenter(0, jumpFunction(),true);
            body.applyLinearImpulse(new Vector2(0,jumpFunction()), body.getPosition(),true);
            if (jumpingTimer > 0.5) {
                jumpState = Jump.FALLING;
                //System.out.println("Maxed out jumping, now " + jumping);
                jumpingTimer = 0;
            }
        }
        if (jumpState == Jump.FALLING)
            if (Math.abs(body.getLinearVelocity().y) < 0.1f)
                jumpState = Jump.GROUNDED;


    }



    @Override
    public void render(World world, com.badlogic.gdx.graphics.Camera camera) {
        debugRenderer.render(world, camera.combined);
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

        if (active) {
            body.getFixtureList().removeValue(standingfixture, true);
            body.getFixtureList().add(duckingfixture);

        }else {
            body.getFixtureList().removeValue(duckingfixture, true);
            body.getFixtureList().add(standingfixture);
        }
    }

    @Override
    public void attack(Vector2 direction) {

    }

    @Override
    public void defensiveMove() {

    }

    @Override
    public void powerSpeed(boolean on) {
        if (on) {
            if (jumpState == Jump.GROUNDED) {
                powerSpeed = POWERSPEED;
                //velocity.x *= POWERSPEED;
            }
        }else {
            //velocity.x /= POWERSPEED;
            powerSpeed = 1;
        }


    }


    @Override
    public void jump(boolean active) {
        if (active)
            if(jumpState == Jump.GROUNDED)
                jumpState = Jump.JUMPING;
        if (!active)
            if (jumpState == Jump.JUMPING) {
                jumpState = Jump.FALLING;
                jumpingTimer = 0;
                //System.out.println("Released W, now character is " + jumpState);
            }
    }

    private float jumpFunction() {
        return JUMPSPEED * (Math.max(0.7f, Math.abs(velocity.x)*0.03f)) *(float)Math.sin(0.6+(jumpingTimer*3.14)/4);
    }


    private enum Jump {
        GROUNDED, JUMPING, FALLING
    }

}
