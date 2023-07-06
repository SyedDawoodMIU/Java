import java.util.ArrayList;
import java.util.List;

public class CommandStack {
    private List<ICommand> history;
    private List<ICommand> undoneCommands;

    public CommandStack() {
        history = new ArrayList<ICommand>();
        undoneCommands = new ArrayList<ICommand>();
    }

    public void addCommand(ICommand command) {
        history.add(command);
    }

    public void undo() {
        if (!history.isEmpty()) {
            ICommand command = history.get(history.size() - 1);
            command.unexecute();
            history.remove(command);
            undoneCommands.add(command);
        }
    }

    public void redo() {

        if (!undoneCommands.isEmpty()) {
            ICommand command = undoneCommands.get(undoneCommands.size() - 1);
            command.execute();
            undoneCommands.remove(command);
            history.add(command);
        }
    }

    public boolean canUndo() {
        return !history.isEmpty();
    }

    public boolean canRedo() {
        return !undoneCommands.isEmpty();
    }
}