package denis.model;

import denis.ExecutionContext;
import denis.model.BotState;

import java.io.IOException;

public interface Handler {
    void execute(ExecutionContext executionContext) throws IOException;
    String commandName();
    BotState state();
}
