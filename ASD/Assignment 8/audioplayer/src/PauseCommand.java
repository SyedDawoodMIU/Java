
// PauseCommand class representing the pause command
public class PauseCommand implements ICommand {
    private AudioPlayer audioPlayer;

    public PauseCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        audioPlayer.pause();
    }

    @Override
    public void unexecute() {
        audioPlayer.play();
    }
}