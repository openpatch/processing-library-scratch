import org.openpatch.scratch.*;

public class StageStopAllSounds {

    public StageStopAllSounds() {
        Stage myStage = new Stage(254, 100);
        myStage.addSound("bump", "assets/bump.wav");
        myStage.addSound("music", "assets/music.mp3");
        myStage.playSound("bump");
        myStage.playSound("music");
        myStage.wait(300);
        myStage.stopAllSounds();
        Window.getInstance().exit();
    }

    public static void main(String[] args) {
        new StageStopAllSounds();
    }
}
