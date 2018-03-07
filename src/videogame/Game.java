/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;


/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {
    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private final int width;              // width of the window
    private final int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private boolean paused;         // flag that checks if game is paused
    private final int PAUSED_KEY;
    private final int LOAD_KEY;
    private final int SAVE_KEY;
    private final int RESET_KEY;
    private Player player;          // to use a player
    private final KeyManager keyManager;  // to manage the keyboard
    private final ArrayList<Bullet> bullets;
    private EnemyBlock enemyBlock;
    
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        bullets = new ArrayList<>();
        paused = false;
        PAUSED_KEY = KeyEvent.VK_P;
        LOAD_KEY = KeyEvent.VK_L;
        SAVE_KEY = KeyEvent.VK_S;
        RESET_KEY = KeyEvent.VK_R;
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init() {
         display = new Display(title, getWidth(), getHeight());  
         Assets.init();
         player = new Player(0, getHeight() - 170, 100, 150, 4);
         enemyBlock = new EnemyBlock(getWidth(), getHeight());
         display.getJframe().addKeyListener(keyManager);
    }
    
    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;
            
            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta --;
            }
        }
        stop();
    }
    
    private void tick() {
        keyManager.tick();
        // avancing player with colision
        if(keyManager.isReleased(PAUSED_KEY)) {
            paused = !paused;
        }
        if(paused) {
            return;
        }
        player.tick();
        player.checkBounds(this);
        enemyBlock.tick();
        for(int i = 0; i < bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            bullet.tick();
            if(bullet.isOutOfBounds(this) || enemyBlock.hasCrashed(bullet)) {
                bullets.remove(i);
                i--;
            } 
        }
        Bullet bullet = player.createBullet();
        if(bullet != null){
            bullets.add(bullet);
        }
        ArrayList<Bullet> shot = enemyBlock.shoot();
        for(Bullet bullet1 : shot){
            bullets.add(bullet1);
        }
    }
    
    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
        */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        }
        else
        {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            enemyBlock.render(g);
            for(Bullet bullet : bullets){
                bullet.render(g);
            }
            if(paused) {
                g.drawImage(Assets.pause, 100, 100, width- 200, height - 200, null);
            }
            bs.show();
            g.dispose();
        }
       
    }
    
    /**
     * setting the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }
    
    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }           
        }
    }

 
    


}
