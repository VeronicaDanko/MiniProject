import com.googlecode.lanterna.terminal.Terminal;

import java.util.Random;

public class Food extends Position{
    final char heart = '\u2665';

    public Food(int x, int y) {
        super(x, y);
    }

    public char getHeart() {
        return heart;
    }

    public Food generateFood(Terminal terminal, Snake snake) throws Exception {
        Random random = new Random();
        int foodX;
        int foodY;
        do {
            boolean randomizer;
            foodY = random.nextInt(6, 29);
            foodX = random.nextInt(1, 57);
            for(Position snakePos : snake.getSnakeBody()) {
                if (snakePos.getX() == foodX && snakePos.getY() == foodY) {
                    randomizer = false;
                } else {
                    randomizer = true;
                }
            }

        } while (false);

        this.setX(foodX);
        this.setY(foodY);
        terminal.setCursorPosition(foodX, foodY);
        terminal.putCharacter(this.getHeart());
        terminal.flush();
        return this;
    }
}