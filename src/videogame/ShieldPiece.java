/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.awt.Color;
import java.io.PrintWriter;

/**
 *
 * @author josec
 */
public class ShieldPiece extends Item {
    
    private int lives;
    
    public ShieldPiece(int x, int y, int width, int height) {
        super(x,y,width,height);
        lives = 2;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println(","+lives);
    }
    
    public static ShieldPiece load(int tokens[]) {
        ShieldPiece piece = new ShieldPiece(tokens[0], tokens[1], tokens[2], tokens[3]);;
        piece.setLives(tokens[4]);
        return piece;
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics g) {
        String hex;
        if(lives == 2) {
            hex = "60BF00";
        } else {
            hex = "67725B";
        }
        g.setColor(new Color(Integer.parseInt(hex, 16)));
        g.fillRect(x,y,width,height);
    }
    
}
