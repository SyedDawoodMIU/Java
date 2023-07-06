
// StopState class representing the stopped state of the audio player
public class StopState implements AudioPlayerState {
    private Song song;

    public StopState(Song song) {
        this.song = song;
    }

    @Override
    public void play() {
        song.play();
    }

    @Override
    public void pause() {
        // No action in stop state
    }

    @Override
    public void create() {
        // Logic to create the play state
    }
}