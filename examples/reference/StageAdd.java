import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;
import org.openpatch.scratch.Window;
import org.openpatch.scratch.extensions.recorder.*;

public class StageAdd {
  public StageAdd() {
    Stage myStage = new Stage(254, 100);
    GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
    myStage.add(new Sprite("cat", "assets/slime.png"));
    myStage.wait(2000);
    recorder.snapshot();
    Window.getInstance().exit();
  }

  public static void main(String[] args) {
    new StageAdd();
  }
}
