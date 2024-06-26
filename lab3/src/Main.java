interface MusicPlayerFactory {
    MusicPlayer createMusicPlayer();
}

class ModernMusicPlayerFactory implements MusicPlayerFactory {
    @Override
    public MusicPlayer createMusicPlayer() {
        return new ModernMusicPlayer();
    }
}

class ClassicMusicPlayerFactory implements MusicPlayerFactory {
    @Override
    public MusicPlayer createMusicPlayer() {
        return new ClassicMusicPlayer();
    }
}

interface MusicPlayer {
    void play();
    void pause();
    void stop();
}

class ModernMusicPlayer implements MusicPlayer {
    @Override
    public void play() {
        System.out.println("Modern music player is playing");
    }

    @Override
    public void pause() {
        System.out.println("Modern music player is paused");
    }

    @Override
    public void stop() {
        System.out.println("Modern music player is stopped");
    }
}

class ClassicMusicPlayer implements MusicPlayer {
    @Override
    public void play() {
        System.out.println("Classic music player is playing");
    }

    @Override
    public void pause() {
        System.out.println("Classic music player is paused");
    }

    @Override
    public void stop() {
        System.out.println("Classic music player is stopped");
    }
}

class LegacyMusicPlayer {
    public void start() {
        System.out.println("Legacy music player started");
    }

    public void halt() {
        System.out.println("Legacy music player halted");
    }
}

class MusicPlayerAdapter implements MusicPlayer {
    private LegacyMusicPlayer legacyMusicPlayer;

    public MusicPlayerAdapter(LegacyMusicPlayer legacyMusicPlayer) {
        this.legacyMusicPlayer = legacyMusicPlayer;
    }

    @Override
    public void play() {
        legacyMusicPlayer.start();
    }

    @Override
    public void pause() {
        legacyMusicPlayer.halt();
    }

    @Override
    public void stop() {
        legacyMusicPlayer.halt();
    }
}

interface PlayerState {
    void play(MusicPlayerContext context);
    void pause(MusicPlayerContext context);
    void stop(MusicPlayerContext context);
}

class MusicPlayerContext {
    private PlayerState state;

    public MusicPlayerContext() {
        state = new StoppedState();
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public void play() {
        state.play(this);
    }

    public void pause() {
        state.pause(this);
    }

    public void stop() {
        state.stop(this);
    }
}

class PlayingState implements PlayerState {
    @Override
    public void play(MusicPlayerContext context) {
        System.out.println("Already playing");
    }

    @Override
    public void pause(MusicPlayerContext context) {
        System.out.println("Pausing");
        context.setState(new PausedState());
    }

    @Override
    public void stop(MusicPlayerContext context) {
        System.out.println("Stopping");
        context.setState(new StoppedState());
    }
}

class PausedState implements PlayerState {
    @Override
    public void play(MusicPlayerContext context) {
        System.out.println("Resuming play");
        context.setState(new PlayingState());
    }

    @Override
    public void pause(MusicPlayerContext context) {
        System.out.println("Already paused");
    }

    @Override
    public void stop(MusicPlayerContext context) {
        System.out.println("Stopping from pause");
        context.setState(new StoppedState());
    }
}

class StoppedState implements PlayerState {
    @Override
    public void play(MusicPlayerContext context) {
        System.out.println("Starting play");
        context.setState(new PlayingState());
    }

    @Override
    public void pause(MusicPlayerContext context) {
        System.out.println("Cannot pause. Player is stopped");
    }

    @Override
    public void stop(MusicPlayerContext context) {
        System.out.println("Already stopped");
    }
}

public class Main {
    public static void main(String[] args) {
        // Використання Abstract Factory
        MusicPlayerFactory modernFactory = new ModernMusicPlayerFactory();
        MusicPlayer modernPlayer = modernFactory.createMusicPlayer();

        MusicPlayerFactory classicFactory = new ClassicMusicPlayerFactory();
        MusicPlayer classicPlayer = classicFactory.createMusicPlayer();

        modernPlayer.play();
        classicPlayer.play();

        // Використання Adapter
        LegacyMusicPlayer legacyPlayer = new LegacyMusicPlayer();
        MusicPlayer adaptedPlayer = new MusicPlayerAdapter(legacyPlayer);

        adaptedPlayer.play();
        adaptedPlayer.pause();
        adaptedPlayer.stop();

        // Використання State
        MusicPlayerContext playerContext = new MusicPlayerContext();

        playerContext.play();
        playerContext.pause();
        playerContext.play();
        playerContext.stop();
    }
}
