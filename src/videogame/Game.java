/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;

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
    private final int PAUSED_KEY;   // to access keyboard
    private final int LOAD_KEY;     // to access keyboard
    private final int SAVE_KEY;     // to access keyboard
    private final int RESET_KEY;    // to access keyboard
    private Player player;          // to use a player
    private final KeyManager keyManager;  // to manage the keyboard
    private final ArrayList<ArrayList<ShieldPiece>> shields;
    private final ArrayList<Bullet> bullets; // to store bullets
    private EnemyBlock enemyBlock;  // to store enemies
    private Boost boost;            // to store boost
    private final SoundClip shieldHit;  // to play sound when the shield is hit
    private final SoundClip shoot;  // to play when someone shoots
    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
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
        shields = new ArrayList<>();
        shieldHit = new SoundClip("/sounds/drop.wav");
        shoot = new SoundClip("/sounds/throw.wav");
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
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
        player = new Player(getWidth() / 2 - 35, getHeight() - 170, 70, 100, 4);
        enemyBlock = new EnemyBlock(getWidth(), getHeight());
        boost = new Boost(0, 0, 35, 35);
        for (int i = 0; i < 3; i++) {
            shields.add(new ArrayList<>());
            int x = i * getWidth() / 3 + getWidth() / 6 - 75;
            for (int j = 0; j < 10; j++) {
                shields.get(i).add(new ShieldPiece(x + j * 15, getHeight() - 200, 15, 15));
            }
        }
        display.getJframe().addKeyListener(keyManager);
    }
    /**
     * Splits a string into integer tokens
     * @param s string from the file
     * @return array of integers
     */
    private int[] sToInt(String s) {
        String tokensS[] = s.split(",");
        int tokens[] = new int[tokensS.length];
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = Integer.parseInt(tokensS[i]);
        }
        return tokens;
    }
    
    /**
     * Calls every save method from the pbjects
     */
    private void save() {
        try {
            PrintWriter writer = new PrintWriter("data.txt");
            player.save(writer);
            enemyBlock.save(writer);
            writer.println("" + bullets.size());
            for (Bullet bullet : bullets) {
                bullet.save(writer);
            }
            for (int i = 0; i < 3; i++) {
                writer.println(shields.get(i).size());
                for (ShieldPiece piece : shields.get(i)) {
                    piece.save(writer);
                }
            }
            boost.save(writer);
            writer.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
    
    /**
     * Loads objects
     */
    private void load() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("data.txt"));
            player = Player.load(sToInt(reader.readLine()));
            enemyBlock.load(reader);
            int b = Integer.parseInt(reader.readLine());
            bullets.clear();
            for (int i = 0; i < b; i++) {
                bullets.add(Bullet.load(sToInt(reader.readLine())));
            }
            for (int i = 0; i < 3; i++) {
                shields.get(i).clear();
                int s = Integer.parseInt(reader.readLine());
                for (int j = 0; j < s; j++) {
                    shields.get(i).add(ShieldPiece.load(sToInt(reader.readLine())));
                }
            }
            boost = Boost.load(sToInt(reader.readLine()));
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
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
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();
        // checks if the user pressed pause
        if(keyManager.isReleased(PAUSED_KEY)) {
            paused = !paused;
            enemyBlock.getTimer().setLastTime();
        }
        // Saves or loads if the player pressed the key
        if(paused) {
            if(keyManager.isReleased(LOAD_KEY)){
                load();
            }
            if (keyManager.isReleased(SAVE_KEY)) {
                save();
            }
            return;
        }
        player.tick();
        // moves everything if the game is going
        if(player.getLives() > 0 && !player.isWinner()) {
            player.checkBounds(this);
            enemyBlock.tick();
            boost.tick();
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.tick();
                // checks if the bullet is out, or if it hits something depending on its type
                if(bullet.isOutOfBounds(this) || (bullet.getType() == Bullet.ENEMY_BULLET && 
                        player.intersects(bullet))) {
                    bullets.remove(i);
                    i--;
                } else if (bullet.getType() == Bullet.PLAYER_BULLET
                        && enemyBlock.hasCrashed(bullet)) {
                    player.setScore(player.getScore() + 100);
                    bullets.remove(i);
                    i--;
                } else if(bullet.intersects(boost)) {
                    bullets.remove(i);
                    i--;
                    boost.crash(player);
                }
            }
            // Checks if the player created a bullet, and if it did adds it to the array
            Bullet bullet = player.createBullet();
            if (bullet != null) {
                shoot.play();
                bullets.add(bullet);
            }
            // checks if the enemies created bullets, and if they did adds them to the array
            ArrayList<Bullet> shot = enemyBlock.shoot();
            shot.forEach((bullet1) -> {
                bullets.add(bullet1);
                if(bullet == null){
                    shoot.play();
                }
            });
            for (ArrayList<ShieldPiece> shield : shields) {
                for (int i = 0; i < shield.size(); i++) {
                    for (int j = 0; j < bullets.size(); j++) {
                        if (shield.get(i).intersects(bullets.get(j))) {
                            shield.get(i).setLives(shield.get(i).getLives() - 1);
                            shieldHit.play();
                            bullets.remove(j);
                            break;
                        }
                    }
                    if (shield.get(i).getLives() == 0) {
                        shield.remove(i);
                        i--;
                    }
                }
            }
            for (ArrayList<ShieldPiece> shield : shields) {
                enemyBlock.crash(shield);
            }
        } else {
            bullets.clear();
            if(keyManager.isReleased(RESET_KEY)){
                if(player.getLives() <= 0){
                    reset();
                } else {
                    continueGame();
                }
            }
        }
        // checks if the player won
        if(enemyBlock.isEmpty() && !player.isWinner()){
            player.setWinner(true);
            bullets.clear();
        }
        // checks if the player lost by having enemies on the ground
        if(enemyBlock.isOnGround() && player.getLives() > 0)
            player.setLives(0);
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
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            enemyBlock.render(g);
            boost.render(g);
            for (Bullet bullet : bullets) {
                bullet.render(g);
            }
            for (ArrayList<ShieldPiece> shield : shields) {
                for (ShieldPiece piece : shield) {
                    piece.render(g);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font(g.getFont().getFontName(), Font.PLAIN, 40));
            g.drawString("Score: " + String.valueOf(player.getScore()),
                    getWidth() - getWidth() / 5, 690);
            for (int i = 0; i < player.getLives(); i++) {
                g.drawImage(Assets.lives, i * 40 + 20, getHeight() - 40, 40, 40, null);
            }
            if (paused) {
                g.drawImage(Assets.pause, 100, 100, width - 200, height - 200, null);
            }
            if(player.isWinner()){
                g.drawString("Press 'R' to play continue", 
                    getWidth()/3, getHeight() / 2);
            } else if(player.getLives() <= 0){
                g.drawString("Press 'R' to play again", 
                    getWidth()/3, getHeight() / 2);
            }
            bs.show();
            g.dispose();
        }
    }
    
    /**
     * Prepares the game for the player to keep on
     */
    public void continueGame(){
        enemyBlock = new EnemyBlock(getWidth(), getHeight());
        player.setWinner(false);
    }
    
    /**
     * Restarts the game
     */
    public void reset() {
        player.stopLose();
        player = new Player(getWidth() / 2 - player.getWidth() / 2, getHeight() - 170, 70, 100, 4);
        enemyBlock = new EnemyBlock(getWidth(), getHeight());
        Assets.reset();
              for (int i = 0; i < 3; i++) {
            shields.add(new ArrayList<>());
            int x = i * getWidth() / 3 + getWidth() / 6 - 75;
            for (int j = 0; j < 10; j++) {
                shields.get(i).add(new ShieldPiece(x + j * 15, getHeight() - 200, 15, 15));
            }
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
