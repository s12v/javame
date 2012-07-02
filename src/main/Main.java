
package main;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import views.KineticScroll;

public class Main extends MIDlet {
  
  private static final long SPLASH_TIME = 500;
  
  private Display display;
  private static Main self;
  
  private KineticScroll KineticScroll;
  
  public Main () {
    self = this;
    display = Display.getDisplay(this);
  }
  
  public static Main getInstance() {
    return self;
  }

  private void init() {
    try {
      KineticScroll = new KineticScroll();
    } catch (Exception ex) { ex.printStackTrace(); }
  }

  public void startApp() {
    showKineticScroll();
  }
  
  public void pauseApp() { }
  public void destroyApp(boolean unconditional) { }

  public void exit() {
    destroyApp(false);
    notifyDestroyed();
  }

  public void showKineticScroll() {
    display.setCurrent(KineticScroll);
  }

}
