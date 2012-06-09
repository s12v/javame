package views;

import components.Kinetic;
import components.button.ImageButton;
import graphics.ImageFactory;
import main.Main;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;

public class KineticScroll extends GameCanvas {

  private final static long UI_PERIOD = 25;
  private final static long LOGIC_PERIOD = 25;

  private Kinetic kinetic;
  private Timer timer;
  private Graphics g;
  
  private Image background;
  private ImageButton closeButton;
      
  public KineticScroll() {
    super(false);
    setFullScreenMode(true);
    g = getGraphics();
    init();
  }

  /**
   */
  private void init()
  {
    closeButton = new ImageButton("/cross.png") {
      public void onSelected() {
        Main.getInstance().exit();
      }
    };
    closeButton.setX(getWidth() - closeButton.getWidth() - 5);
    closeButton.setY(5);
    
    try {
      background = ImageFactory.getInstance().getImage("/lorem.png");
    } catch (IOException io) { }
    
    kinetic = new Kinetic() {
      protected int offsetDecrement(int offset) {
        return 99 * offset / 100;
      }
    };
    kinetic.setMaxOffset(background.getWidth()-getWidth(), 2*background.getHeight()-getHeight());
  }
  
  public void showNotify() {
    super.showNotify();
    
    timer = new Timer();
    TimerTask ui = new TimerTask() {
      public void run() {
        render();
        flushGraphics();
      }
    };
    timer.schedule(ui, 0, UI_PERIOD);
    
    TimerTask logic = new TimerTask() {
      public void run() {
        logic();
      }
    };
    timer.schedule(logic, 0, LOGIC_PERIOD);
  }

  public void hideNotify() {
    super.hideNotify();
    timer.cancel();
  }

  private void render() {
    g.setColor(0xffffffff);
    g.fillRect(0, 0, getWidth(), getHeight());
    g.drawImage(background, -kinetic.getWindowX(), -kinetic.getWindowY(), Graphics.LEFT | Graphics.TOP);
    g.drawImage(background, -kinetic.getWindowX(), background.getHeight() - kinetic.getWindowY(), Graphics.LEFT | Graphics.TOP);
    closeButton.render(g);
    
    kinetic.renderDebugData(g);
  }

  protected void logic() {
    kinetic.logic();
  }

  protected void pointerPressed(int x, int y) {
    closeButton.pointerPressed(x, y);
   kinetic.pointerPressed(x, y);
 }

  protected void pointerDragged(int x, int y) {
   kinetic.pointerDragged(x, y);
 }
  
  protected void pointerReleased(int x, int y) {
    closeButton.pointerReleased(x, y);
   kinetic.pointerReleased(x, y);
  }
  
}
