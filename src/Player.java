import javax.swing.*;
import java.awt.*;
public class Player {
    private String name;
    private int health;
    private int x, y;
    private int costume;
    private int playerNum;
    private boolean isBlocking;

    public Player(String name, int playerNum){
        this.name = name;
        this.playerNum = playerNum;
        health = 100;
        costume = 1;
        isBlocking = false;

        y = 300;
        if(playerNum == 1){
            x = 100;
        }
        else if(playerNum == 2){
            x = 700;
        }
    }

    public void changeX(int dx){
        if(x + dx <= 75/2){
            x = 75/2;
        }
        else if(x + dx >= 800 - 75/2){
            x = 800 - 75/2;
        }
        else {
            x += dx;
        }
    }
    public void changeY(int dy){
        if(y + dy <= 75/2){
            y = 75/2;
        }
        else if(y + dy >= 800 - 75/2) {
            y = 800 - 75 / 2;
        }
        else {
            y += dy;
        }
    }

    public void draw(Graphics g){
        System.out.println(x);
        System.out.println(y);
        g.setColor(Color.black);
        g.drawOval(x - 75/2, y - 75/2, 75, 75);
        g.drawLine(x, y + 75/2, x, y + 75/2 + 100);
        g.drawLine(x, y + 75/2 + 100, x - 75/2, y + 75 + 100);
        g.drawLine(x, y + 75/2 + 100, x + 75/2, y + 75 + 100);
        if(playerNum == 1) {
            g.drawLine(x, y + 75 / 2 + 30, x + 75 / 2, y + 65);
            g.drawLine(x + 75 / 2, y + 65, x + 55, y + 25);
            g.drawLine(x, y + 75 / 2 + 30, x + 30, y + 55);
            g.drawLine(x + 30, y + 55, x + 55, y + 15);
        }
        else if(playerNum == 2) {
            g.drawLine(x, y + 75 / 2 + 30, x - 75 / 2, y + 65);
            g.drawLine(x - 75 / 2, y + 65, x - 55, y + 25);
            g.drawLine(x, y + 75 / 2 + 30, x - 30, y + 55);
            g.drawLine(x - 30, y + 55, x - 55, y + 15);
        }

    }
}
