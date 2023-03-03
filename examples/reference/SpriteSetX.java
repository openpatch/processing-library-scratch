import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;
import org.openpatch.scratch.Window;

public class SpriteSetX {
    public SpriteSetX() {
        Stage myStage = new Stage(254, 100);
        Sprite mySprite = new Sprite("slime", "assets/slime.png");
        myStage.add(mySprite);
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        myStage.wait(2000);
        mySprite.setX(50);
        myStage.wait(2000);
        recorder.stop();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new SpriteSetX();
    }
}
