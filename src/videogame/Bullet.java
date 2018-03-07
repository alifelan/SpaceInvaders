/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;

/**
 *
 * @author josec
 */
public class Bullet extends Item{
    
    private final int type;
    private int imageNumber;
    private final static int SPEED = 2;
    public static final int PLAYER_BULLET = -1;
    public static final int ENEMY_BULLET = 1;
    private BufferedImage sprite;
    
    /**
     * Constructor
     * @param x position in x
     * @param y position in y
     * @param width width of the bullet
     * @param height height of the bullet
     * @param type owner
     */
    public Bullet(int x, int y, int width, int height, int type) {
        super(x,y,width,height);
        this.type = type;
        imageNumber = (int)(Math.random()*116);
        if(type == PLAYER_BULLET) {
            sprite = Assets.menu[imageNumber];
        } else {
            sprite = Assets.enemyBullet;
        }
    }
    
    /**
     * checks if the bullet is out of the window
     * @param game to check the window
     * @return true if its out
     */
    public boolean isOutOfBounds(Game game) {
        return getY() + getHeight() < 0 || 
                getY() - getHeight() > game.getHeight();
    }
    
    /**
     * Saves the bullet
     * @param writer file to write
     */
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println(","+type+","+imageNumber);
    }
    
    /**
     * Loads bullet
     * @param tokens values
     * @return a bullet with the new values
     */
    public static Bullet load(int[] tokens) {
        Bullet bullet = new Bullet(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
        if(bullet.type == Bullet.PLAYER_BULLET) {
            bullet.sprite = Assets.menu[tokens[5]];
        }
        return bullet;
    }

    /**
     * Returns type
     * @return owner
     */
    public int getType() {
        return type;
    }

    @Override
    public void tick() {
        setY(getY() + SPEED*type);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(sprite, getX(), getY(), getHeight(), getWidth(), null);
    }
    
}
