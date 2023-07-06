import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App extends JFrame {
    private AudioPlayer audioPlayer;

    private JButton playButton;
    private JButton pauseButton;
    private JButton undoButton;
    private JButton redoButton;
    private JLabel statusLabel;

    public App() {
        this.audioPlayer = new AudioPlayer(new CommandStack(), this);
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Audio Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickPlayButton();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickPauseButton();
            }
        });

        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickUndoButton();
            }
        });

        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickRedoButton();
            }
        });

        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(redoButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        statusLabel = new JLabel("Status: Stopped");
        statusPanel.add(statusLabel);

        mainPanel.add(statusPanel, BorderLayout.SOUTH);

        add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    public void clickPlayButton() {
        ICommand playCommand = new PlayCommand(audioPlayer);
        playCommand.execute();
        audioPlayer.addCommand(playCommand);
        updateStatus(audioPlayer.getStatus());
        enableButtons();
    }

    public void clickPauseButton() {
        ICommand pauseCommand = new PauseCommand(audioPlayer);
        pauseCommand.execute();
        audioPlayer.addCommand(pauseCommand);
        updateStatus(audioPlayer.getStatus());
        enableButtons();
    }

    public void clickUndoButton() {
        audioPlayer.undo();
        updateStatus(audioPlayer.getStatus());
        enableButtons();
    }

    public void clickRedoButton() {
        audioPlayer.redo();
        updateStatus(audioPlayer.getStatus());
        enableButtons();
    }

    public void enableButtons() {
        boolean canUndo = audioPlayer.canUndo();
        boolean canRedo = audioPlayer.canRedo();

        undoButton.setEnabled(canUndo);
        redoButton.setEnabled(canRedo);
    }

    public void updateStatus(String status) {
        statusLabel.setText("Status: " + status);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App app = new App();
                app.setVisible(true);
            }
        });
    }
}
