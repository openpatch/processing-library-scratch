import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.Window;
import org.openpatch.scratch.extensions.recorder.*;

public class SpriteThink {
  public SpriteThink() {
    Stage myStage = new Stage(254, 100);
    Sprite zeta = new Sprite("green", "assets/zeta_green_badge.png");
    myStage.add(zeta);
    GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
    zeta.think("Hi! I'm Zeta and can think line breaks");
    myStage.wait(1000);
    recorder.snapshot();
    Window.getInstance().exit();
  }

  public static void main(String[] args) {
    new SpriteThink();
  }
}
