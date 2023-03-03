import org.openpatch.scratch.Stage;
import org.openpatch.scratch.extensions.GifRecorder;
import org.openpatch.scratch.Window;

public class StageGetMouse {
    public StageGetMouse() {
        Stage myStage = new Stage(254,100);
        
        GifRecorder recorder = new GifRecorder("" + this.getClass().getName() + ".gif");
        recorder.start();
        while(myStage.getTimer().forMillis(3000)) {
            float mouseX = myStage.getMouseX();
            float mouseY = myStage.getMouseY();

            myStage.display("X: " + mouseX + " Y: " + mouseY);
        }
        recorder.stop();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new StageGetMouse();
    }
}
