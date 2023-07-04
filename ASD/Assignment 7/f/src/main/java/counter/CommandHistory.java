package counter;

import java.util.ArrayList;
import java.util.List;

class CommandHistory {
    private List<Command> history;
    private int currentIndex;

    public CommandHistory() {
        history = new ArrayList<>();
        currentIndex = -1;
    }

    public void addCommand(Command command) {
        
        while (history.size() > currentIndex + 1) {
            history.remove(history.size() - 1);
        }

        history.add(command);
        currentIndex++;
    }

    public boolean canUndo() {
        return currentIndex >= 0;
    }

    public void undo() {
        if (canUndo()) {
            Command command = history.get(currentIndex);
            command.undo();
            currentIndex--;
        }
    }

    public boolean canRedo() {
        return currentIndex < history.size() - 1;
    }

    public void redo() {
        if (canRedo()) {
            currentIndex++;
            Command command = history.get(currentIndex);
            command.redo();
        }
    }
}