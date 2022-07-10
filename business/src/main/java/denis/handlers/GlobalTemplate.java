package denis.handlers;

import denis.states.ExecutionContext;

public interface GlobalTemplate<T> {
    void execute (ExecutionContext executionContext, T localState);
    String nextStep();
    String commandName();
}
