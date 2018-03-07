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
    private final SoundClip boosto; // to play when the player is boosted
    
    public Boost(int x, int y, int width, int height) {
        super(x,y,width,height);
        timer = new Timer(10000);
        SPEED = 4;
        this.y = 1000;
        boosto = new SoundClip("/sounds/boosto.wav");
    }
    
    @Override
    public void save(PrintWriter writer) {
        super.save(writer);
        writer.println(","+(timer.getTime() - timer.getTimer()));
    }
    
    public static Boost load(int tokens[]) {
        Boost boost = new Boost(tokens[0], tokens[1], tokens[2], tokens[3]);
        boost.timer = new Timer(tokens[4]);
        boost.y = tokens[1];
        return boost;
    }
    
    public void crash(Player player) {
        setY(1000);
        boosto.play();
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
