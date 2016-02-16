package se.swedishcoffee.game.model;

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

    abstract public void moveLeft();
    abstract public void moveRight();
    abstract public void jump();
    abstract public void duck();
    abstract public void attack(Vector2 direction);
    abstract public void defensiveMove();
    abstract public void powerSpeed(boolean on);

}
