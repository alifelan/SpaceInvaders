/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author josec
 */
public class Bullet extends Item{
    
    private final int type;
    private final static int SPEED = 2;
    public static final int PLAYER_BULLET = -1;
    public static final int ENEMY_BULLET = 1;
    private final BufferedImage sprite;
    
    public Bullet(int x, int y, int width, int height, int type) {
        super(x,y,width,height);
        this.type = type;
        if(type == PLAYER_BULLET) {
            sprite = Assets.menu[(int)(Math.random()*116)];
        } else {
            sprite = Assets.enemyBullet;
        }
    }
    
    public boolean isOutOfBounds(Game game) {
        return getY() + getHeight() < 0 || 
                getY() - getHeight() > game.getHeight();
    }

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
