import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.recorder.*;
import org.openpatch.scratch.extensions.timer.Timer;

public class TimerReset {

  public TimerReset() {
    Stage myStage = new Stage(254, 100);
    Timer myTimer = new Timer();
    GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
    recorder.start();
    myStage.display("Start!");
    myStage.wait(500);
    while (myTimer.forMillis(1000)) {
      myStage.display("Running 1st...");
    }
    ;
    myTimer.reset();
    while (myTimer.forMillis(1000)) {
      myStage.display("Running 2nd...");
    }
    ;
    myStage.display("Stop!");
    myStage.wait(500);
    recorder.stop();
    Window.getInstance().exit();
  }

  public static void main(String[] args) {
    new TimerReset();
  }
}
