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
    private int speed;    

    private Animation idleRight;
    private Animation idleLeft;
    private Animation moveRight;
    private Animation moveLeft;
    private Animation attackRight;
    private Animation attackLeft;
    private Animation loseRight;
    private Animation win;

    public Player(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        MOVE_LEFT_KEY = KeyEvent.VK_LEFT;
        MOVE_RIGHT_KEY = KeyEvent.VK_RIGHT;

        idleRight = new Animation(Assets.playerIdleRight, 100);
        idleLeft = new Animation(Assets.playerIdleLeft, 100);
        moveRight = new Animation(Assets.playerMoveRight, 100);
        moveLeft = new Animation(Assets.playerMoveLeft, 100);
        attackRight = new Animation(Assets.playerAttackRight, 100);
        attackLeft = new Animation(Assets.playerAttackLeft, 100);
        loseRight = new Animation(Assets.playerLoseRight, 100);
        win = new Animation(Assets.playerWin, 100);

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
        win.tick();
    }

    @Override
    public void render(Graphics g) {

    }
}
