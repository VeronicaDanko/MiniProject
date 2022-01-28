import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(58, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        List<Position> snakeBody2 = new ArrayList<>();
        snakeBody2.add(new Position(15, 16));
        snakeBody2.add(new Position(15, 17));
        Snake userSnake = new Snake(15, 15, snakeBody2);
        for (Position snakePosition : userSnake.getSnakeBody()){
            terminal.setCursorPosition(snakePosition.getX(), snakePosition.getY());
            terminal.putCharacter('S');
        }
        terminal.flush();

        terminal.setCursorPosition(userSnake.getX(), userSnake.getY());
        terminal.putCharacter('s');
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

        //RandomHeart

        Random random = new Random();
        Character c = ' '; //gör att äpplet försvinner
        Food food = new Food(5, 5);
        int foodX = random.nextInt(1, 29);
        int foodY = random.nextInt(1, 57); //mat får inte dyka upp på orm - hur löser vi detta?
        terminal.setCursorPosition(foodX, foodY);
        terminal.putCharacter(food.getHeart());







        KeyStroke latestKeyStroke = null;

        /*boolean continueReadingInput = true;
        while (continueReadingInput) {

            int index = 0;
            KeyStroke keyStroke = null;
            do {
                index++;
                if (index % 100 == 0) {
                    if (latestKeyStroke != null) {
                        handlePlayer(snake, latestKeyStroke, terminal);
                    }
                }

                Thread.sleep(5); // might throw InterruptedException
                keyStroke = terminal.pollInput();


            } while (keyStroke == null);
            latestKeyStroke = keyStroke;


        }

    }
    private static void handlePlayer(Snake snake, KeyStroke keyStroke,Terminal terminal) throws Exception {
        Position oldPosition = new Position(snake.getX(), snake.getY());
        switch (keyStroke.getKeyType()) {
            case ArrowDown:
                snake.getY() += 1;
                break;
            case ArrowUp:
                snake.getY() -= 1;
                break;
            case ArrowLeft:
                snake.getX() -=1;
                break;
            case ArrowRight:
                snake.getX() +=1;
                break;
        }
        terminal.setCursorPosition(oldPosition.getX(), oldPosition.getY());
        terminal.putCharacter(' ');

        terminal.setCursorPosition(snake.getX(), snake.getY());
        terminal.putCharacter('\u263a');

        terminal.flush();
    }

    private static boolean isSnakeAlive(Snake snake, List<Walls> walls) {
        for (Walls wall : walls) {
            if(wall.getX() == snake.getX() && wall.getY() == snake.getY()) {
                return false;
            }
        }
        return true;*/
    }
}
