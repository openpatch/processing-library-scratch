import org.openpatch.scratch.*;
import org.openpatch.scratch.extensions.recorder.*;
import org.openpatch.scratch.extensions.pen.*;

public class PenUp {
    public PenUp() {
        Stage myStage = new Stage(254, 100);
        Pen myPen = new Pen();
        myStage.add(myPen);
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        myPen.down();
        myPen.setSize(10);
        myPen.setPosition(120, 45);
        myPen.setColor(0, 255, 0);
        myPen.setPosition(50, 45);
        myPen.up();
        myPen.setPosition(50, 0);
        myStage.wait(500);
        recorder.snapshot();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new PenUp();
    }
}
