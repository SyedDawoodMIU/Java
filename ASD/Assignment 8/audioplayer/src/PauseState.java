
// PauseState class representing the paused state of the audio player
public class PauseState implements AudioPlayerState {
    private Song song;

    public PauseState(Song song) {
        this.song = song;
    }

    @Override
    public void play() {
        song.playFromLastLocation();
        // Logic to create the play state
    }

    @Override
    public void pause() {
        // No action when already paused
    }

    @Override
    public void create() {
        // Logic to create the pause state
    }
}