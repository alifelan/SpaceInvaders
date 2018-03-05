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
    public static BufferedImage menu[];

    /**
     * initializing the images of the game
     */
    public static void init() {
        SpriteSheet chefRight = new SpriteSheet(ImageLoader.loadImage("/images/chefRight.png"));
        SpriteSheet chefLeft = new SpriteSheet(ImageLoader.loadImage("/images/chefLeft.png"));
        SpriteSheet menuSheet = new SpriteSheet(ImageLoader.loadImage("/images/menu.png"));
        background = ImageLoader.loadImage("/images/Background.jpg");
        menu = new BufferedImage[116];
        for(int i=0; i<8; i++) {
            for(int j=0; j<14; j++) {
                menu[i*8+j] = menuSheet.crop(34*j, 34*i, 34, 34);
            }
        }
        for(int i=0; i<4; i++) {
            menu[112+i] = menuSheet.crop(34*14, 34*(4+i), 34, 34);
            System.out.println(34*(4+i));
        }
    }
    
}
