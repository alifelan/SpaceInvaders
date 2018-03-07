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
    
    /**
     * Constructor
     * @param time how much time to check
     */
    public Timer(long time) {
        lastTime = System.currentTimeMillis();
        this.time = time;
        timer = 0;
    }
    
    /**
     * Constructor for a started timer
     * @param time how much time to check
     * @param timer current time
     */
    public Timer(long time, long timer){
        lastTime = System.currentTimeMillis();
        this.time = time;
        this.timer = timer;
    }
    
    /**
     * Updates last time to avoid problems with pause
     */
    public void setLastTime(){
        lastTime = System.currentTimeMillis();
    }

    /**
     * Returns timer
     * @return timer
     */
    public long getTimer() {
        return timer;
    }
    
    /**
     * Returns time
     * @return time
     */
    public long getTime() {
        return time;
    }
    
    /**
     * Ticks the timer
     */
    public void tick() {
        timer += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
    }
    
    /**
     * Checks if the timer has finished
     * @return true if it finished
     */
    public boolean isFinished() {
        return timer > time;
    }
}
