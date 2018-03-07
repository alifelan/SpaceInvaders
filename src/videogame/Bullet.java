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
    
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println(","+type);
    }
    
    public static Bullet load(int[] tokens) {
        return new Bullet(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4]);
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
