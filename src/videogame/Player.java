/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;


/**
 *
 * @author antoniomejorado
 */
public class Player extends Item{

    private final int MOVE_LEFT_KEY;
    private final int MOVE_RIGHT_KEY;
    private final int SHOOT_KEY;
    private int speed;    
    public Player(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        MOVE_LEFT_KEY = KeyEvent.VK_LEFT;
        MOVE_RIGHT_KEY = KeyEvent.VK_RIGHT;
        SHOOT_KEY = KeyEvent.VK_SPACE;
        this.speed = speed;
    }
    
    public void checkBounds(Game game) {
        if(getX() < 0){
            setX(0);
        }
        if(getX() + getWidth() > game.getWidth()) {
            setX(game.getWidth() - getWidth());
        }
    }

    @Override
    public void tick() {
        KeyManager keyManager = KeyManager.getInstance();
        if(keyManager.isPressed(MOVE_LEFT_KEY)) {
            setX(getX() - speed);
        }
        if(keyManager.isPressed(MOVE_RIGHT_KEY)) {
            setX(getX() + speed);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
}
