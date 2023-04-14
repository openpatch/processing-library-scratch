import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.recorder.*;

public class SpriteGoLayersForwards {
    public SpriteGoLayersForwards() {
        Stage myStage = new Stage(254, 100);

        Sprite gamma = new Sprite("gamma", "assets/gamma_purple_badge.png");
        gamma.changeX(10);
        myStage.add(gamma);
        myStage.add(new Sprite("zeta", "assets/zeta_green_badge.png"));
        myStage.add(new Sprite("zeta", "assets/zeta_green_badge.png"));
        myStage.add(new Sprite("zeta", "assets/zeta_green_badge.png"));
        myStage.add(new Sprite("zeta", "assets/zeta_green_badge.png"));
        myStage.add(new Sprite("zeta", "assets/zeta_green_badge.png"));

        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        myStage.wait(1000);
        gamma.goLayersForwards(2);
        myStage.wait(1000);
        gamma.goLayersForwards(3);
        myStage.wait(1000);
        recorder.stop();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new SpriteGoLayersForwards();
    }
}

