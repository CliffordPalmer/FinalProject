import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

/**
 * Game (BackEnd) class for Stick Figure Boxing
 * @author: Clifford Palmer
 * @version: 05/10/2024
 */

public class Game implements KeyListener{
    private GameView window;
    private static final int MAX_WIDTH = 800;		// Window size
    private static final int MAX_HEIGHT = 600;		// Window size
    private static final int TOP_OF_WINDOW = 22;
    private int gameState;
    private Player p1, p2;

    public Game(){
        // Add two players to the game
        p1 = new Player("Player 1", 1);
        p2 = new Player("Player 2", 2);
        // Set gameState to the fight state
        gameState = 0;
        // Create front end
        this.window = new GameView(MAX_WIDTH, MAX_HEIGHT, this);
        window.addKeyListener(this);
        // Share front end with Players
        p1.addWindow(window);
        p2.addWindow(window);
    }

    public static void main(String[] args) {
        // Start game
        Game g = new Game();
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }
    @Override
    public void keyTyped(KeyEvent e) {
        // Nothing required for this program.
        // However, as a KeyListener, this class must supply this method
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Nothing required for this program.
        // However, as a KeyListener, this class must supply this method
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // The keyCode lets you know which key was pressed
        switch (e.getKeyCode()) {
            // Player one movement keys
            case KeyEvent.VK_A:
                p1.changeX(-15);
                break;
            case KeyEvent.VK_D:
                p1.changeX(15);
                break;
            // Player two movement keys
            case KeyEvent.VK_LEFT:
                p2.changeX(-15);
                break;
            case KeyEvent.VK_RIGHT:
                p2.changeX(15);
                break;
            // Player one hit key
            case KeyEvent.VK_F:
                // If player two is in range
                if((p2.getX()- p1.getX()) > 0 && (p2.getX()- p1.getX()) < 125){
                    // If player one can hit, and player two is not blocking, deal damage
                    if(p1.isCanHit() && !p2.isBlocking()) {
                        p2.damage();
                    }
                }
                // If player one can hit, start the hit animation
                if(p1.isCanHit()) {
                    p1.hit();
                    resetArm(p1);
                }
                break;
            // Player two hit key. This case works identically to the one above
            case KeyEvent.VK_L:
                if((p2.getX()- p1.getX()) > 0 && (p2.getX()- p1.getX()) < 125){
                    if(p2.isCanHit() && !p1.isBlocking()) {
                        p1.damage();
                    }
                }
                if(p2.isCanHit()) {
                    p2.hit();
                    resetArm(p2);
                }
                break;
            // Player one block key
            case KeyEvent.VK_G:
                // If player one is not currently blocking run the block animation
                if(!p1.isBlocking()) {
                    p1.block();
                    resetArm(p1);
                }
                break;
            // Player two block key. This case works identically to the one above
            case KeyEvent.VK_K:
                if(!p2.isBlocking()) {
                    p2.block();
                    resetArm(p2);
                }
                break;
        }
        // Repaint window and check for a win
        window.repaint();
        checkWin();
        window.repaint();
    }

    // Method which starts a new thread to handle resetting any hit or block animation
    private void resetArm(Player player) {
        // Gradually reset arm angles after a delay
        new Thread(() -> {
            try {
                // Thread waits 400 ms
                Thread.sleep(400);
                // Reset player costume and repaint window
                player.normal();
                window.repaint();
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Check for a win
    public void checkWin(){
        // If p2 is dead, player one wins
        if(p2.isDead()){
            gameState = 1;
        }
        // If p2 is dead, player two wins
        if(p1.isDead()){
            gameState = 2;
        }
    }

    // Draw the game
    public void draw(Graphics g){
        // Draw the fight state
        if(gameState == 0){
            g.setColor(Color.black);
            g.drawLine(0, 476, window.getWidth(), 476);
        }
        // Draw the player one win state
        if(gameState == 1){
            g.setColor(Color.blue);
            g.fillRect(0, 0, window.getWidth(), window.getHeight());
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.PLAIN, 80));
            g.drawString("P1 Wins!", 250, 350);
        }
        // Draw the player two win state
        if(gameState == 2){
            g.setColor(Color.red);
            g.fillRect(0, 0, window.getWidth(), window.getHeight());
            g.setColor(Color.white);
            g.setFont(new Font("Serif", Font.PLAIN, 80));
            g.drawString("P2 Wins!", 250, 350);
        }
    }
}
