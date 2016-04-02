package se.swedishcoffee.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.Viewport;

import se.swedishcoffee.game.control.Controller;

/**
 * Created by Alex on 16-02-16.
 */
public class Player extends Actor {

    //Members
    private final AimCross aimCross;
    private Controller controller;
    private Viewport viewport;

    //Constants
    private static final float JUMPSPEED = 150;
    private static final float WALKSPEED = 30;
    private static final float POWERSPEED = 2.2f;


    //flags
    private float powerSpeed = 1;
    private float jumpingTimer = 0;
    private States state = States.GROUNDED;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    private Body body;
    Fixture standingfixture;
    Fixture duckingfixture;


    //Fields
    float shootAngle;
    Box2DDebugRenderer debugRenderer;


    /*
    *
    * Constructors and inits
    *
    *
     */
    public Player(World world, Viewport viewport){
        this(30, 50, 1, 2, world, viewport);
    }

    public Player(float x, float y, float width, float height, World world, Viewport viewport){
        this(new Vector2(x, y), new Vector2(width, height), world, viewport);
    }

    public Player(Vector2 position, Vector2 size, World world,Viewport viewport){
        super(position, size, world);
        aimCross = new AimCross(viewport.getCamera());
        this.viewport = viewport;
        controller = new Controller(this, aimCross);
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
        bodyDef.fixedRotation = true;

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


    public void update(float delta) {
        // Left/right movement

        Vector2 pos = body.getPosition();
        if (moveRight && moveLeft && state == States.GROUNDED)
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        else if (moveLeft)
            body.setLinearVelocity(-WALKSPEED * powerSpeed, body.getLinearVelocity().y);
        else if (moveRight)
            body.setLinearVelocity(WALKSPEED * powerSpeed, body.getLinearVelocity().y);
        else
            body.setLinearVelocity(0, body.getLinearVelocity().y);


        //Attack
        /*
        if (state == States.JUMPING) {
            //System.out.println("character is " + jumping);
            jumpingTimer += delta;
            //body.applyForceToCenter(0, jumpFunction(),true);
            body.setLinearVelocity(body.getLinearVelocity().x,jumpFunction());
            if (jumpingTimer > 0.5) {
                state = States.FALLING;
                //System.out.println("Maxed out jumping, now " + jumping);
                jumpingTimer = 0;
            }
        }//*/


        //*/
        //jump mechanics

        if (state == States.JUMPING) {
            //System.out.println("character is " + jumping);
            jumpingTimer += delta;
            //body.applyForceToCenter(0, jumpFunction(),true);
            body.setLinearVelocity(body.getLinearVelocity().x,jumpFunction());
            if (jumpingTimer > 0.5) {
                state = States.FALLING;
                //System.out.println("Maxed out jumping, now " + jumping);
                jumpingTimer = 0;
            }
        }
        if (state == States.FALLING)
            if (Math.abs(body.getLinearVelocity().y) < 0.1f)
                state = States.GROUNDED;


    }



    @Override
    public void render(World world, Camera camera, ShapeRenderer renderer) {
        aimCross.render(renderer);
        debugRenderer.render(world, camera.combined);

    }





    //Movements
    @Override
    public void moveLeft(boolean pressed) {
        moveLeft = pressed;
        if(state == States.SHOOTING)
            moveLeft = false;
    }

    @Override
    public void moveRight(boolean pressed) {
        moveRight = pressed;
        if(state == States.SHOOTING)
            moveRight = false;
    }

    @Override
    public void duck(boolean active) {
        PolygonShape shape = (PolygonShape)standingfixture.getShape();
        if (active) {
            shape.setAsBox(size.x, size.y/2);
        }else {
            shape.setAsBox(size.x, size.y);
        }
        //shape.dispose();
    }

    @Override
    public void attack(Vector2 direction, boolean active) {
        if (active && state == States.GROUNDED)
            shoot(new Vector3(direction.x,direction.y,0));
        else if (!active && state == States.SHOOTING)
            state = States.GROUNDED;
    }

    private void shoot(Vector3 direction) {
        state = States.SHOOTING;
        Vector3 v3 = viewport.getCamera().unproject(direction)
                .sub(position.x, position.y,0);
        Vector2 v = new Vector2(v3.x, v3.y);
        shootAngle = v.angle();
        moveLeft = false;
        moveRight= false;
    }

    @Override
    public void defensiveMove() {

    }

    @Override
    public void powerSpeed(boolean on) {
        if (on) {
            if (state == States.GROUNDED) {
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
            if(state == States.GROUNDED)
                state = States.JUMPING;
        if (!active)
            if (state == States.JUMPING) {
                state = States.FALLING;
                jumpingTimer = 0;
                //System.out.println("Released W, now character is " + state);
            }
    }

    private float jumpFunction() {
        return 30f;
        //return JUMPSPEED * (Math.max(0.3f, Math.abs(velocity.x)*0.001f)) *(float)Math.sin(0.6+(jumpingTimer*3.14)/4);
    }



    private enum States {
        GROUNDED, JUMPING, FALLING, SHOOTING
    }

}
