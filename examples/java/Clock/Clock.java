import org.openpatch.scratch.*;

public class Clock {
  public Clock() {
    Stage myStage = new Stage(800, 800);
    myStage.setDebug(true);
    myStage.add(new ClockSprite());
    myStage.add(new SecondHandSprite());
    myStage.add(new MinuteHandSprite());
    myStage.add(new HourHandSprite());
  }

  public static void main(String[] args) {
    new Clock();
  }
}
