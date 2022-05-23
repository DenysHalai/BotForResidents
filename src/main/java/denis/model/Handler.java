package denis.model;

import denis.ExecutionContext;
import denis.model.BotState;

public interface Handler {
    void execute(ExecutionContext executionContext);
    String commandName();
    BotState state();
}
