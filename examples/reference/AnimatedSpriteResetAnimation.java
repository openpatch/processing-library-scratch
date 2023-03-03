import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.GifRecorder;
import org.openpatch.scratch.Window;
import org.openpatch.scratch.extensions.Recorder;

public class AnimatedSpriteResetAnimation {
    public AnimatedSpriteResetAnimation() {
        Stage myStage = new Stage(254, 100);
        AnimatedSprite bee = new AnimatedSprite();
        bee.addAnimation("idle", "assets/bee_idle.png", 6, 36, 34);
        myStage.add(bee);

        Recorder recorder = new GifRecorder("" + this.getClass().getName());
        recorder.start();
        while(myStage.getTimer().forMillis(4000)) {
            bee.playAnimation("idle", true);
            if(myStage.isKeyPressed(KeyCode.VK_SPACE)) {
                bee.resetAnimation();
            }
        }
        recorder.stop();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new AnimatedSpriteResetAnimation();
    }
}
