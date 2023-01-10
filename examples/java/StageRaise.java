import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;

public class StageRaise {
    public StageRaise() {
        Stage myStage = new Stage(254, 100);

        Sprite gamma = new Sprite("gamma", "assets/gamma_purple_badge.png");
        myStage.add(gamma);

        myStage.add(new Sprite("zeta", "assets/zeta_green_badge.png"));

        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        myStage.wait(2000);
        myStage.raise(gamma);
        myStage.wait(2000);
        recorder.stop();
        System.exit(0);

    }

    public static void main(String[] args) {
        new StageRaise();
    }
}
