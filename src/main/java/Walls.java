public class Walls extends Position {
    private final char BLOCK;

    public Walls(int x, int y) {
        super(x, y);
        this.BLOCK = '\u2588';
    }

    public char getBLOCK() {
        return BLOCK;
    }
}
