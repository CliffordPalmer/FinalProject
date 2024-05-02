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
    }

    public void Draw(Graphics g){

    }
}
