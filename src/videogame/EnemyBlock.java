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
    
    public EnemyBlock(int width, int height){
        int enemiesX = (width - 300) / 60;
        int enemiesY = (height - 450) / 40;
        enemies = new ArrayList<>();
        for(int x = 0; x < enemiesX; x++){
            enemies.add(new ArrayList<>());
            for(int y = 0; y < enemiesY; y++){
                enemies.get(x).add(new Enemy(150 + x * 60, y * 40, 50, 35, 1));
            }
        }
        Enemy.setDirection(0);
        timer = new Timer(3000);
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
    }
    
    public void render(Graphics g){
        for(ArrayList<Enemy> column : enemies){
            for(Enemy enemy : column){
                enemy.render(g);
            }
        }
    }
}
