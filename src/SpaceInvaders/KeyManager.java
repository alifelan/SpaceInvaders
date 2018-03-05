/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpaceInvaders;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author antoniomejorado
 */
public class KeyManager implements KeyListener {

    private final boolean releasedKeys[];
    private final boolean pressedKeys[];
    private final boolean releasedKeysQuery[];
    private static final int arraySize = 256;
    
    public KeyManager() {
        releasedKeys = new boolean[arraySize];
        pressedKeys = new boolean[arraySize];
        releasedKeysQuery = new boolean[arraySize];
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // set true if key was pressed
        pressedKeys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys[e.getKeyCode()] = false;
        releasedKeys[e.getKeyCode()] = true;
    }
    
    /**
     * to enable or disable moves on every tick
     */
    public void tick() {
        System.arraycopy(releasedKeys, 0, releasedKeysQuery, 0, arraySize);
        for(int i=0; i<releasedKeys.length; i++) {
            releasedKeys[i] = false;
        }
    }
    
    public boolean isPressed(int key) {
        return pressedKeys[key];
    }
    
    public boolean isReleased(int key) {
        return releasedKeysQuery[key];
    }
}
