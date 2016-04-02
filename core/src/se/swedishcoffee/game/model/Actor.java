package se.swedishcoffee.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Alex on 16-02-26.
 */
public abstract class Actor extends Entity {

    public Actor(World world){
        this(0, 0, 10, 10, world);
    }

    public Actor(float x, float y, float width, float height, World world){
        this(new Vector2(x, y), new Vector2(width, height), world);
    }

    public Actor(Vector2 position, Vector2 size, World world){
        super(position, size, world);
    }


    abstract public void moveLeft(boolean active);
    abstract public void moveRight(boolean active);
    abstract public void jump(boolean active);
    abstract public void duck(boolean active);
    abstract public void attack(Vector2 direction, boolean active);
    abstract public void defensiveMove();
    abstract public void powerSpeed(boolean on);
}
