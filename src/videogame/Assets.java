/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {
    public static BufferedImage background; // to store background image
    public static BufferedImage player;     // to store the player image

    /**
     * initializing the images of the game
     */
    public static void init() {
        SpriteSheet chefRight = new SpriteSheet(ImageLoader.loadImage("/images/chefRight.png"));
        SpriteSheet chefLeft = new SpriteSheet(ImageLoader.loadImage("/images/chefLeft.png"));
        SpriteSheet menu = new SpriteSheet(ImageLoader.loadImage("/images/menu.png"));
        background = ImageLoader.loadImage("/images/Background.jpg");
        player = ImageLoader.loadImage("/images/mario.png");
    }
    
}
