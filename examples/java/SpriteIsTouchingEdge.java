import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;

public class SpriteIsTouchingEdge {
    public SpriteIsTouchingEdge() {
        Stage myStage = new Stage(254, 100);
        Sprite mySprite = new Sprite("zeta", "assets/zeta_green_badge.png");
        mySprite.changeY(-10);
        mySprite.changeX(-80);
        myStage.add(mySprite);

        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        while(myStage.getTimer().forMillis(3000)) {
            mySprite.say("Is touching edge? " + mySprite.isTouchingEdge());
            mySprite.changeY(10);
            myStage.wait(500);
        }
        recorder.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        new SpriteIsTouchingEdge();
    }
}
