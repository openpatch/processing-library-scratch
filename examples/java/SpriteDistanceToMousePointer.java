import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;

public class SpriteDistanceToMousePointer {
    public SpriteDistanceToMousePointer() {
        Stage myStage = new Stage(254, 100);

        Sprite gamma = new Sprite("gamma", "assets/gamma_purple_badge.png");
        gamma.setPosition(0, 50);
        myStage.add(gamma);

        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        while(myStage.getTimer().forMillis(3000)) {
            gamma.changeX(5);
            myStage.display("Distance: " + gamma.distanceToMousePointer());
            myStage.wait(100);
        }
        recorder.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        new SpriteDistanceToMousePointer();
    }
}

