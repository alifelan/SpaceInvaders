/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author josec
 */
public class Bullet extends Item{
    
    private int type;
    private static int SPEED = 2;
    public static int PLAYER_BULLET = -1;
    public static int ENEMY_BULLET = 1;
    
    public Bullet(int x, int y, int width, int height, int type) {
        super(x,y,width,height);
        this.type = type;
    }
    
    public boolean isOutOfBounds(Game game) {
        return getY() < 0 || (getY() + getWidth()) > game.getHeight();
    }

    @Override
    public void tick() {
        setY(getY() + SPEED*type);
    }

    @Override
    public void render(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
