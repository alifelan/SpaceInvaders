/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.io.PrintWriter;
import java.awt.Rectangle;

/**
 *
 * @author josec
 */
public class Enemy extends Item {
    
    private static int SPEED = 2;
    public static int TYPE_1 = 1;
    public static int TYPE_2 = 2;
    public static int TYPE_3 = 3;
    private final int type;
    // Animations
    private static Animation walkLeft;
    private static Animation walkRight;
    private static Animation walkDown;
    private static Animation walkUp;
    private static Animation current;
    private static int direction;
    
    /**
     * Constructor
     * @param x position in x
     * @param y position in y
     * @param width width of the enemy
     * @param height height of the enemy
     * @param type 
     */
    public Enemy(int x, int y, int width, int height, int type) {
        super(x,y,width,height);
        this.type = type;
        walkLeft = new Animation(Assets.clientWalkLeft, 100);
        walkRight = new Animation(Assets.clientWalkRight, 100);
        walkDown = new Animation(Assets.clientWalkDown, 100);
        walkUp = new Animation(Assets.clientWalkUp, 100);
        current = walkRight;
    }

    /**
     * Updates direction
     * @param direction Where its heading
     */
    public static void setDirection(int direction) {
        Enemy.direction = direction;
        if(direction == 0){
            current = walkRight;
        } else if(direction == 1){
            current = walkLeft;
        } else {
            current = walkDown;
        }
    }
    
    /**
     * Returns direction
     * @return where the enemy is heading
     */
    public static int getDirection(){
        return direction;
    }

    /**
     * Creates a bullet randomly
     * @return null if the enemy didnt create a bullet, a new bullet if it did
     */
    public Bullet createBullet() {
        int prob = (int)(Math.random()*1000);
        if(prob < 995) {
            return null;
        }
        return new Bullet(getX() + getWidth()/2-10, getY() + getHeight(), 
                20, 20, Bullet.ENEMY_BULLET);
    }
    
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println(","+type);
    }
    
    /**
     * Loads the enemy
     * @param tokens values
     * @return a new enemy with the values
     */
    public static Enemy load(int[] tokens) {
        return new Enemy(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
    }
    
    /**
     * Moves the enemy depending of its direction
     */
    @Override
    public void tick() {
        if(direction == 0){
            setX(getX() + 1);
        } else if(direction == 1){
            setX(getX() - 1);
        } else {
            setY(getY() + 1);
        }
        current.tick();
    }
    
     /**
     * Returns its rectangle
     * @return 
     */
    @Override
    public Rectangle getBounds() {
        return new Rectangle(getX() + 10, getY(), getWidth() - 15, getHeight());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(current.getCurrentFrame(), getX(), getY(), 
                getWidth(), getHeight(), null);
    }
    
}
