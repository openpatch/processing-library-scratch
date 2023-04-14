package org.openpatch.scratch;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.openpatch.scratch.extensions.hitbox.Hitbox;
import org.openpatch.scratch.extensions.math.Vector2;
import org.openpatch.scratch.extensions.pen.Pen;
import org.openpatch.scratch.extensions.text.Text;
import org.openpatch.scratch.extensions.text.TextStyle;
import org.openpatch.scratch.extensions.timer.Timer;
import org.openpatch.scratch.internal.Applet;
import org.openpatch.scratch.internal.Color;
import org.openpatch.scratch.internal.Drawable;
import org.openpatch.scratch.internal.Image;
import org.openpatch.scratch.internal.Sound;

import processing.core.PApplet;
import processing.event.KeyEvent;
import processing.event.MouseEvent;

public class Sprite implements Drawable {

  private CopyOnWriteArrayList<Image> costumes = new CopyOnWriteArrayList<>();
  private int currentCostume = 0;
  private CopyOnWriteArrayList<Sound> sounds = new CopyOnWriteArrayList<>();
  private boolean show = true;
  private float size = 100;
  private boolean onEdgeBounce = false;
  private RotationStyle rotationStyle = RotationStyle.ALL_AROUND;
  private float x = 0;
  private float y = 0;
  private float direction = 0;
  private Stage stage;
  private final ConcurrentHashMap<String, Timer> timer;
  private final Pen pen;
  private Hitbox hitbox;
  private Text text;

  public Sprite() {
    this.pen = new Pen();
    this.timer = new ConcurrentHashMap<>();
    this.text = new Text(this);
    this.x = Applet.getInstance().getWidth() / 2.0f;
    this.y = Applet.getInstance().getHeight() / 2.0f;
    this.timer.put("default", new Timer());

  }

  public Sprite(String name, String imagePath) {
    this();
    Image costume = new Image(name, imagePath);
    this.costumes.add(costume);
  }

  /**
   * Copies a Sprite object.
   *
   * @param s a Sprite object to copy
   */
  public Sprite(Sprite s) {
    this.costumes = new CopyOnWriteArrayList<>();
    for (Image costume : s.costumes) {
      this.costumes.add(new Image(costume));
    }
    this.currentCostume = s.currentCostume;
    this.sounds = new CopyOnWriteArrayList<>();
    for (Sound sound : s.sounds) {
      this.sounds.add(new Sound(sound));
    }
    this.show = s.show;
    this.size = s.size;
    this.onEdgeBounce = s.onEdgeBounce;
    this.direction = s.direction;
    this.x = s.x;
    this.y = s.y;
    this.timer = new ConcurrentHashMap<>();
    this.pen = new Pen(s.pen);
    this.text = new Text(s.text);
    this.stage = s.stage;
  }

  public void addedToStage(Stage stage) {
    this.stage = stage;
    this.pen.addedToStage(stage);
    this.text.addedToStage(stage);
    Applet.getInstance().registerMethod("keyEvent", this);
    Applet.getInstance().registerMethod("mouseEvent", this);
  }

  public void removedFromStage(Stage stage) {
    this.pen.removedFromStage(stage);
    Applet.getInstance().unregisterMethod("keyEvent", this);
    Applet.getInstance().unregisterMethod("mouseEvent", this);
    this.stage = null;
  }

  public void remove() {
    if (this.stage != null) {
      this.stage.remove(this);
    }
  }

  public Stage getStage() {
    return this.stage;
  }

  /**
   * Add a costume to the sprite. If a costume with the received name already
   * exists do nothing.
   *
   * @param name      a unique name
   * @param imagePath a image path
   */
  public void addCostume(String name, String imagePath) {
    for (Image costume : this.costumes) {
      if (costume.getName().equals(name)) {
        return;
      }
    }

    Image costume = new Image(name, imagePath);
    this.costumes.add(costume);
  }

  public void addCostume(
      String name,
      String spriteSheetPath,
      int x,
      int y,
      int width,
      int height) {
    for (Image costume : this.costumes) {
      if (costume.getName().equals(name)) {
        return;
      }
    }

    Image costume = new Image(name, spriteSheetPath, x, y, width, height);
    this.costumes.add(costume);
  }

  /**
   * Switch to a costume by name.
   *
   * @param name the name of a costume
   */
  public void switchCostume(String name) {
    for (int i = 0; i < costumes.size(); i++) {
      Image costume = costumes.get(i);
      if (costume.getName().equals(name)) {
        this.currentCostume = i;
        return;
      }
    }
  }

  /**
   * Switch to the next costume.
   */
  public void nextCostume() {
    this.currentCostume = (this.currentCostume + 1) % costumes.size();
  }

  /**
   * Returns the current costume name
   *
   * @return a costume name
   */
  public String getCurrentCostumeName() {
    if (costumes.size() == 0)
      return null;

    return this.costumes.get(this.currentCostume).getName();
  }

  /**
   * Returns the current costume index
   *
   * @return a costume index
   */
  public int getCurrentCostumeIndex() {
    return this.currentCostume;
  }

  /**
   * Add a sound to the sprite. If a sound with the received name already exists
   * do nothing.
   *
   * @param name      a unique name
   * @param soundPath a sound path
   */
  public void addSound(String name, String soundPath) {
    for (Sound sound : this.sounds) {
      if (sound.getName().equals(name)) {
        return;
      }
    }

    Sound sound = new Sound(name, soundPath);
    this.sounds.add(sound);
  }

  /**
   * Remove a sound from the sprite.
   *
   * @param name the sound name
   */
  public void removeSound(String name) {
    for (int i = 0; i < this.sounds.size(); i++) {
      Sound sound = this.sounds.get(i);
      if (sound.getName().equals(name)) {
        this.sounds.remove(i);
        return;
      }
    }
  }

  /**
   * Plays a sound.
   *
   * @param name the sound name
   */
  public void playSound(String name) {
    for (Sound sound : sounds) {
      if (sound.getName().equals(name) && !sound.isPlaying()) {
        sound.play();
      }
    }
  }

  /**
   * Stops the playing of all sounds of the sprite.
   */
  public void stopAllSounds() {
    for (Sound sound : sounds) {
      sound.stop();
    }
  }

  /**
   * Stops the playing of the sound with the given name
   *
   * @param name Name of the sound
   */
  public void stopSound(String name) {
    for (Sound sound : sounds) {
      if (sound.getName().equals(name)) {
        sound.stop();
        break;
      }
    }
  }

  /**
   * Returns true if the sound if playing
   *
   * @return playing
   */
  public boolean isSoundPlaying(String name) {
    for (Sound sound : sounds) {
      if (sound.getName().equals(name)) {
        return sound.isPlaying();
      }
    }
    return false;
  }

  public void setTint(int r, int g, int b) {
    this.setTint((float) b, (float) g, (float) b);
  }

  public void setTint(Color c) {
    this.setTint(c.getRed(), c.getGreen(), c.getBlue());
  }

  /**
   * Sets the tint for the sprite with rgb.
   *
   * @see Image#setTint(float, float, float)
   */
  public void setTint(float r, float g, float b) {
    if (costumes.size() == 0)
      return;

    for (Image costume : this.costumes) {
      costume.setTint(r, g, b);
    }
  }

  /**
   * Sets the tint for the sprite with a hue.
   *
   * @see Image#setTint(float)
   */
  public void setTint(float h) {
    if (costumes.size() == 0)
      return;

    for (Image costume : this.costumes) {
      costume.setTint(h);
    }
  }

  /**
   * Changes the tint for the sprite.
   *
   * @see Image#changeTint(float)
   */
  public void changeTint(float step) {
    if (costumes.size() == 0)
      return;

    for (Image costume : this.costumes) {
      costume.changeTint(step);
    }
  }

  public void changeTint(double step) {
    this.changeTint((float) step);
  }

  /**
   * Sets the transparency of the sprite.
   *
   * @see Image#setTransparency(float)
   * @param transparency 0 full transparency, 255 no transparency
   */
  public void setTransparency(float transparency) {
    if (costumes.size() == 0)
      return;

    for (Image costume : this.costumes) {
      costume.setTransparency(transparency);
    }
  }

  /**
   * Changes the transparency for the sprite.
   *
   * @see Image#changeTransparency(float)
   */
  public void changeTransparency(float step) {
    if (costumes.size() == 0)
      return;

    for (Image costume : this.costumes) {
      costume.changeTransparency(step);
    }
  }

  public void changeTransparency(double step) {
    this.changeTransparency((float) step);
  }

  /**
   * Hides the sprite. The pen is not effected.
   */
  public void hide() {
    this.show = false;
  }

  /**
   * Shows the sprite.
   */
  public void show() {
    this.show = true;
  }

  /**
   * Returns if the sprite is visible
   *
   * @return is visible
   */
  public boolean isVisible() {
    return this.show;
  }

  /**
   * Returns the size of the sprite.
   *
   * @return size in percentage
   */
  public float getSize() {
    return this.size;
  }

  /**
   * Sets the size of the sprite.
   *
   * @param percentage a percentage [0...100]
   */
  public void setSize(float percentage) {
    this.size = percentage;
    for (Image costume : this.costumes) {
      costume.setSize(percentage);
    }
  }

  public void setSize(double percentage) {
    this.setSize((float) percentage);
  }

  /**
   *
   * Changes the size of the sprite by a given percentage.
   *
   * @param amount a percentage [0...100]
   */
  public void changeSize(float amount) {
    this.size += amount;
  }

  public void changeSize(double amount) {
    this.changeSize((float) amount);
  }

  /**
   * Sets if the sprite should bounce when hitting the edge of the screen. This
   * method is for making is attribute perment.
   *
   * @param b
   */
  public void setOnEdgeBounce(boolean b) {
    this.onEdgeBounce = b;
  }

  public void ifOnEdgeBounce() {
    float newX = this.x;
    float newY = this.y;
    Image currentCostume = null;
    if (this.costumes.size() > 0) {
      currentCostume = this.costumes.get(this.currentCostume);
    }
    float costumeWidth = currentCostume != null
        ? currentCostume.getWidth()
        : this.pen.getSize();
    float costumeHeight = currentCostume != null
        ? currentCostume.getHeight()
        : this.pen.getSize();

    float spriteWidth = this.show ? costumeWidth : this.pen.getSize();
    if (newX > Applet.getInstance().getWidth() - spriteWidth / 2 || newX < spriteWidth / 2) {
      this.setDirection(
          this.calculateAngleOfReflection(this.direction, false));
    }

    float spriteHeight = this.show ? costumeHeight : this.pen.getSize();
    if (newY > Applet.getInstance().getHeight() - spriteHeight / 2 || newY < spriteHeight / 2) {
      this.setDirection(
          this.calculateAngleOfReflection(this.direction, true));
    }
    this.setPosition(newX, newY);
  }

  public void setRotationStyle(RotationStyle style) {
    this.rotationStyle = style;
  }

  /**
   * Sets the position of the sprite
   *
   * @param x a x coordinate
   * @param y a y coordinate
   */
  public void setPosition(int x, int y) {
    this.x = x;
    this.y = y;
    this.getPen().setPosition(x, y);
  }

  public void setPosition(float x, float y) {
    this.setPosition(Math.round(x), Math.round(y));
  }

  public void setPosition(double x, double y) {
    this.setPosition((float) x, (float) y);
  }

  /**
   *
   * Sets the position of the sprite based on the coordinates of a given vector.
   *
   * @param v a vector
   */
  public void setPosition(Vector2 v) {
    this.setPosition(v.getX(), v.getY());
  }

  /**
   * Rotates the sprite by a certain degrees to the left.
   *
   * @param degrees between 0 and 360
   */
  public void turnLeft(float degrees) {
    this.setDirection(this.direction - degrees);
  }

  /**
   * Rotates the sprite by a certain degrees to the right.
   *
   * @param degrees between 0 and 360
   */
  public void turnRight(float degrees) {
    this.setDirection(this.direction + degrees);
  }

  /**
   * Sets the direction of the sprite to a given degrees. When this value is 0 the
   * sprite move right, when it is 180 is moves to the left.
   *
   * @param degrees between 0 and 360
   */
  public void setDirection(float degrees) {
    this.direction = degrees;
    if (this.direction < 0) {
      this.direction += 360;
    }
    this.direction %= 360;
  }

  public void setDirection(double degrees) {
    this.setDirection((float) degrees);
  }

  /**
   *
   * Sets the direction of the sprite to the direction of a given vector.
   *
   * @param v a vector
   */
  public void setDirection(Vector2 v) {
    this.setDirection(v.angle());
  }

  public void pointInDirection(float degrees) {
    this.setDirection(degrees);
  }

  public void pointInDirection(double degrees) {
    this.setDirection(degrees);
  }

  public void pointInDirection(Vector2 v) {
    this.setDirection(v);
  }

  public void pointTowardsMousePointer() {
    float mx = this.getMouseX();
    float my = this.getMouseY();

    double angle = new Vector2(mx - this.x, my - this.y).angle();
    this.setDirection(angle);
  }

  public void pointTowardsSprite(Sprite s) {
    float mx = s.getX();
    float my = s.getY();

    double angle = new Vector2(mx - this.x, my - this.y).angle();
    this.setDirection(angle);
  }

  /**
   * Returns the direction of the sprite.
   *
   * @return the direction [0...360]
   */
  public float getDirection() {
    return this.direction;
  }

  /**
   * Returns the pen of the sprite.
   *
   * @return
   */
  public Pen getPen() {
    return this.pen;
  }

  /**
   * Moves the sprite towards the current rotation by the received steps.
   *
   * @param steps a number of pixels
   */
  public void move(float steps) {
    // convert degrees to radians
    float newX = steps * (float) Math.cos(this.direction * Math.PI / 180) + this.x;
    float newY = steps * (float) Math.sin(this.direction * Math.PI / 180) + this.y;

    this.x = newX;
    this.y = newY;

    if (this.onEdgeBounce) {
      this.ifOnEdgeBounce();
    }

    this.pen.setPosition(this.x, this.y);
  }

  public void move(double steps) {
    this.move((float) steps);
  }

  /**
   *
   * Moves the sprite in the direction of the given vector. The length of the
   * vector determines how move the sprite will move in this direction.
   *
   * @param v a vector
   */
  public void move(Vector2 v) {
    setDirection(v.angle());
    move(v.length());
  }

  /**
   * Returns the x coordinate of the sprite
   *
   * @return a x coordinate
   */
  public float getX() {
    return this.x;
  }

  /**
   * Sets the x coordinate
   *
   * @param x a x coordinate
   */
  public void setX(float x) {
    this.x = x;
    this.pen.setPosition(this.x, this.y);
  }

  public void setX(double x) {
    this.setX((float) x);
  }

  /**
   * Changes x by a certain amount
   *
   * @param x number in pixels
   */
  public void changeX(float x) {
    this.x += x;
    this.pen.setPosition(this.x, this.y);
  }

  public void changeX(double x) {
    this.changeX((float) x);
  }

  /**
   * Returns the y coordinate of the sprite
   *
   * @return a y coordinate
   */
  public float getY() {
    return this.y;
  }

  /**
   * Sets the y coordinate
   *
   * @param y a y coordinate
   */
  public void setY(float y) {
    this.y = y;
    this.pen.setPosition(this.x, this.y);
  }

  public void setY(double y) {
    this.setY((float) y);
  }

  /**
   * Changes y by a certain amount
   *
   * @param y number in pixels
   */
  public void changeY(float y) {
    this.y += y;
    this.pen.setPosition(this.x, this.y);
  }

  public void changeY(double y) {
    this.changeY((float) y);
  }

  /**
   * Return the width of the current costume or the pen size, when no costume is
   * available.
   *
   * @return the width of the sprite
   */
  public int getWidth() {
    if (costumes.size() == 0)
      return (int) this.getPen().getSize();

    return this.costumes.get(this.currentCostume).getWidth();
  }

  /**
   * Return the height of the current costume or the pen size, when no costume is
   * available.
   *
   * @return the height of the sprite
   */
  public int getHeight() {
    if (costumes.size() == 0)
      return (int) this.getPen().getSize();

    return this.costumes.get(this.currentCostume).getHeight();
  }

  /**
   * Return the default timer
   *
   * @return the default timer
   */
  public Timer getTimer() {
    return this.timer.get("default");
  }

  /**
   * Return a timer by name
   *
   * @return a timer
   */
  public Timer getTimer(String name) {
    return this.timer.get(name);
  }

  /**
   * Add a new timer by name. Overwriting default is not permitted.
   *
   * @param name the name of the timer
   */
  public void addTimer(String name) {
    if (name.equals("default"))
      return;

    this.timer.put(name, new Timer());
  }

  /**
   * Remove a timer by name. Removing of default is not permitted.
   *
   * @param name the name of the timer
   */
  public void removeTimer(String name) {
    if (name.equals("default"))
      return;

    this.timer.remove(name);
  }

  private float calculateAngleOfReflection(
      float angleOfIncidence,
      boolean horizontalWall) {
    if (horizontalWall) {
      float angleOfReflection = 360 - angleOfIncidence;
      while (angleOfReflection < 0)
        angleOfReflection += 360;
      return angleOfReflection;
    } else {
      float angleOfReflection = 180 - angleOfIncidence;
      while (angleOfReflection < 0)
        angleOfReflection += 360;
      return angleOfReflection;
    }
  }

  /**
   * Returns true is the mouse pointer is touching a non transparent area of the
   * sprite.
   *
   * @return true if touching
   */
  public boolean isTouchingMousePointer() {
    float topLeftCornerX = x - getWidth() / 2.0f;
    float topLeftCornerY = y - getHeight() / 2.0f;

    float bottomRightCornerX = x + getWidth() / 2.0f;
    float bottomRightCornerY = y + getHeight() / 2.0f;

    float[] mouse = Stage.rotateXY(getMouseX(), getMouseY(), x, y, -direction);

    boolean touching = mouse[0] > topLeftCornerX &&
        mouse[1] > topLeftCornerY &&
        mouse[0] < bottomRightCornerX &&
        mouse[1] < bottomRightCornerY;

    if (touching) {
      int relativeMouseX = Math.round(mouse[0] - topLeftCornerX);
      int relativeMouseY = Math.round(mouse[1] - topLeftCornerY);

      if (this.costumes.size() > this.getCurrentCostumeIndex()) {
        int color = this.costumes.get(this.getCurrentCostumeIndex())
            .getPixel(relativeMouseX, relativeMouseY);
        return Applet.getInstance().alpha(color) != 0;
      }
    }

    return false;
  }

  /**
   * Returns true if the rectangle which contains the image is outside of the
   * stage
   *
   * @return true if outside
   */
  public boolean isTouchingEdge() {
    Image currentCostume = null;
    if (this.costumes.size() > this.getCurrentCostumeIndex()) {
      currentCostume = this.costumes.get(this.getCurrentCostumeIndex());
    }
    PApplet parent = Applet.getInstance();
    float costumeWidth = currentCostume != null
        ? currentCostume.getWidth()
        : this.pen.getSize();
    float costumeHeight = currentCostume != null
        ? currentCostume.getHeight()
        : this.pen.getSize();
    float spriteWidth = this.show ? costumeWidth : this.pen.getSize();
    float spriteHeight = this.show ? costumeHeight : this.pen.getSize();

    float[] cornerTopLeft = Stage.rotateXY(
        x - spriteWidth / 2.0f,
        y - spriteHeight / 2.0f,
        x,
        y,
        direction);
    float[] cornerTopRight = Stage.rotateXY(
        x + spriteWidth / 2.0f,
        y - spriteHeight / 2.0f,
        x,
        y,
        direction);
    float[] cornerBottomLeft = Stage.rotateXY(
        x - spriteWidth / 2.0f,
        y + spriteHeight / 2.0f,
        x,
        y,
        direction);
    float[] cornerBottomRight = Stage.rotateXY(
        x + spriteWidth / 2.0f,
        y + spriteHeight / 2.0f,
        x,
        y,
        direction);

    float[][] corners = {
        cornerTopLeft,
        cornerTopRight,
        cornerBottomLeft,
        cornerBottomRight,
    };

    for (float[] corner : corners) {
      float cornerX = corner[0];
      float cornerY = corner[1];
      if (cornerX > parent.width || cornerX < 0) {
        return true;
      }

      if (cornerY > parent.height || cornerY < 0) {
        return true;
      }
    }

    return false;
  }

  public float distanceToMousePointer() {
    float x2 = this.getMouseX();
    float y2 = this.getMouseY();
    float x1 = this.getX();
    float y1 = this.getY();
    return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
  }

  public float distanceToSprite(Sprite sprite) {
    float x2 = sprite.getX();
    float y2 = sprite.getY();
    float x1 = this.getX();
    float y1 = this.getY();
    return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
  }

  public void setHitbox(int... points) {
    int l = points.length / 2;
    int[] xPoints = new int[l];
    int[] yPoints = new int[l];
    for (int i = 0; i < points.length; i += 2) {
      xPoints[i / 2] = points[i];
      yPoints[i / 2] = points[i + 1];
    }
    this.hitbox = new Hitbox(xPoints, yPoints);
  }

  public void setHitbox(int[] xPoints, int[] yPoints) {
    this.hitbox = new Hitbox(xPoints, yPoints);
  }

  public void setHitbox(Hitbox hitbox) {
    this.hitbox = hitbox;
  }

  public Hitbox getHitbox() {
    Image currentCostume = null;
    if (this.costumes.size() > this.getCurrentCostumeIndex()) {
      currentCostume = this.costumes.get(this.getCurrentCostumeIndex());
    }
    float costumeWidth = currentCostume != null
        ? currentCostume.getWidth()
        : this.pen.getSize();
    float costumeHeight = currentCostume != null
        ? currentCostume.getHeight()
        : this.pen.getSize();
    float spriteWidth = this.show ? costumeWidth : this.pen.getSize();
    float spriteHeight = this.show ? costumeHeight : this.pen.getSize();

    if (this.hitbox != null) {
      this.hitbox.translateAndRotateAndResize(
          direction,
          x,
          y,
          x - spriteWidth / 2.0f,
          y - spriteHeight / 2.0f,
          size);
      return this.hitbox;
    }

    float[] cornerTopLeft = Stage.rotateXY(
        x - spriteWidth / 2.0f,
        y - spriteHeight / 2.0f,
        x,
        y,
        direction);
    float[] cornerTopRight = Stage.rotateXY(
        x + spriteWidth / 2.0f,
        y - spriteHeight / 2.0f,
        x,
        y,
        direction);
    float[] cornerBottomLeft = Stage.rotateXY(
        x - spriteWidth / 2.0f,
        y + spriteHeight / 2.0f,
        x,
        y,
        direction);
    float[] cornerBottomRight = Stage.rotateXY(
        x + spriteWidth / 2.0f,
        y + spriteHeight / 2.0f,
        x,
        y,
        direction);

    int[] xPoints = new int[4];
    int[] yPoints = new int[4];
    xPoints[0] = Math.round(cornerTopLeft[0]);
    yPoints[0] = Math.round(cornerTopLeft[1]);
    xPoints[1] = Math.round(cornerTopRight[0]);
    yPoints[1] = Math.round(cornerTopRight[1]);
    xPoints[2] = Math.round(cornerBottomRight[0]);
    yPoints[2] = Math.round(cornerBottomRight[1]);
    xPoints[3] = Math.round(cornerBottomLeft[0]);
    yPoints[3] = Math.round(cornerBottomLeft[1]);

    Hitbox hitbox = new Hitbox(xPoints, yPoints);
    return hitbox;
  }

  public boolean isTouchingSprite(Sprite sprite) {
    if (sprite == null || !sprite.show)
      return false;
    return this.getHitbox().intersects(sprite.getHitbox());
  }

  public boolean isTouchingSprite(Class<? extends Sprite> c) {
    for (Drawable d : stage.drawables) {
      if (c.isInstance(d) && this.isTouchingSprite((Sprite) d)) {
        return true;
      }
    }
    return false;
  }

  public Sprite getTouchingSprite(Class<? extends Sprite> c) {
    for (Drawable d : stage.drawables) {
      if (c.isInstance(d) && this.isTouchingSprite((Sprite) d)) {
        return (Sprite) d;
      }
    }
    return null;
  }

  public ArrayList<Sprite> getTouchingSprites(Class<? extends Sprite> c) {
    ArrayList<Sprite> sprites = new ArrayList<>();
    for (Drawable d : stage.drawables) {
      if (c.isInstance(d) && this.isTouchingSprite((Sprite) d)) {
        sprites.add((Sprite) d);
      }
    }
    return sprites;
  }

  /**
   * Returns the current x-position of the mouse cursor
   *
   * @return x-position
   */
  public float getMouseX() {
    return stage.getMouseX();
  }

  /**
   * Returns the current y-position of the mouse cursor
   *
   * @return y-position
   */
  public float getMouseY() {
    return stage.getMouseY();
  }

  /**
   * Returns true is the mouse button is down
   *
   * @return mouse button down
   */
  public boolean isMouseDown() {
    return stage.isMouseDown();
  }

  /**
   * Returns true if the key is pressed
   *
   * @param keyCode a key code
   * @return key pressed
   */
  public boolean isKeyPressed(int keyCode) {
    return stage.isKeyPressed(keyCode);
  }

  /**
   * Returns the current year
   *
   * @return current year
   */
  public int getCurrentYear() {
    return stage.getCurrentYear();
  }

  /**
   * Returns the current month
   *
   * @return current month
   */
  public int getCurrentMonth() {
    return stage.getCurrentMonth();
  }

  /**
   * Returns the current day of the month
   *
   * @return current day of the month
   */
  public int getCurrentDay() {
    return stage.getCurrentDay();
  }

  /**
   * Returns the current day of the week
   *
   * @return current day of the week
   */
  public int getCurrentDayOfWeek() {
    return stage.getCurrentDayOfWeek();
  }

  /**
   * Returns the current hour
   *
   * @return current hour
   */
  public int getCurrentHour() {
    return stage.getCurrentHour();
  }

  /**
   * Returns the current minute
   *
   * @return current minute
   */
  public int getCurrentMinute() {
    return stage.getCurrentMinute();
  }

  /**
   * Returns the current second
   *
   * @return current second
   */
  public int getCurrentSecond() {
    return stage.getCurrentSecond();
  }

  /**
   * Returns the current millisecond
   *
   * @return current millisecond
   */
  public int getCurrentMillisecond() {
    return stage.getCurrentMillisecond();
  }

  /**
   * Returns the days since 2010/01/01
   *
   * @return days since 2010/01/01
   */
  public int getDaysSince2000() {
    return stage.getDaysSince2000();
  }

  public void keyEvent(KeyEvent e) {
    switch (e.getAction()) {
      case KeyEvent.PRESS:
        this.whenKeyPressed(e.getKeyCode());
    }
  }

  public void whenKeyPressed(int keyCode) {
  }

  public void mouseEvent(MouseEvent e) {
    this.whenMouseMoved(e.getX(), e.getY());
  }

  public void whenMouseMoved(float x, float y) {
  }

  public void whenClicked() {
  }

  public void goToFrontLayer() {
    stage.goToFrontLayer(this);
  }

  public void goToBackLayer() {
    stage.goToBackLayer(this);
  }

  public void goLayersForwards(int number) {
    stage.goLayersForwards(this, number);
  }

  public void goLayersBackwards(int number) {
    stage.goLayersBackwards(this, number);
  }

  public void whenBackdropSwitches(String name) {
  }

  public int pickRandom(int from, int to) {
    if (to < from) {
      return to + (int) (Math.random() * (from - to));
    }
    return from + (int) (Math.random() * (to - from));
  }

  public Text getText() {
    return this.text;
  }

  public void think(String text) {
    this.text.setStyle(TextStyle.THINK);
    this.text.showText(text);
  }

  public void think(String text, int millis) {
    this.text.setStyle(TextStyle.THINK);
    this.text.showText(text, millis);
  }

  public void say(String text) {
    this.text.setStyle(TextStyle.SPEAK);
    this.text.showText(text);
  }

  public void say(String text, int millis) {
    this.text.setStyle(TextStyle.SPEAK);
    this.text.showText(text, millis);
  }

  /**
   * Draws the sprite if it is not hidden.
   */
  public void draw() {
    if (this.stage == null)
      return;
    this.pen.draw();
    if (costumes.size() > 0 && this.show) {
      this.costumes.get(this.currentCostume)
          .draw(this.size, this.direction, this.x, this.y, this.rotationStyle);
    }

    if (Applet.getInstance().isDebug()) {
      this.getHitbox().draw();
    }
    this.text.setPosition(
        x + this.getWidth() * 0.9 / 2,
        y - this.getHeight() * 1.1 / 2);
    this.text.draw();
    this.run();
  }

  public void run() {
  }
}
