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
public class Enemy extends Item {
    
    private static int SPEED = 2;
    public static int TYPE_1 = 1;
    public static int TYPE_2 = 2;
    public static int TYPE_3 = 3;
    private final int type;
    
    public Enemy(int x, int y, int width, int height, int type) {
        super(x,y,width,height);
        this.type = type;
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics g) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
