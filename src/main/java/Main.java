import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(58, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        //Create snake
        List<Position> snakeBody2 = new ArrayList<>();
        snakeBody2.add(new Position(15,15));
        snakeBody2.add(new Position(15, 16));
        snakeBody2.add(new Position(15, 17));
        Snake userSnake = new Snake(15, 15, snakeBody2);
        for (Position snakePosition : userSnake.getSnakeBody()){
            terminal.setCursorPosition(snakePosition.getX(), snakePosition.getY());
            terminal.putCharacter('S');
        }
        terminal.flush();


        // Setup gameboard
        List<Walls> walls = new ArrayList<>();
        for (int i = 0; i < 58; i++) {
            walls.add(new Walls(i, 5));
        }
        for (int i = 0; i < 58; i++) {
            walls.add(new Walls(i, 30));
        }
        for (int i = 5; i < 30; i++) {
            walls.add(new Walls(0, i));
        }
        for (int i = 5; i < 30; i++) {
            walls.add(new Walls(58, i));
        }
        for (Walls wall : walls) {
            terminal.setCursorPosition(wall.getX(), wall.getY());
            terminal.putCharacter(wall.getBLOCK());
            terminal.flush();
        }

        Food food = generateFood(terminal);

        //mat får inte dyka upp på orm - hur löser vi detta?
        //äpple ska försvinna när äpplet äts av orm
        //äpple ska dyka upp igen när det ätits upp
        //orm ska växa när äpplet äts





        KeyStroke latestKeyStroke = null;

        boolean continueReadingInput = true;
        while (continueReadingInput) {

            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 100 == 0) {
                    if (latestKeyStroke != null) {
                        handlePlayer(userSnake, latestKeyStroke, terminal, food);
                    }
                }

                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();


            } while (keyStroke == null);
            latestKeyStroke = keyStroke;


        }

    }
    private static void handlePlayer(Snake snake, KeyStroke keyStroke,Terminal terminal, Food food) throws Exception {
        Position oldPosition = snake.getLast();
        Position firstPosition = snake.getFirst();
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX(), firstPosition.getY()+1));
                if(snakeEatApple(food, snake)) {
                    generateFood(terminal);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowUp:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX(), firstPosition.getY()-1));
                if(snakeEatApple(food, snake)) {
                    generateFood(terminal);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowLeft:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX()-1, firstPosition.getY()));
                if(snakeEatApple(food, snake)) {
                    generateFood(terminal);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowRight:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX()+1, firstPosition.getY()));
                if(snakeEatApple(food, snake)) {
                    generateFood(terminal);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
        }
        terminal.setCursorPosition(oldPosition.getX(), oldPosition.getY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(snake.getFirst().getX(), snake.getFirst().getY());
        terminal.putCharacter('S');

        terminal.flush();
    }

    private static boolean isSnakeAlive(Snake snake, List<Walls> walls) {
        for (Walls wall : walls) {
            if(wall.getX() == snake.getFirst().getX() && wall.getY() == snake.getFirst().getY()) {
                return false;
            }
        }
        return true;
    }
    public static Food generateFood(Terminal terminal) throws Exception {
        Random random = new Random();
        int foodY = random.nextInt(6, 29);
        int foodX = random.nextInt(1, 57);
        Food food = new Food(foodX, foodY);
        terminal.setCursorPosition(food.getX(), food.getY());
        terminal.putCharacter(food.getHeart());
        terminal.flush();
        return food;
    }
    public static boolean snakeEatApple(Food food, Snake snake) {
        if(food.getX() == snake.getFirst().getX() && food.getY() == snake.getFirst().getY()) {
            return true;
        } else {
            return false;
        }
    }
}
