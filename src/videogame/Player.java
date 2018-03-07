/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item {

    private final int MOVE_LEFT_KEY;
    private final int MOVE_RIGHT_KEY;
    private final int SHOOT;
    private int speed;
    private int direction = 1;
    private int score;
    private int lives;
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
    private BufferedImage frame;

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
        attackRight = new Animation(Assets.playerAttackRight, 100);
        attackLeft = new Animation(Assets.playerAttackLeft, 100);
        loseRight = new Animation(Assets.playerLoseRight, 100);
        loseLeft = new Animation(Assets.playerLoseLeft, 100);
        win = new Animation(Assets.playerWin, 100);

        this.speed = speed;
        direction = 0;
        score = 0;
        lives = 3;
        timer = new Timer(0);
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void checkBounds(Game game) {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() + getWidth() > game.getWidth()) {
            setX(game.getWidth() - getWidth());
        }
    }

    public Bullet createBullet() {
        if (bulletGen == null) {
            return null;
        }
        bulletGen.tick();
        if (bulletGen.isFinished()) {
            bulletGen = null;
            if (direction == 1) {
                return new Bullet(getX() + 10, getY(), 40, 40, Bullet.PLAYER_BULLET);
            } else {
                return new Bullet(getX() + getWidth() - 50, getY(), 40, 40, Bullet.PLAYER_BULLET);
            }
        }
        return null;
    }

    @Override
    public void tick() {
        KeyManager keyManager = KeyManager.getInstance();
        if (keyManager.isPressed(MOVE_LEFT_KEY)) {
            setX(getX() - speed);
            current = moveLeft;
            direction = 1;
        } else if (keyManager.isPressed(MOVE_RIGHT_KEY)) {
            setX(getX() + speed);
            current = moveRight;
            direction = 0;
        } else {
            if (direction == 1) {
                current = idleLeft;
            } else {
                current = idleRight;
            }
        }
        if (keyManager.isReleased(SHOOT) && timer.isFinished()) {
            timer = new Timer(300);
            bulletGen = new Timer(100);
        }
        timer.tick();
        if (!timer.isFinished()) {
            if (direction == 1) {
                current = attackLeft;
            } else {
                current = attackRight;
            }
        }
        current.tick();
        frame = current.getCurrentFrame();
        setWidth(3 * frame.getWidth());
        setHeight(3 * frame.getHeight());
        setY(660 - getHeight());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(frame, getX(), getY(),
                getWidth(), getHeight(), null);
    }
}
