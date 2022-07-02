package denis.handlers;

import denis.states.BotState;
import denis.states.ExecutionContext;

import java.io.IOException;

public interface Handler {
    void execute(ExecutionContext executionContext) throws IOException;
    String commandName();
    BotState state();
    MainScreen mainScreen();
}
