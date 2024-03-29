/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Ali
 */
public class EnemyBlock {

    private ArrayList<ArrayList<Enemy>> enemies;    // to store enemies
    private Timer timer;    // to move enemies
    private ArrayList<Timer> timers;    // to make enemies shoot
    private final SoundClip eat;
    
    /**
     * Constructor
     * @param width width of the window
     * @param height height of the window
     */
    public EnemyBlock(int width, int height){
        int enemiesX = (width - 300) / 60;
        int enemiesY = (height - 450) / 40;
        enemies = new ArrayList<>();
        timers = new ArrayList<>();
        for(int x = 0; x < enemiesX; x++){
            enemies.add(new ArrayList<>());
            timers.add(new Timer(0));
            for(int y = 0; y < enemiesY; y++){
                enemies.get(x).add(new Enemy(150 + x * 60, y * 40, 50, 35, 1));
            }
        }
        Enemy.setDirection(0);
        // to move enemies to the right
        timer = new Timer(3000);
        eat = new SoundClip("/sounds/munch.wav");
    }
    
    /**
     * Checks if the block is empty
     * @return true if empty
     */
    public boolean isEmpty(){
        for(ArrayList<Enemy> column : enemies){
            if(!column.isEmpty()){
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if the enemies are on the ground
     * @return true if an enemy is on the ground
     */
    public boolean isOnGround(){
        for(ArrayList<Enemy> column : enemies){
            if(column.size() > 0 && column.get(column.size() - 1).getY() >= 500){
                return true;
            }
        }
        return false;
    }

    /**
     * Returns timer
     * @return 
     */
    public Timer getTimer() {
        return timer;
    }
    
    /**
     * Makes the enemies shoot
     * @return arraylist of bullets
     */
    public ArrayList<Bullet> shoot(){
        ArrayList<Bullet> bullets = new ArrayList<>();
        // Checks for the last enemy of each column
        for(int i = 0; i < enemies.size(); i++){
            ArrayList<Enemy> column = enemies.get(i);
            // Checks if the column is available to shoot
            if(!column.isEmpty() && timers.get(i).isFinished()){
                Bullet bullet = column.get(column.size() - 1).createBullet();
                if(bullet != null){
                    // stops this column from shooting for 5 seconds
                    timers.set(i, new Timer(5000));
                    bullets.add(bullet);
                }
            }
        }
        return bullets;
    }
    
    /**
     * Checks if the enemies crashed with a bullet
     * @param bullet
     * @return true if the bullet crashed
     */
    public boolean hasCrashed(Bullet bullet){
        for(ArrayList<Enemy> column : enemies){
            for(Enemy enemy : column){
                if(enemy.intersects(bullet)){
                    column.remove(enemy);
                    eat.play();
                    return true;
                }
            }
        }
        return false;
    }
    
    public void crash(ArrayList<ShieldPiece> shield) {
        for(ShieldPiece piece : shield) {
            for(ArrayList<Enemy> column : enemies) {
                for(Enemy enemy : column) {
                    if(piece.intersects(enemy)) {
                        shield.clear();
                        return;
                    }
                }
            }
        }
    }

    public void tick() {
        for (ArrayList<Enemy> column : enemies) {
            for (Enemy enemy : column) {
                enemy.tick();
            }
        }
        timer.tick();
        // Updates direction
        if (timer.isFinished()) {
            switch (Enemy.getDirection()) {
                case 0:
                    timer = new Timer(500);
                    Enemy.setDirection(2);
                    break;
                case 1:
                    timer = new Timer(500);
                    Enemy.setDirection(3);
                    break;
                case 2:
                    timer = new Timer(5800);
                    Enemy.setDirection(1);
                    break;
                default:
                    timer = new Timer(5800);
                    Enemy.setDirection(0);
            }
        }
        // ticks every shooting timer
        for(Timer timer2 : timers){
            timer2.tick();
        }
    }

    public void render(Graphics g) {
        for (ArrayList<Enemy> column : enemies) {
            for (Enemy enemy : column) {
                enemy.render(g);
            }
        }
    }
    
    /**
     * Saves the enemyblock with all its enemies
     * @param writer file writer
     */
    public void save(PrintWriter writer){
        writer.println("" + enemies.size());
        for(ArrayList<Enemy> column : enemies){
            writer.println("" + column.size());
            for(Enemy enemy : column){
                enemy.save(writer);
            }
        }
        writer.println("" + timer.getTimer());
        writer.println("" + timer.getTime());
        writer.println("" + Enemy.getDirection());
    }
    
    /**
     * Loads enemyblock with all its enemies
     * @param reader file to read
     * @throws IOException 
     */
    public void load(BufferedReader reader) throws IOException{
        enemies.clear();
        int x = Integer.parseInt(reader.readLine());
        for(int i = 0; i < x; i++){
            enemies.add(new ArrayList<Enemy>());
            int y = Integer.parseInt(reader.readLine());
            for(int j = 0; j < y; j++){
                String tokensS[] = reader.readLine().split(",");
                int tokens[] = new int[tokensS.length];
                for(int k = 0; k < tokensS.length; k++){
                    tokens[k] = Integer.parseInt(tokensS[k]);
                }
                enemies.get(i).add(Enemy.load(tokens));
            }
        }
        long aux1 = Long.parseLong(reader.readLine());
        long aux2 = Long.parseLong(reader.readLine()); 
        timer = new Timer(aux2, aux1);
        
        Enemy.setDirection(Integer.parseInt(reader.readLine()));
    }
}
