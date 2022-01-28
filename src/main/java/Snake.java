import java.util.ArrayList;
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

    public List<Position> initializeSnakeBody() {
        List<Position> snakebody = new ArrayList();
        snakebody.add();
        return snakeBody;
    }
}
