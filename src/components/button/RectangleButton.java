package components.button;

import javax.microedition.lcdui.Graphics;

public abstract class RectangleButton {
  
  private int x;
  private int y;
  private int width;
  private int height;
  private boolean hidden;
  private boolean highlighted;
  
  public RectangleButton() { }
  
  public abstract void onSelected();
  public abstract void render(Graphics g);
  public abstract void render(Graphics g, int x, int y);

  public final void setPosition(int x, int y) {
    this.setX(x);
    this.setY(y);
  }

  private boolean isPressed(int x, int y) {
    if (!hidden) {
      if (x >= this.x && x <= (this.x + getWidth())) {
        if (y >= this.y && y <= this.y + getHeight()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * @param highlighted the highlighted to set
   */
  protected void setHighlighted(boolean highlighted) {
    this.highlighted = highlighted;
  }

  /**
   * @param hidden the hidden to set
   */
  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  /**
   * @param width the width to set
   */
  protected void setWidth(int width) {
    this.width = width;
  }

  /**
   * @param height the height to set
   */
  protected void setHeight(int height) {
    this.height = height;
  }

  /**
   * @return the x
   */
  public int getX() {
    return x;
  }

  /**
   * @param x the x to set
   */
  public void setX(int x) {
    this.x = x;
  }

  /**
   * @return the y
   */
  public int getY() {
    return y;
  }

  /**
   * @param y the y to set
   */
  public void setY(int y) {
    this.y = y;
  }
  
  /**
   * @return the hidden
   */
  public boolean isHidden() {
    return hidden;
  }

  /**
   * @return the highlighted
   */
  public boolean isHighlighted() {
    return highlighted;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  public void pointerPressed(int x, int y) {
    if (isPressed(x, y)) {
      setHighlighted(true);
    } else {
      setHighlighted(false);
    }
  }

  public void pointerReleased(int x, int y) {
    if (isHighlighted()) {
      setHighlighted(false);
      if (isPressed(x, y)) {
        onSelected();
      }
    }
  }

}
