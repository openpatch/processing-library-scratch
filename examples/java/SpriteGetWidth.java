import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;

public class SpriteGetWidth {
    public SpriteGetWidth() {
        Stage myStage = new Stage(254, 100);
        Sprite mySprite = new Sprite("zeta", "assets/zeta_green_badge.png");
        myStage.add(mySprite);
        mySprite.changeX(-80);
        mySprite.changeY(30);
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        mySprite.say("Width: " + mySprite.getWidth());
        myStage.wait(3000);
        recorder.snapshot();
        System.exit(0);
    }

    public static void main(String[] args) {
        new SpriteGetWidth();
    }
}