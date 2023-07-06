
// PlayCommand class representing the play command
public class PlayCommand implements ICommand {
    private AudioPlayer audioPlayer;

    public PlayCommand(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public void execute() {
        audioPlayer.play();
    }

    @Override
    public void unexecute() {
        audioPlayer.pause();
    }
}