import org.openpatch.scratch.Stage;
import org.openpatch.scratch.Timer;
import org.openpatch.scratch.extensions.*;
import org.openpatch.scratch.Window;

public class TimerIntervalMillis3 {

    public TimerIntervalMillis3() {
        Stage myStage = new Stage(254, 100);
        Timer myTimer = new Timer();
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        while (myStage.getTimer().forMillis(3000)) {
            if (myTimer.intervalMillis(500, true)) {
                myStage.display("Interval 1");
            } else {
                myStage.display("Interval 2");
            }
        }
        recorder.stop();
        Window.getInstance().exit();
    }
    public static void main(String[] args) {
        new TimerIntervalMillis3();
    }
}