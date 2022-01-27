public class Food {
    private Position posX;
    private Position posY;

    public Food(Position posX, Position posY) {
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
