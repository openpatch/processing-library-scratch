import org.openpatch.scratch.Stage;
import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.extensions.GifRecorder;
import org.openpatch.scratch.extensions.Pen;

public class PenEraseAll {
    public PenEraseAll() {
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
        while(myStage.getTimer().forMillis(3000)) {
            if (myStage.isKeyPressed(KeyCode.VK_SPACE)) {
                myPen.eraseAll();
            }
        }
        recorder.snapshot();
        System.exit(0);
    }

    public static void main(String[] args) {
        new PenEraseAll();
    }
}