import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.sun.jdi.Value;

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
        Food food = new Food(0,0);
        snakeBody2.add(0, new Position(15,15));
        snakeBody2.add(1, new Position(15, 16));
        snakeBody2.add(2, new Position(15, 17));
        Snake userSnake = new Snake(15, 15, snakeBody2);
        food.generateFood(terminal, userSnake);
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

                        if (!isSnakeAlive(userSnake, walls)) {
                            continueReadingInput = false;
                            gameEnd(terminal);
                            break;
                        }
                    }
                }

                Thread.sleep(1); // might throw InterruptedException
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
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowUp:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX(), firstPosition.getY()-1));
                if(snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowLeft:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX()-1, firstPosition.getY()));
                if(snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowRight:
                snake.getSnakeBody().add(0,new Position(firstPosition.getX()+1, firstPosition.getY()));
                if(snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
        }
        terminal.setCursorPosition(oldPosition.getX(), oldPosition.getY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(snake.getFirst().getX(), snake.getFirst().getY());
        terminal.putCharacter('S');


        String message = "SCORE";
        for (int i = 0; i < message.length(); i++) {
            terminal.setCursorPosition(i+3, 2);
            terminal.putCharacter(message.charAt(i));
        }
        terminal.flush();

        int score = snake.getSnakeBody().size() * 100 - 300;
        String s = String.valueOf(score);
        for (int i = 0; i <= s.length() - 1; i++) {
            terminal.setCursorPosition(i + 3,3);
            terminal.putCharacter(s.charAt(i));
        }
        terminal.flush();
    }

    private static boolean isSnakeAlive(Snake snake, List<Walls> walls) {
        for (Walls wall : walls) {
            if(wall.getX() == snake.getFirst().getX() && wall.getY() == snake.getFirst().getY()) {
                return false;
            }
        }
        for (int i = 1; i < (snake.getSnakeBody().size() - 1); i++) {
            if (snake.getFirst().getX() == snake.getSnakeBody().get(i).getX() && snake.getFirst().getY() == snake.getSnakeBody().get(i).getY()) {
                return false;
            }
        }
        if (snake.getFirst().getX() == snake.getPrevious().getX() && snake.getFirst().getY() == snake.getPrevious().getY()) {
            return false;
        }
        return true;
    }

    private static void gameEnd(Terminal terminal) throws Exception {
        terminal.setCursorPosition(20, 20);
        terminal.putCharacter('E');
        terminal.flush();
    }

    public static boolean snakeEatApple(Food food, Snake snake) {
        if(food.getX() == snake.getFirst().getX() && food.getY() == snake.getFirst().getY()) {
            return true;
        } else {
            return false;
        }
    }
}
