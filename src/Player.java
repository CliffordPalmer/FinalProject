import javax.swing.*;
import java.awt.*;
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
    private int hitPercent;
    private int blockPercent;
    private GameView window;

    public Player(String name, int playerNum){
        this.name = name;
        this.playerNum = playerNum;
        health = 100;
        costume = 1;
        isBlocking = false;
        isDead = false;
        canHit = true;
        hitPercent = 100;
        blockPercent = 100;
        y = 300;
        if(playerNum == 1){
            x = 100;
        }
        else if(playerNum == 2){
            x = 700;
        }
    }

    public void addWindow(GameView window){
        this.window = window;
    }

    public void changeX(int dx){
        if(x + dx <= HEAD_RADIUS){
            x = HEAD_RADIUS;
        }
        else if(x + dx >= window.getWidth() - HEAD_RADIUS){
            x = window.getWidth() - HEAD_RADIUS;
        }
        else {
            x += dx;
        }
    }
    public void changeY(int dy){
        if(y + dy <= HEAD_RADIUS){
            y = HEAD_RADIUS;
        }
        else if(y + dy >= window.getWidth() - HEAD_RADIUS) {
            y = window.getWidth() - HEAD_RADIUS;
        }
        else {
            y += dy;
        }
    }

    public void damage(){
        if(isBlocking){
            return;
        }
        int damage = (int)(Math.random()*(MAX_DAMAGE-MIN_DAMAGE)) + MIN_DAMAGE;
        if(damage >= health){
            isDead = true;
        }
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

    public void hit(){
        if(canHit && !isBlocking) {
            costume = 2;
            canHit = false;
            hitPercent = 0;
            new Thread(() -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(100);
                        hitPercent += 10;
                        window.repaint();
                    }
                    canHit = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
    public void block(){
        if(!isBlocking && canHit) {
            costume = 3;
            isBlocking = true;
            blockPercent = 0;
            new Thread(() -> {
                try {
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(100);
                        blockPercent += 10;
                        window.repaint();
                    }
                    isBlocking = false;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    public void normal(){
        costume = 1;
    }

    public void draw(Graphics g){
        g.setFont(new Font("Serif", Font.PLAIN, 10));
        if(playerNum == 1){
            g.setColor(Color.blue);
        }
        else if(playerNum == 2){
            g.setColor(Color.red);
        }
        g.drawOval(x - HEAD_RADIUS, y - HEAD_RADIUS, 2*HEAD_RADIUS, 2*HEAD_RADIUS);
        g.drawLine(x, y + HEAD_RADIUS, x, y + HEAD_RADIUS + 100);
        g.drawLine(x, y + HEAD_RADIUS + 100, x - HEAD_RADIUS, y + 175);
        g.drawLine(x, y + HEAD_RADIUS + 100, x + HEAD_RADIUS, y + 175);
        // Draw Player 1 specifics
        if(playerNum == 1) {
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.drawString("P1 Heatlh:", 20, 115);
            g.drawString("Hit Charge:", 20, 155);
            g.drawString("Block Charge:", 20, 195);
            g.drawRect(120, 100, 100, 20);
            g.drawRect(120, 140, 100, 20);
            g.drawRect(120, 180, 100, 20);
            g.setColor(Color.RED);
            g.fillRect(120, 100, health, 20);
            g.setColor(Color.yellow);
            g.fillRect(120, 140, hitPercent, 20);
            g.setColor(Color.cyan);
            g.fillRect(120, 180, blockPercent, 20);
            g.setColor(Color.blue);
            if(costume == 1) {
                g.drawLine(x, y + HEAD_RADIUS + 30, x + HEAD_RADIUS, y + HEAD_RADIUS + 60);
                g.drawLine(x + HEAD_RADIUS, y + HEAD_RADIUS + 60, x + 50, y + 30);
                g.drawLine(x, y + HEAD_RADIUS + 30, x + 30, y + 85);
                g.drawLine(x + 30, y + 85, x + 50, y + 20);
            }
            if(costume == 2){
                g.drawLine(x, y + HEAD_RADIUS + 30, x + HEAD_RADIUS, y + 65);
                g.drawLine(x + HEAD_RADIUS, y + 65, x + 55, y + 25);
                g.drawLine(x, y + HEAD_RADIUS + 30, x + 30, y + 55);
                g.drawLine(x + 30, y + 55, x + 90, y + 10);
            }
            if(costume == 3){
                g.drawLine(x, y + HEAD_RADIUS + 30, x + HEAD_RADIUS, y + HEAD_RADIUS + 10);
                g.drawLine(x + HEAD_RADIUS, y + HEAD_RADIUS + 10, x + 50, y);
                g.drawLine(x, y + HEAD_RADIUS + 30, x + 30, y + 65);
                g.drawLine(x + 30, y + 65, x + 40, y);
            }
        }
        else if(playerNum == 2) {
            g.setFont(new Font("Serif", Font.PLAIN, 15));
            g.drawString("P2 Heatlh:", 580, 115);
            g.drawString("Hit Charge:", 580, 155);
            g.drawString("Block Charge:", 580, 195);
            g.setColor(Color.black);
            g.drawRect(680, 100, 100, 20);
            g.drawRect(680, 140, 100, 20);
            g.drawRect(680, 180, 100, 20);
            g.setColor(Color.RED);
            g.fillRect(680, 100, health, 20);
            g.setColor(Color.yellow);
            g.fillRect(680, 140, hitPercent, 20);
            g.setColor(Color.cyan);
            g.fillRect(680, 180, blockPercent, 20);
            g.setColor(Color.red);
            if(costume == 1) {
                g.drawLine(x, y + HEAD_RADIUS + 30, x - HEAD_RADIUS, y + HEAD_RADIUS + 60);
                g.drawLine(x - HEAD_RADIUS, y + HEAD_RADIUS + 60, x - 50, y + 30);
                g.drawLine(x, y + HEAD_RADIUS + 30, x - 30, y + 85);
                g.drawLine(x - 30, y + 85, x - 50, y + 20);
            }
            if(costume == 2){
                g.drawLine(x, y + HEAD_RADIUS + 30, x - HEAD_RADIUS, y + 65);
                g.drawLine(x - HEAD_RADIUS, y + 65, x - 55, y + 25);
                g.drawLine(x, y + HEAD_RADIUS+ 30, x - 30, y + 55);
                g.drawLine(x - 30, y + 55, x - 90, y + 10);
            }
            if(costume == 3){
                g.drawLine(x, y + HEAD_RADIUS + 30, x - 75 / 2, y + HEAD_RADIUS + 10);
                g.drawLine(x - HEAD_RADIUS, y + HEAD_RADIUS + 10, x - 50, y);
                g.drawLine(x, y + HEAD_RADIUS + 30, x - 30, y + 65);
                g.drawLine(x - 30, y + 65, x - 40, y);
            }
        }

    }
}
