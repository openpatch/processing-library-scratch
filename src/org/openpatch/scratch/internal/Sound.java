package org.openpatch.scratch.internal;

import processing.sound.*;

public class Sound {
  private String name;
  private SoundFile sound;
  private double volume;

  public Sound(String name, String soundPath) {
    this.name = name;
    this.sound = new SoundFile(Applet.getInstance(), soundPath);
  }

  /**
   * Copies a ScratchSound object.
   *
   * @param s ScratchSound object to copy
   */
  public Sound(Sound s) {
    this.name = s.name;
    this.sound = s.sound;
  }

  /**
   * Returns the name
   *
   * @return the name
   */
  public String getName() {
    return this.name;
  }

  /** Starts playing the sound file. */
  public void play() {
    if (!this.isPlaying()) {
      this.sound.play();
    }
  }

  /** Pauses the playing of the sound file. */
  public void pause() {
    if (this.sound != null) {
      this.sound.pause();
    }
  }

  /** Stops the playing of the sound file. */
  public void stop() {
    if (this.sound != null) {
      this.sound.stop();
    }
  }

  /**
   * Return true if the sound file is playing.
   *
   * @return sound file is playing
   */
  public boolean isPlaying() {
    if (this.sound != null) {
      return this.sound.isPlaying();
    }
    return false;
  }

  /**
   * Sets the volume
   *
   * @param amp A volume [0...1]
   */
  public void setVolume(double amp) {
    this.volume = amp;
    if (amp <= 1 && amp >= 0) {
      this.sound.amp((float) amp);
    }
  }

  /**
   * Changes the volume by a step
   *
   * @param step
   */
  public void changeVolume(double step) {
    var newVolume = this.volume + step;
    if (newVolume <= 1 && newVolume >= 0) {
      this.volume = newVolume;
    }
  }

  /**
   * Returns the volume
   *
   * @return the volume
   */
  public double getVolume() {
    return this.volume;
  }
}
