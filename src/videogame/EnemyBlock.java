/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Ali
 */
public class EnemyBlock {
    private ArrayList<ArrayList<Enemy>> enemies;
    private Timer timer;
    private ArrayList<Timer> timers;
    
    public EnemyBlock(int width, int height){
        int enemiesX = (width - 300) / 60;
        int enemiesY = (height - 450) / 40;
        enemies = new ArrayList<>();
        timers = new ArrayList<>();
        for(int x = 0; x < enemiesX; x++){
            enemies.add(new ArrayList<>());
            timers.add(new Timer(5000));
            for(int y = 0; y < enemiesY; y++){
                enemies.get(x).add(new Enemy(150 + x * 60, y * 40, 50, 35, 1));
            }
        }
        Enemy.setDirection(0);
        timer = new Timer(3000);
    }
    
    public ArrayList<Bullet> shoot(){
        ArrayList<Bullet> bullets = new ArrayList<>();
        for(int i = 0; i < enemies.size(); i++){
            ArrayList<Enemy> column = enemies.get(i);
            if(!column.isEmpty() && timers.get(i).isFinished()){
                Bullet bullet = column.get(column.size() - 1).createBullet();
                if(bullet != null){
                    timers.set(i, new Timer(5000));
                    bullets.add(bullet);
                }
            }
        }
        return bullets;
    }
    
    public boolean hasCrashed(Bullet bullet){
        for(ArrayList<Enemy> column : enemies){
            for(Enemy enemy : column){
                if(enemy.intersects(bullet)){
                    column.remove(enemy);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void tick(){
        for(ArrayList<Enemy> column : enemies){
            for(Enemy enemy : column){
                enemy.tick();
            }
        }
        timer.tick();
        if(timer.isFinished()){
            if(Enemy.getDirection() == 0){
                timer = new Timer(500);
                Enemy.setDirection(2);
            } else if(Enemy.getDirection() == 1){
                timer = new Timer(500);
                Enemy.setDirection(3);
            } else if(Enemy.getDirection() == 2){
                timer = new Timer(5800);
                Enemy.setDirection(1);
            } else {
                timer = new Timer(5800);
                Enemy.setDirection(0);
            }
        }
        for(Timer timer2 : timers){
            timer2.tick();
        }
    }
    
    public void render(Graphics g){
        for(ArrayList<Enemy> column : enemies){
            for(Enemy enemy : column){
                enemy.render(g);
            }
        }
    }
}
