package se.swedishcoffee.game.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Alexander on 2016-03-22.
 */
public class AimCross {

    private static final float LENGTH = 1.2f;
    private static final float THICKNESS = 0.4f;
    private static final Color COLOR = Color.RED;
    private Vector3 position;
    private Camera camera;

    public AimCross(Camera cam){
        position = new Vector3(0,0,0);
        camera = cam;
    }

    public void move(int screenX, int screenY) {
        position.x=screenX;
        position.y=screenY;
        camera.unproject(position);
    }

    public void render(ShapeRenderer renderer) {



        Vector2[] vertical = {
                new Vector2(position.x, position.y-LENGTH),
                new Vector2(position.x, position.y-THICKNESS),
                new Vector2(position.x, position.y+THICKNESS),
                new Vector2(position.x, position.y+LENGTH)
        };
        Vector2[] horizontal = {
                new Vector2(position.x-LENGTH, position.y),
                new Vector2(position.x-THICKNESS, position.y),
                new Vector2(position.x+THICKNESS, position.y),
                new Vector2(position.x+LENGTH, position.y)
        };
        renderer.setColor(COLOR);
        renderer.rectLine(vertical[0], vertical[1], THICKNESS);
        renderer.rectLine(vertical[2], vertical[3], THICKNESS);
        renderer.rectLine(horizontal[0], horizontal[1], THICKNESS);
        renderer.rectLine(horizontal[2], horizontal[3], THICKNESS);
    }
}
