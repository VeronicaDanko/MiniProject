public class Walls {
    private Position wallPosX;
    private Position wallPosY;

    public Walls(Position wallPosX, Position wallPosY) {
        this.wallPosX = wallPosX;
        this.wallPosY = wallPosY;
    }

    public Position getWallPosX() {
        return wallPosX;
    }

    public Position getWallPosY() {
        return wallPosY;
    }
}
