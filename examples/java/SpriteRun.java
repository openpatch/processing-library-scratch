import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;

class Zeta extends Sprite {
    public Zeta() {
        super("green","assets/zeta_green_badge.png");
    }

    @Override
    public void run() {
        this.move(0.1);
    }
}

public class SpriteRun {
    public SpriteRun() {
        Stage myStage = new Stage(254, 100);
        myStage.add(new Zeta());
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        while(myStage.getTimer().forMillis(3000));
        recorder.stop();
        System.exit(0);
    }
    public static void main(String[] args) {
        new SpriteRun();
    }
}