/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

/**
 *
 * @author josec
 */
public class Timer {
    
    private long lastTime;
    private long time;
    private long timer;
    
    public Timer(long time) {
        lastTime = System.currentTimeMillis();
        this.time = time;
        timer = 0;
    }
    
    public Timer(long time, long timer){
        lastTime = System.currentTimeMillis();
        this.time = time;
        this.timer = timer;
    }
    
    public void setLastTime(){
        lastTime = System.currentTimeMillis();
    }

    public long getTimer() {
        return timer;
    }
    
    public long getTime() {
        return time;
    }
    
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
    }
    
    public boolean isFinished() {
        return timer > time;
    }
}
