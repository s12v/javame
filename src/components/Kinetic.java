package components;

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Graphics;

/**
 * Kinetic scrolling support
 * 
 * Usage:
 *   1. setMaxOffset().
 *   2. add logic() call to your logic thread
 *        or
 *      call startLogic() for separate thread (don't forget to call stopLogic() in hideNotify() aswell)
 *   3. pass pointerPressed/Dragged/Released events from your Canvas class
 *   4. get window offsets using getWindowX()/Y and use in your Canvas during render
 * 
 * Adjust speed by overriding offsetDecrement()
 */
public class Kinetic {

  private static final long  LOGIC_PERIOD = 25;
  
  /**
   * Finger position in last pointerDragged() event
   */
  private int[] drag1;
  /**
   * Current window position
   */
  private int[] window;
  private int[] offset;

  private int[] minOffset;
  private int[] maxOffset;

  private boolean moving;

  private Timer timer;
  
  private int tmp;
  
  public Kinetic() {
    drag1 = new int[2];
    window = new int[2];
    offset = new int[2];
    minOffset = new int[2];
    maxOffset = new int[2];
  }
  
  private void setWindow() {
    // X
    tmp = window[0] + offset[0];
    if (tmp <= minOffset[0]) {
      window[0] = minOffset[0];
    } else if (tmp > maxOffset[0]) {
      window[0] = maxOffset[0];
    } else {
      window[0] = tmp;
    }

    // Y
    tmp = window[1] + offset[1];
    if (tmp <= minOffset[1]) {
      window[1] = minOffset[1];
    } else if (tmp > maxOffset[1]) {
      window[1] = maxOffset[1];
    } else {
      window[1] = tmp;
    }
  }

  protected int offsetDecrement(int offset) {
    return 9 * offset / 10;
  }

  public void logic() {
    if (moving) {
      if (offset[0] != 0) {
        offset[0] = offsetDecrement(offset[0]);
      }
      if (offset[1] != 0) {
        offset[1] = offsetDecrement(offset[1]);
      }
      setWindow();
      if (offset[0] == 0 && offset[1] == 0) {
        moving = false;
      }
    }
  }
  
  public void pointerPressed(int x, int y) {
    drag1[0] = x;
    drag1[1] = y;
    moving = false;
  }

  public void pointerDragged(int x, int y) {
    // Смещение с предудещего pointerDragged()
    offset[0] = drag1[0] - x;
    offset[1] = drag1[1] - y;
    drag1[0] = x;
    drag1[1] = y;
    
    setWindow();
  }
  
  public void pointerReleased(int x, int y) {
    moving = true;
  }
  
  public void setMinOffset(int x, int y) {
    minOffset[0] = x;
    minOffset[1] = y;
  }
  
  public void setMaxOffset(int x, int y) {
    maxOffset[0] = x;
    maxOffset[1] = y;
  }
  
  public int getWindowX() {
    return window[0];
  }
  
  public int getWindowY() {
    return window[1];
  }

  public void renderDebugData(Graphics g) {
    g.setColor(0xffff0000);
    g.drawString("X: " + window[0] + ", Y: " + window[1], 5, 5, Graphics.LEFT | Graphics.TOP);
    g.drawString("offset X: " + offset[0] + ", Y: " + offset[1], 5, 5 + g.getFont().getHeight(), Graphics.LEFT | Graphics.TOP);
  }
  
  public void startLogic() {
    timer = new Timer();
    TimerTask logic = new TimerTask() {
      public void run() {
        logic();
      }
    };
    timer.schedule(logic, 0, LOGIC_PERIOD);
  }

  public void stopLogic() {
    if (timer != null) {
      timer.cancel();
    }
  }
}
