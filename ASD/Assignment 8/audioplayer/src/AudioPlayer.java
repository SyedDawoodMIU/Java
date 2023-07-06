
// AudioPlayer class
public class AudioPlayer {
    private AudioPlayerState state;
    private CommandStack commandStack;

    public AudioPlayer(CommandStack commandStack, App ui) {
        this.commandStack = commandStack;
        this.state = new StopState(new Song());
    }

    public void setState(AudioPlayerState state) {
        this.state = state;
    }

    public void play() {
        state.play();
    }

    public void pause() {
        state.pause();
    }

    public void create() {
        state.create();
    }

    public void addCommand(ICommand command) {
        commandStack.addCommand(command);
    }

    public void undo() {
        commandStack.undo();
    }

    public void redo() {
        commandStack.redo();
    }

    public boolean canUndo() {
        return commandStack.canUndo();
    }

    public boolean canRedo() {
        return commandStack.canRedo();
    }

    public String getStatus() {
        return state.getClass().getSimpleName();
    }

}