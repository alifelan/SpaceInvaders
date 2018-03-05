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
    private final int SHOOT;
    private int speed;    
    private int direction = 1;
    private Timer timer;
    private Timer bulletGen;

    private final Animation idleRight;
    private final Animation idleLeft;
    private final Animation moveRight;
    private final Animation moveLeft;
    private final Animation attackRight;
    private final Animation attackLeft;
    private final Animation loseRight;
    private final Animation loseLeft;
    private final Animation win;

    private Animation current;
    
    public Player(int x, int y, int width, int height, int speed) {
        super(x, y, width, height);
        MOVE_LEFT_KEY = KeyEvent.VK_LEFT;
        MOVE_RIGHT_KEY = KeyEvent.VK_RIGHT;
        SHOOT = KeyEvent.VK_SPACE;

        idleRight = new Animation(Assets.playerIdleRight, 100);
        idleLeft = new Animation(Assets.playerIdleLeft, 100);
        moveRight = new Animation(Assets.playerMoveRight, 100);
        moveLeft = new Animation(Assets.playerMoveLeft, 100);
        attackRight = new Animation(Assets.playerAttackRight, 200);
        attackLeft = new Animation(Assets.playerAttackLeft, 200);
        loseRight = new Animation(Assets.playerLoseRight, 100);
        loseLeft = new Animation(Assets.playerLoseLeft, 100);
        win = new Animation(Assets.playerWin, 100);

        this.speed = speed;
        direction = 0;
    }
    
    public void checkBounds(Game game) {
        if(getX() < 0){
            setX(0);
        }
        if(getX() + getWidth() > game.getWidth()) {
            setX(game.getWidth() - getWidth());
        }
    }
    
    public Bullet createBullet(){
        if(timer == null) {
            return null;
        }
        bulletGen.tick();
        if(bulletGen.isFinished()){
            timer = null;
            if(direction == 1){
                return new Bullet(getX() + 10, getY(), 40, 40, 2);
            } else {
                return new Bullet(getX() + getWidth() - 10, getY(), 40, 40, 2);
            }
        }
        return null;
    }

    @Override
    public void tick() {
        KeyManager keyManager = KeyManager.getInstance();
        if(keyManager.isPressed(MOVE_LEFT_KEY)) {
            setX(getX() - speed);
            current = moveLeft;
            direction = 1;
        } 
        else if(keyManager.isPressed(MOVE_RIGHT_KEY)) {
            setX(getX() + speed);
            current = moveRight;
            direction = 0;
        } else {
            if(direction == 1) {
                current = idleLeft;
            } else {
                current = idleRight;
            }
        }
        if(keyManager.isReleased(SHOOT) && (timer == null || timer.isFinished())) {
            timer = new Timer(500);
            bulletGen = new Timer(100);
        }
        if(timer != null) {
            timer.tick();
            if(!timer.isFinished()) {
                if(direction == 1) {
                    current = attackLeft;
                } else {
                    current = attackRight;
                }
            }
        }
        current.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(current.getCurrentFrame(), getX(), getY(), 
                getWidth(), getHeight(), null);
    }
}
