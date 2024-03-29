import java.util.List;

public class Snake extends Position {
    private List<Position> snakeBody;

    public Snake(int x, int y, List<Position> snakeBody) {
        super(x, y);
        this.snakeBody = snakeBody;
    }

    public List<Position> getSnakeBody() {
        return snakeBody;
    }

    public Position getFirst() {
        Position firstPosition = snakeBody.get(0);
        return firstPosition;
    }

    public Position getPrevious() {
        Position previousPosition = snakeBody.get(1);
        return previousPosition;
    }

    public Position getLast() {
        int lastIndex = snakeBody.size() - 1;
        Position lastPosition = snakeBody.get(lastIndex);
        return lastPosition;
    }
}
