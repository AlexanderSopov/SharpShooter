package se.swedishcoffee.game.model;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Alex on 16-02-16.
 */
public abstract class Entity {
    Vector2 position, size, velocity;

    public Entity(){
        this(0,0,10,10);
    }

    public Entity(float x, float y, float width, float height){
        this(new Vector2(x,y), new Vector2(width, height));
    }

    public Entity(Vector2 position, Vector2 size){
        this.position = position;
        this.size = size;
        velocity = new Vector2(0,0);
    }

    abstract public void moveLeft(boolean active);
    abstract public void moveRight(boolean active);
    abstract public void jump(boolean active);
    abstract public void duck(boolean active);
    abstract public void attack(Vector2 direction);
    abstract public void defensiveMove();
    abstract public void powerSpeed(boolean on);
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
