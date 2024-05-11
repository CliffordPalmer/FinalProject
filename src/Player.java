import javax.swing.*;
import java.awt.*;
/**
 * Player class for Stick Figure Boxing
 * @author: Clifford Palmer
 * @version: 05/10/2024
 */
public class Player {
    private String name;
    private int health;
    private int x, y;
    private int costume;
    private int playerNum;
    private boolean isBlocking;
    private boolean isDead;
    private boolean canHit;
    private static final int MAX_DAMAGE = 12;
    private static final int MIN_DAMAGE = 5;
    private static final int HEAD_RADIUS = 75/2;
    private static final int NORMAL_COSTUME = 1;
    private static final int HIT_COSTUME = 2;
    private static final int BLOCK_COSTUME = 3;
    private int hitPercent;
    private int blockPercent;
    private GameView window;

    public Player(String name, int playerNum){
        // Initialize instance variables
        this.name = name;
        this.playerNum = playerNum;
        health = 100;
        costume = NORMAL_COSTUME;
        isBlocking = false;
        isDead = false;
        canHit = true;
        hitPercent = 100;
        blockPercent = 100;
        y = 300;
        // Set x position based on player number
        if(playerNum == 1){
            x = 100;
        }
        else if(playerNum == 2){
            x = 700;
        }
    }

    // Gives the front end to the player
    public void addWindow(GameView window){
        this.window = window;
    }

    // Method for player movement
    public void changeX(int dx){
        // Make sure player doesn't move off the left of the screen
        if(x + dx <= HEAD_RADIUS){
            x = HEAD_RADIUS;
        }
        // Make sure player doesn't move off the right of the screen
        else if(x + dx >= window.getWidth() - HEAD_RADIUS){
            x = window.getWidth() - HEAD_RADIUS;
        }
        // Change the player's x position by dx
        else {
            x += dx;
        }
    }

    // Method to deal damage to a player
    public void damage(){
        // If the player is blocking, no damage is dealt
        if(isBlocking){
            return;
        }
        // Randomize damage
        int damage = (int)(Math.random()*(MAX_DAMAGE-MIN_DAMAGE)) + MIN_DAMAGE;
        // If the damage exceeds the health of the player, the player dies
        if(damage >= health){
            isDead = true;
        }
        // Otherwise, deal the damage
        health -= damage;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isCanHit() {
        return canHit;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isBlocking() {
        return isBlocking;
    }

    // Hit lockout
    public void hit(){
        // Check if the player just hit or blocked
        if(canHit && !isBlocking) {
            costume = HIT_COSTUME;
            canHit = false;
            isBlocking = true;
            hitPercent = 0;
            // Start a new thread to lockout the player from hitting until 1 second later
            new Thread(() -> {
                try {
                    // Increment hitPercent by 10 every 100 ms
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(100);
                        hitPercent += 10;
                        window.repaint();
                    }
                    // End the lockout
                    canHit = true;
                    isBlocking = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public void block(){
        // Check if a player just hit or blocked
        if(!isBlocking && canHit) {
            costume = BLOCK_COSTUME;
            canHit = false;
            isBlocking = true;
            blockPercent = 0;
            // Start a new thread to lockout the player from blocking until 1 second later
            new Thread(() -> {
                try {
                    // increment blockPercent by 10 every 100 ms
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(100);
                        blockPercent += 10;
                        window.repaint();
                    }
                    // End the lockout
                    canHit = true;
                    isBlocking = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void normal(){
        costume = NORMAL_COSTUME;
    }

    // Draw function for each player
    public void draw(Graphics g){
        // Variables for graphics positions
        int INFO_BAR_WIDTH = 100;
        int INFO_BAR_HEIGHT = 20;
        int INFO_START_X = 20;
        int INFO_START_Y = 115;
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        // Set color of player based on player number
        if(playerNum == 1){
            g.setColor(Color.blue);
        }
        else if(playerNum == 2){
            g.setColor(Color.red);
        }
        // Draw the head, torso, and legs of each player
        g.drawOval(x - HEAD_RADIUS, y - HEAD_RADIUS, 2*HEAD_RADIUS, 2*HEAD_RADIUS);
        g.drawLine(x, y + HEAD_RADIUS, x, y + HEAD_RADIUS + 100);
        g.drawLine(x, y + HEAD_RADIUS + 100, x - HEAD_RADIUS, y + 175);
        g.drawLine(x, y + HEAD_RADIUS + 100, x + HEAD_RADIUS, y + 175);
        // Draw Player 1 specifics
        if(playerNum == 1) {
            // Draw player one info bars
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.drawString("P1 Heatlh:", INFO_START_X, INFO_START_Y);
            g.drawString("Hit Charge:", INFO_START_X, INFO_START_Y + 40);
            g.drawString("Block Charge:", INFO_START_X, INFO_START_Y + 80);
            g.drawRect(INFO_START_X + 100, INFO_START_Y - 15, INFO_BAR_WIDTH, INFO_BAR_HEIGHT);
            g.drawRect(INFO_START_X + 100, INFO_START_Y + 25, INFO_BAR_WIDTH, INFO_BAR_HEIGHT);
            g.drawRect(INFO_START_X + 100, INFO_START_Y + 65, INFO_BAR_WIDTH, INFO_BAR_HEIGHT);
            g.setColor(Color.RED);
            g.fillRect(INFO_START_X + 100, INFO_START_Y - 15, health, INFO_BAR_HEIGHT);
            g.setColor(Color.yellow);
            g.fillRect(INFO_START_X + 100, INFO_START_Y + 25, hitPercent, INFO_BAR_HEIGHT);
            g.setColor(Color.cyan);
            g.fillRect(INFO_START_X + 100, INFO_START_Y + 65, blockPercent, INFO_BAR_HEIGHT);
            g.setColor(Color.blue);
            // Normal arms
            if(costume == NORMAL_COSTUME) {
                g.drawLine(x, y + HEAD_RADIUS + 30, x + HEAD_RADIUS, y + HEAD_RADIUS + 60);
                g.drawLine(x + HEAD_RADIUS, y + HEAD_RADIUS + 60, x + 50, y + 30);
                g.drawLine(x, y + HEAD_RADIUS + 30, x + 30, y + 85);
                g.drawLine(x + 30, y + 85, x + 50, y + 20);
            }
            // Arms while hitting
            if(costume == HIT_COSTUME){
                g.drawLine(x, y + HEAD_RADIUS + 30, x + HEAD_RADIUS, y + 65);
                g.drawLine(x + HEAD_RADIUS, y + 65, x + 55, y + 25);
                g.drawLine(x, y + HEAD_RADIUS + 30, x + 30, y + 55);
                g.drawLine(x + 30, y + 55, x + 90, y + 10);
            }
            // Arms while blocking
            if(costume == BLOCK_COSTUME){
                g.drawLine(x, y + HEAD_RADIUS + 30, x + HEAD_RADIUS, y + HEAD_RADIUS + 10);
                g.drawLine(x + HEAD_RADIUS, y + HEAD_RADIUS + 10, x + 50, y);
                g.drawLine(x, y + HEAD_RADIUS + 30, x + 30, y + 65);
                g.drawLine(x + 30, y + 65, x + 40, y);
            }
        }
        // Player two specifics
        else if(playerNum == 2) {
            // Player two info bars
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.drawString("P2 Heatlh:", 580, 115);
            g.drawString("Hit Charge:", 580, 155);
            g.drawString("Block Charge:", 580, 195);
            g.setColor(Color.black);
            g.drawRect(680, 100, INFO_BAR_WIDTH, INFO_BAR_HEIGHT);
            g.drawRect(680, 140, INFO_BAR_WIDTH, INFO_BAR_HEIGHT);
            g.drawRect(680, 180, INFO_BAR_WIDTH, INFO_BAR_HEIGHT);
            g.setColor(Color.RED);
            g.fillRect(680, 100, health, INFO_BAR_HEIGHT);
            g.setColor(Color.yellow);
            g.fillRect(680, 140, hitPercent, INFO_BAR_HEIGHT);
            g.setColor(Color.cyan);
            g.fillRect(680, 180, blockPercent, INFO_BAR_HEIGHT);
            g.setColor(Color.red);
            // Normal arms
            if(costume == NORMAL_COSTUME) {
                g.drawLine(x, y + HEAD_RADIUS + 30, x - HEAD_RADIUS, y + HEAD_RADIUS + 60);
                g.drawLine(x - HEAD_RADIUS, y + HEAD_RADIUS + 60, x - 50, y + 30);
                g.drawLine(x, y + HEAD_RADIUS + 30, x - 30, y + 85);
                g.drawLine(x - 30, y + 85, x - 50, y + 20);
            }
            // Arms while hitting
            if(costume == HIT_COSTUME){
                g.drawLine(x, y + HEAD_RADIUS + 30, x - HEAD_RADIUS, y + 65);
                g.drawLine(x - HEAD_RADIUS, y + 65, x - 55, y + 25);
                g.drawLine(x, y + HEAD_RADIUS+ 30, x - 30, y + 55);
                g.drawLine(x - 30, y + 55, x - 90, y + 10);
            }
            // Arms while blocking
            if(costume == BLOCK_COSTUME){
                g.drawLine(x, y + HEAD_RADIUS + 30, x - 75 / 2, y + HEAD_RADIUS + 10);
                g.drawLine(x - HEAD_RADIUS, y + HEAD_RADIUS + 10, x - 50, y);
                g.drawLine(x, y + HEAD_RADIUS + 30, x - 30, y + 65);
                g.drawLine(x - 30, y + 65, x - 40, y);
            }
        }

    }
}
