import org.openpatch.scratch.KeyCode;
import org.openpatch.scratch.Sprite;
import org.openpatch.scratch.Stage;

public class SpriteWhenKeyPressed {

    class CustomSprite extends Sprite {
        public CustomSprite() {
            this.addCostume("zeta", "examples/java/assets/zeta_green_badge.png");
            this.addCostume("gamma", "examples/java/assets/gamma_purple_badge.png");
        }

        @Override
        public void whenKeyPressed(int keyCode) {
            if (keyCode == KeyCode.VK_UP) {
                this.changeY(-2);
            } else if (keyCode == KeyCode.VK_DOWN) {
                this.changeY(2);
            } else if (keyCode == KeyCode.VK_LEFT) {
                this.changeX(-2);
            } else if (keyCode == KeyCode.VK_RIGHT) {
                this.changeX(2);
            }
        }
    }

    public SpriteWhenKeyPressed() {
        Stage myStage = new Stage(254, 100);
        myStage.add(new CustomSprite());
    }

    public static void main(String[] args) {
        new SpriteWhenKeyPressed();
    }
}