import org.openpatch.scratch.Stage;
import org.openpatch.scratch.Timer;
import org.openpatch.scratch.extensions.GifRecorder;

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
        System.exit(0);
    }
    public static void main(String[] args) {
        new TimerIntervalMillis3();
    }
}