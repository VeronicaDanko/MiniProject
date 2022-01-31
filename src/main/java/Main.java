import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(58, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);

        //Create snake
        Snake snake = createSnake(terminal);

        //Create food
        Food food = new Food(0, 0);
        food.generateFood(terminal, snake);


        // Setup gameboard
        List<Walls> walls = new ArrayList<>();
        walls = setUpGameBoard(terminal);


        KeyStroke latestKeyStroke = null;

        boolean continueReadingInput = true;
        while (continueReadingInput) {

            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 100 == 0) {
                    if (latestKeyStroke != null) {
                        handlePlayer(snake, latestKeyStroke, terminal, food);

                        if (!isSnakeAlive(snake, walls)) {
                            Position gameOverPosition = new Position(20, 15);
                            printText(terminal, "G A M E   O V E R", gameOverPosition);
                            Position scorePosition = new Position(20, 18);

                            printText(terminal, "S C O R E: " + scoreCount(terminal, snake), scorePosition);
                            continueReadingInput = false;
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

    private static void handlePlayer(Snake snake, KeyStroke keyStroke, Terminal terminal, Food food) throws Exception {

        Position oldPosition = snake.getLast();
        Position firstPosition = snake.getFirst();
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                snake.getSnakeBody().add(0, new Position(firstPosition.getX(), firstPosition.getY() + 1));
                if (snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowUp:
                snake.getSnakeBody().add(0, new Position(firstPosition.getX(), firstPosition.getY() - 1));
                if (snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowLeft:
                snake.getSnakeBody().add(0, new Position(firstPosition.getX() - 1, firstPosition.getY()));
                if (snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
            case ArrowRight:
                snake.getSnakeBody().add(0, new Position(firstPosition.getX() + 1, firstPosition.getY()));
                if (snakeEatApple(food, snake)) {
                    food.generateFood(terminal, snake);
                } else {
                    snake.getSnakeBody().remove(oldPosition);
                }
                break;
        }
        terminal.setCursorPosition(oldPosition.getX(), oldPosition.getY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(snake.getFirst().getX(), snake.getFirst().getY());
        terminal.putCharacter('\u26AB');


        String message = "SCORE";
        for (int i = 0; i < message.length(); i++) {
            terminal.setCursorPosition(i + 3, 2);
            terminal.putCharacter(message.charAt(i));
        }
        terminal.flush();

        scoreCount(terminal, snake);

    }

    private static String scoreCount(Terminal terminal, Snake snake) throws Exception {
        int score = snake.getSnakeBody().size() * 100 - 300;
        String s = String.valueOf(score);
        for (int i = 0; i <= s.length() - 1; i++) {
            terminal.setCursorPosition(i + 3, 3);
            terminal.putCharacter(s.charAt(i));
            terminal.flush();
        }
        return s;
    }


    private static boolean isSnakeAlive(Snake snake, List<Walls> walls) {
        for (Walls wall : walls) {
            if (wall.getX() == snake.getFirst().getX() && wall.getY() == snake.getFirst().getY()) {
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


    public static boolean snakeEatApple(Food food, Snake snake) {
        if (food.getX() == snake.getFirst().getX() && food.getY() == snake.getFirst().getY()) {
            return true;
        } else {
            return false;
        }
    }

    public static void printText(Terminal terminal, String text, Position startPosition) throws IOException, InterruptedException {
        //terminal.setForegroundColor(new TextColor.RGB(255,0,0));
        for (int i = 0; i < text.length(); i++) {
            terminal.setCursorPosition(i + startPosition.getX(), startPosition.getY());
            terminal.putCharacter(text.charAt(i));
            Thread.sleep(50);
            terminal.flush();

        }
        terminal.flush();
    }

    public static Snake createSnake(Terminal terminal) throws Exception {
        List<Position> snakeBody2 = new ArrayList<>();
        snakeBody2.add(0, new Position(15, 15));
        snakeBody2.add(1, new Position(15, 16));
        snakeBody2.add(2, new Position(15, 17));
        Snake snake = new Snake(15, 15, snakeBody2);
        for (Position snakePosition : snake.getSnakeBody()) {
            terminal.setCursorPosition(snakePosition.getX(), snakePosition.getY());
            terminal.putCharacter('\u26AB');
        }
        terminal.flush();
        return snake;
    }

    public static List<Walls> setUpGameBoard(Terminal terminal) throws Exception {
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
        return walls;
    }
}
