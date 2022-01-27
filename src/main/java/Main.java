import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class Main {
    public static void main(String[] args) throws Exception {
        TerminalSize ts = new TerminalSize(58, 30);
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        terminalFactory.setInitialTerminalSize(ts);
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
    }
}
