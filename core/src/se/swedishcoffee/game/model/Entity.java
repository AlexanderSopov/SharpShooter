package se.swedishcoffee.game.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 16-02-16.
 */
public abstract class Entity {
    Vector2 position, size, velocity;
    protected Body body;

    public Entity(World world){
        this(0,0,10,10, world);
    }

    public Entity(float x, float y, float width, float height, World world){
        this(new Vector2(x,y), new Vector2(width, height), world);
    }

    public Entity(Vector2 position, Vector2 size, World world){
        this.position = position;
        this.size = size;
        velocity = new Vector2(0,0);
        initBody(world);
    }

    protected abstract void initBody(World world);


    public abstract void render(float delta, ShapeRenderer renderer);
    abstract public void update(float delta);

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getSize() {
        return size;
    }

    public Vector2 getVelocity() {
        return velocity;
    }
}
