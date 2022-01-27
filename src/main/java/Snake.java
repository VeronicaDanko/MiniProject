public class Snake {
    private Position posX;
    private Position posY;

    public Snake(Position posX, Position posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public Position getPosX() {
        return posX;
    }

    public Position getPosY() {
        return posY;
    }
}
