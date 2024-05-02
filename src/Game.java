public class Game {
    private GameView window;
    private static final int MAX_WIDTH = 800;		// Window size
    private static final int MAX_HEIGHT = 600;		// Window size
    private static final int TOP_OF_WINDOW = 22;

    private Player p1, p2;

    public Game(){
        this.window = new GameView(MAX_WIDTH, MAX_HEIGHT, this);
    }

    public static void main(String[] args) {
        Game g = new Game();
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }
}
