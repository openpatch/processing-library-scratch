import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;
import org.openpatch.scratch.extensions.Pen;

public class PenChangeSize {
    public PenChangeSize() {
        Stage myStage = new Stage(254, 100);
        Pen myPen = new Pen();
        myStage.add(myPen);
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        while (myStage.getTimer().forMillis(3000)) {
            myPen.changeSize(1);
            myPen.down();
            myPen.setPosition(myStage.pickRandom(0, myStage.getWidth()), 45);
            myPen.up();
            myStage.wait(200);
        }
        recorder.stop();
        System.exit(0);
    }

    public static void main(String[] args) {
        new PenChangeSize();
    }
}