/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.io.PrintWriter;

/**
 *
 * @author josec
 */
public class Boost extends Item {
    
    private Timer timer;
    private final int SPEED;
    
    public Boost(int x, int y, int width, int height) {
        super(x,y,width,height);
        timer = new Timer(10000);
        SPEED = 4;
        setY(-100);
    }
    
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println();
    }
    
    public static Boost load(int tokens[]) {
        return new Boost(tokens[0], tokens[1], tokens[2], tokens[3]);
    }
    
    public void crash(Player player) {
        setY(-100);
        if(Math.random() > 0.5){
            player.setSpeed(player.getSpeed() + 2);
        } else {
            player.setLives(player.getLives() + player.getLives() / 2 + 1);
        }
    }

    @Override
    public void tick() {
        timer.tick();
        if(timer.isFinished()) {
            timer = new Timer(20000);
            setX(0);
            setY(0);
        }
        setX(getX() + SPEED);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.boost, x, y, width, height, null);
    }
}
