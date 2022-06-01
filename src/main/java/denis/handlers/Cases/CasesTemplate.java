package denis.handlers.Cases;

import denis.states.CaseLocalState;
import denis.states.ExecutionContext;

public interface CasesTemplate {
    void execute(ExecutionContext executionContext, CaseLocalState localState);
    String nextStep();
    String commandName();
}
