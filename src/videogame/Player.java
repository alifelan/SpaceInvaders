/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;

/**
 *
 * @author antoniomejorado
 */
public class Player extends Item {

    private final int MOVE_LEFT_KEY;    // to access keyboard
    private final int MOVE_RIGHT_KEY;   // to access keyboard
    private final int SHOOT;    // to access keyboard
    private int speed;  // players speed
    private int direction = 1;  // where the player is heading
    private int score;  // points gained
    private int lives;  // lives remaining
    private Timer timer;    // to keep track of the losing or shooting animation
    private Timer bulletGen;    // to create a bullet
    private Timer winTimer; // to keep track of the winning animation
    public boolean winner;  // to know if the player has winned
    private final SoundClip lose;   // to play the sound when the player loses
    private final SoundClip winSound;    // to play when the player wins

    // to animate the player
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

    // to keep track of current animation
    private Animation current;
    
    /**
     * Constructor
     * @param x position in x
     * @param y position in y
     * @param width width of the player
     * @param height height of the player
     * @param speed movement
     */
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
        loseRight = new Animation(Assets.playerLoseRight, 300);
        loseLeft = new Animation(Assets.playerLoseLeft, 300);
        win = new Animation(Assets.playerWin, 100);

        this.speed = speed;
        direction = 0;
        score = 0;
        lives = 5;
        timer = new Timer(0);
        winTimer = null;
        lose = new SoundClip("/sounds/ramsayInsult.wav");
        winSound = new SoundClip("/sounds/win.wav");
    }

    /**
     * Returns if the player won
     * @return winner, true if the player won
     */
    public boolean isWinner() {
        return winner;
    }

    /**
     * Sets winner state
     * @param winner state
     */
    public void setWinner(boolean winner) {
        // sets timer to null, and restarts win animation
        if(!winner){
            winTimer = null;
            win.tick();
        }
        this.winner = winner;
    }

    /**
     * Returns lives
     * @return lives remaining
     */
    public int getLives() {
        return lives;
    }

    /**
     * Sets lives
     * @param lives lives to play
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Returns score of the player
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets score, used to restart the game
     * @param score 
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Returns speed
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Sets speed
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSHOOT() {
        return SHOOT;
    }
    
    /**
     * Keeps the player in the window
     * @param game 
     */
    public void checkBounds(Game game) {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() + getWidth() > game.getWidth()) {
            setX(game.getWidth() - getWidth());
        }
    }

    /**
     * Creates a bullet
     * @return a bullet if the player pressed the key, otherwise null
     */
    public Bullet createBullet() {
        // checks if the timer exists
        if (bulletGen == null) {
            return null;
        }
        bulletGen.tick();
        // if the timer has finished, player creates a bullet
        if (bulletGen.isFinished()) {
            // sets timer to null to create more bullets when needed
            bulletGen = null;
            if (direction == 1) {
                return new Bullet(getX() + 10, getY(), 40, 40, Bullet.PLAYER_BULLET);
            } else {
                return new Bullet(getX() + getWidth() - 50, getY(), 40, 40, Bullet.PLAYER_BULLET);
            }
        }
        return null;
    }

    /**
     * Saves the player
     * @param writer file to write
     */
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println(","+speed+","+direction+","+score+","+lives);
    }
    
    /**
     * Loads the player
     * @param tokens values from the file
     * @return a new player with the new values
     */
    public static Player load(int[] tokens) {
        Player player = new Player(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
        player.direction = tokens[5];
        player.score = tokens[6];
        player.lives = tokens[7];
        return player;
    }
    
    @Override
    public void tick() {
        KeyManager keyManager = KeyManager.getInstance();
        // Checks if the player is out of lives
        if (getLives() == 0) {
            timer = null;
            Assets.lost();
            // Takes one out to avoid entering again
            lives--;
            lose.play();
        }
        // Checks if the player won and if we havent updated the animation
        if (isWinner() && winTimer == null) {
            current = win;
            winTimer = new Timer(2200);
            winSound.play();
        } 
        // Checks if the player lost
        else if (getLives() <= 0 && !isWinner()) {
            // Creates a timer if it doesnt exists
            if (timer == null) {
                timer = new Timer(500);
                // Updates the animation
                if (direction == 1) {
                    current = loseLeft;
                } else {
                    current = loseRight;
                }
            }
            timer.tick();
        } 
        // Enters if the game is going
        else if(!isWinner()){
            // Checks if the player moved left or right, or if it didnt move and selects the animation
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
            // Checks if the player pressed the shoot key and if the player is available to shoot
            if (keyManager.isReleased(SHOOT) && timer.isFinished()) {
                timer = new Timer(300);
                bulletGen = new Timer(100);
            }
            timer.tick();
            // uses the attack animation while the timer hasnt finished
            if (!timer.isFinished()) {
                if (direction == 1) {
                    current = attackLeft;
                } else {
                    current = attackRight;
                }
            }
        }
        // Enters if the player won, and the animation hasnt ended
        if(winTimer != null && !winTimer.isFinished()){
            winTimer.tick();
            frame = current.getCurrentFrame();
            current.tick();
        } else if ((getLives() > 0 || !timer.isFinished()) && !isWinner()) {
            current.tick();
            frame = current.getCurrentFrame();
            setWidth(2 * frame.getWidth());
            setHeight(2 * frame.getHeight());
            setY(660 - getHeight());
        }
    }

    @Override
    public boolean intersects(Item item) {
        if (super.intersects(item)) {
            // takes one live out if the player collided
            lives--;
            return true;
        }
        return false;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(frame, getX(), getY(),
                getWidth(), getHeight(), null);
    }
}
