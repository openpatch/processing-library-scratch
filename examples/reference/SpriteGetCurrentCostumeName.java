import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.*;

public class SpriteGetCurrentCostumeName {
    public SpriteGetCurrentCostumeName() {
        Stage myStage = new Stage(256, 100);
        Sprite mySprite = new Sprite("zeta", "assets/zeta_green_badge.png");
        mySprite.addCostume("gamma", "assets/gamma_purple_badge.png");
        mySprite.changeY(20);
        myStage.add(mySprite);

        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();

        mySprite.think("Name: " + mySprite.getCurrentCostumeName());
        myStage.wait(2000);
        mySprite.nextCostume();
        mySprite.think("Name: " + mySprite.getCurrentCostumeName());
        myStage.wait(2000);

        recorder.stop();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new SpriteGetCurrentCostumeName();
    }
}