package se.swedishcoffee.game.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alexander on 2016-02-27.
 */
public class Tile extends Entity {
    public Tile(World world) {
        this(60, 0, 50, 5, world);
    }

    public Tile(float x, float y, float width, float height, World world) {
        this(new Vector2(x, y), new Vector2(width, height), world);
    }

    public Tile(Vector2 position, Vector2 size, World world) {
        super(position, size, world);
        initBody(world);
    }

    @Override
    protected void initBody(World world) {
        // Create our body definition
        BodyDef groundBodyDef =new BodyDef();
// Set its world position
        groundBodyDef.position.set(position);

// Create a body from the defintion and add it to the world
        body = world.createBody(groundBodyDef);

// Create a polygon shape
        PolygonShape groundBox = new PolygonShape();
// Set the polygon shape as a box which is twice the size of our view port and 20 high
// (setAsBox takes half-width and half-height as arguments)
        groundBox.setAsBox(size.x, size.y);
// Create a fixture from our polygon shape and add it to our ground body
        body.createFixture(groundBox, 0.0f);
// Clean up after ourselves
        groundBox.dispose();
    }

    @Override
    public void render(World world, Camera camera, ShapeRenderer renderer) {

    }

    @Override
    public void update(float delta) {

    }
}
