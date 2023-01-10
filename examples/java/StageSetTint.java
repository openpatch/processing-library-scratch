import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;

public class StageSetTint {
    public StageSetTint() {
        Stage myStage = new Stage(254, 100);
        myStage.addBackdrop("forest", "assets/background_forest.png");
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        myStage.wait(2000);
        myStage.setTint(200);
        myStage.wait(2000);
        recorder.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        new StageSetTint();
    }
}