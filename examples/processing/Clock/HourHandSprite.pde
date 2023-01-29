class HourHandSprite extends Sprite {
  HourHandSprite() {
    this.addCostume("hand", "sprites/hour.png");
  }

  void run() {
    int hour = this.getCurrentHour();
    this.setDirection(hour / 12.0 * 360);
  }
}
