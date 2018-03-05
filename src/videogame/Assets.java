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
    public static BufferedImage playerIdleLeft[];     // to store the player image
    public static BufferedImage playerIdleRight[];     // to store the player image
    public static BufferedImage playerMoveRight[];
    public static BufferedImage playerMoveLeft[];
    public static BufferedImage playerAttackRight[];
    public static BufferedImage playerAttackLeft[];
    public static BufferedImage playerLoseRight[];
    public static BufferedImage playerLoseLeft[];
    public static BufferedImage playerWin[];

    /**
     * initializing the images of the game
     */
    public static void init() {
        playerIdleLeft = new BufferedImage[4];
        playerIdleRight = new BufferedImage[4];
        playerMoveRight = new BufferedImage[4];
        playerMoveLeft = new BufferedImage[4];
        playerAttackRight = new BufferedImage[5];
        playerAttackLeft = new BufferedImage[5];
        playerLoseRight = new BufferedImage[3];
        playerLoseLeft = new BufferedImage[3];
        playerWin = new BufferedImage[20];
        SpriteSheet chefRight = new SpriteSheet(ImageLoader.loadImage("/images/chefRight.png"));
        SpriteSheet chefLeft = new SpriteSheet(ImageLoader.loadImage("/images/chefLeft.png"));
        SpriteSheet menu = new SpriteSheet(ImageLoader.loadImage("/images/menu.png"));
        background = ImageLoader.loadImage("/images/Background.jpg");
        playerIdleLeft[0] = chefLeft.crop(494, 7, 57, 50);
        playerIdleLeft[1] = chefLeft.crop(440, 7, 56, 49);
        playerIdleLeft[2] = chefLeft.crop(385, 7, 57, 54);
        playerIdleLeft[3] = chefLeft.crop(328, 7, 56, 51);
        playerIdleRight[0] = chefRight.crop(6, 7, 57, 50);
        playerIdleRight[1] = chefRight.crop(63, 7, 56, 49);
        playerIdleRight[2] = chefRight.crop(118, 7, 53, 51);
        playerIdleRight[3] = chefRight.crop(172, 7, 56, 48);
        playerMoveRight[0] = chefRight.crop(7, 105, 47, 51);
        playerMoveRight[1] = chefRight.crop(54, 105, 46, 52);
        playerMoveRight[2] = chefRight.crop(102, 105, 60, 50);
        playerMoveRight[3] = chefRight.crop(165, 105, 44, 52);
        playerMoveLeft[0] = chefLeft.crop(503, 105, 48, 50);
        playerMoveLeft[1] = chefLeft.crop(455, 105, 47, 50);
        playerMoveLeft[2] = chefLeft.crop(392, 105, 64, 53);
        playerMoveLeft[3] = chefLeft.crop(346, 105, 47, 52);
        playerAttackRight[0] = chefRight.crop(6, 277, 42, 47);
        playerAttackRight[1] = chefRight.crop(48, 254, 55, 68);
        playerAttackRight[2] = chefRight.crop(106, 254, 46, 68);
        playerAttackRight[3] = chefRight.crop(156, 254, 32, 68);
        playerAttackRight[4] = chefRight.crop(199, 277, 41, 45);
        playerAttackLeft[0] = chefLeft.crop(511, 277, 40, 50);
        playerAttackLeft[1] = chefLeft.crop(452, 254, 57, 69);
        playerAttackLeft[2] = chefLeft.crop(404, 254, 48, 73);
        playerAttackLeft[3] = chefLeft.crop(365, 254, 36, 69);
        playerAttackLeft[4] = chefLeft.crop(316, 277, 42, 49);
        playerLoseRight[0] = chefRight.crop(58, 160, 42, 50);
        playerLoseRight[1] = chefRight.crop(100, 160, 47, 47);
        playerLoseRight[2] = chefRight.crop(219, 171, 56, 38);
        playerLoseLeft[0] = chefLeft.crop(456, 160, 46, 49);
        playerLoseLeft[1] = chefLeft.crop(408, 160, 47, 47);
        playerLoseLeft[2] = chefLeft.crop(281, 171, 56, 35);
        playerWin[0] = chefRight.crop(6, 325, 34, 46);
        playerWin[1] = chefRight.crop(44, 325, 51, 45);
        playerWin[2] = chefRight.crop(96, 325, 62, 47);
        playerWin[3] = chefRight.crop(163, 325, 33, 46);
        playerWin[4] = chefRight.crop(197, 325, 52, 45);
        playerWin[5] = chefRight.crop(251, 325, 65, 48);
        playerWin[6] = chefRight.crop(318, 325, 34, 47);
        playerWin[7] = chefRight.crop(353, 325, 61, 47);
        playerWin[8] = chefRight.crop(412, 325, 71, 48);
        playerWin[9] = chefRight.crop(483, 325, 68, 50);
        playerWin[10] = chefRight.crop(318, 325, 34, 47);
        playerWin[11] = chefRight.crop(39, 374, 36, 46);
        playerWin[12] = chefRight.crop(79, 370, 32, 49);
        playerWin[13] = chefRight.crop(113, 370, 36, 49);
        playerWin[14] = chefRight.crop(79, 370, 32, 49);
        playerWin[15] = chefRight.crop(113, 370, 36, 49);
        playerWin[16] = chefRight.crop(79, 370, 32, 49);
        playerWin[17] = chefRight.crop(113, 370, 36, 49);
        playerWin[18] = chefRight.crop(150, 370, 30, 48);
        playerWin[19] = chefRight.crop(179, 370, 40, 48);
    }
    
}
