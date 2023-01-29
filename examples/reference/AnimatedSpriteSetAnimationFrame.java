import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.GifRecorder;
import org.openpatch.scratch.extensions.Recorder;

public class AnimatedSpriteSetAnimationFrame {
    public AnimatedSpriteSetAnimationFrame() {
        Stage myStage = new Stage(254, 100);
        AnimatedSprite bee = new AnimatedSprite();
        bee.addAnimation("idle", "assets/bee_idle.png", 6, 36, 34);
        myStage.add(bee);
        bee.changeY(20);

        Recorder recorder = new GifRecorder("" + this.getClass().getName());
        recorder.start();
        while(myStage.getTimer().forMillis(5000)) {
            bee.playAnimation("idle");
            if (myStage.isKeyPressed(KeyCode.VK_SPACE)) {
                bee.setAnimationFrame(0);
            }
        }
        recorder.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        new AnimatedSpriteSetAnimationFrame();
    }
}
