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
    public static BufferedImage pause;      // to store pause image
    public static BufferedImage lives;      // to store lives image
    public static BufferedImage playerIdleLeft[];     // to store the player image
    public static BufferedImage playerIdleRight[];     // to store the player image
    public static BufferedImage playerMoveRight[];
    public static BufferedImage playerMoveLeft[];
    public static BufferedImage playerAttackRight[];
    public static BufferedImage playerAttackLeft[];
    public static BufferedImage playerLoseRight[];
    public static BufferedImage playerLoseLeft[];
    public static BufferedImage playerWin[];
    public static BufferedImage menu[];
    public static BufferedImage clientWalkLeft[];
    public static BufferedImage clientWalkRight[];
    public static BufferedImage clientWalkDown[];
    public static BufferedImage clientWalkUp[];

    /**
     * initializing the images of the game
     */
    public static void init() {
        playerIdleLeft = new BufferedImage[4];
        playerIdleRight = new BufferedImage[4];
        playerMoveRight = new BufferedImage[4];
        playerMoveLeft = new BufferedImage[4];
        playerAttackRight = new BufferedImage[3];
        playerAttackLeft = new BufferedImage[3];
        playerLoseRight = new BufferedImage[3];
        playerLoseLeft = new BufferedImage[3];
        playerWin = new BufferedImage[20];
        clientWalkLeft = new BufferedImage[4];
        clientWalkRight = new BufferedImage[4];
        clientWalkDown = new BufferedImage[4];
        clientWalkUp = new BufferedImage[4];
        SpriteSheet chefRight = new SpriteSheet(ImageLoader.loadImage("/images/chefRight.png"));
        SpriteSheet chefLeft = new SpriteSheet(ImageLoader.loadImage("/images/chefLeft.png"));
        SpriteSheet menuSheet = new SpriteSheet(ImageLoader.loadImage("/images/menu.png"));
        SpriteSheet client = new SpriteSheet(ImageLoader.loadImage("/images/client.png"));
        background = ImageLoader.loadImage("/images/Background.jpg");
        pause = ImageLoader.loadImage("/images/pause.png");
        lives = ImageLoader.loadImage("/images/lives.png");
        playerIdleLeft[0] = chefLeft.crop(494, 7, 57, 50);
        playerIdleLeft[1] = chefLeft.crop(440, 7, 56, 50);
        playerIdleLeft[2] = chefLeft.crop(385, 7, 57, 50);
        playerIdleLeft[3] = chefLeft.crop(328, 7, 56, 50);
        playerIdleRight[0] = chefRight.crop(6, 7, 57, 50);
        playerIdleRight[1] = chefRight.crop(63, 7, 56, 50);
        playerIdleRight[2] = chefRight.crop(118, 7, 53, 50);
        playerIdleRight[3] = chefRight.crop(172, 7, 56, 50);
        playerMoveRight[0] = chefRight.crop(7, 105, 47, 51);
        playerMoveRight[1] = chefRight.crop(54, 105, 46, 52);
        playerMoveRight[2] = chefRight.crop(102, 105, 60, 50);
        playerMoveRight[3] = chefRight.crop(165, 105, 44, 52);
        playerMoveLeft[0] = chefLeft.crop(503, 105, 48, 50);
        playerMoveLeft[1] = chefLeft.crop(455, 105, 47, 50);
        playerMoveLeft[2] = chefLeft.crop(392, 105, 64, 53);
        playerMoveLeft[3] = chefLeft.crop(346, 105, 47, 52);
        playerAttackRight[0] = chefRight.crop(48, 254, 55, 68);
        playerAttackRight[1] = chefRight.crop(106, 254, 46, 68);
        playerAttackRight[2] = chefRight.crop(156, 254, 32, 68);
        playerAttackLeft[0] = chefLeft.crop(452, 254, 57, 68);
        playerAttackLeft[1] = chefLeft.crop(404, 254, 48, 68);
        playerAttackLeft[2] = chefLeft.crop(365, 254, 36, 68);
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
        menu = new BufferedImage[116];
        for(int i=0; i<8; i++) {
            for(int j=0; j<14; j++) {
                menu[i*14+j] = menuSheet.crop(34*j, 34*i, 34, 34);
            }
        }
        for(int i=0; i<4; i++) {
            menu[112+i] = menuSheet.crop(34*14, 34*(4+i), 34, 34);
        }
        for(int i = 0; i < 4; i++){
            clientWalkDown[i] = client.crop(i * 32, 10, 32, 40);
        }
        for(int i = 0; i < 4; i++){
            clientWalkLeft[i] = client.crop(i * 32, 60, 32, 40);
        }
        for(int i = 0; i < 4; i++){
            clientWalkRight[i] = client.crop(i * 32, 110, 32, 40);
        }
        for(int i = 0; i < 4; i++){
            clientWalkUp[i] = client.crop(i * 32, 160, 32, 40);
        }
    }
    
}
