import java.util.ArrayList;
import java.util.List;

public class Snake extends Position {
    private List<Character> snakeBody;

    public Snake(int x, int y, List<Character> snakeBody) {
        super(x, y);
        this.snakeBody = snakeBody;
    }

    public List<Character> getSnakeBody() {
        return snakeBody;
    }

    public List<Character> initializeSnakeBody() {
        this.snakeBody.add((char) 3);
        return snakeBody;
    }
}
