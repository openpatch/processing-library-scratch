import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.*;

public class SpriteRun {

  public SpriteRun() {
    Stage myStage = new Stage(254, 100);
    myStage.add(new Zeta());
    GifRecorder recorder = new GifRecorder(
      "" + this.getClass().getName() + ".gif"
    );
    recorder.start();
    while (myStage.getTimer().forMillis(3000));
    recorder.stop();
    Window.getInstance().exit();
  }

  public static void main(String[] args) {
    new SpriteRun();
  }
}

class Zeta extends Sprite {

  public Zeta() {
    super("green", "assets/zeta_green_badge.png");
  }

  @Override
  public void run() {
    this.move(0.1);
  }
}
