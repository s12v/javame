package components.button;

import graphics.ImageFactory;
import java.io.IOException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class ImageButton extends RectangleButton {
  
  private Image image;
 
  public ImageButton(String image) {
    try {
      this.image = ImageFactory.getInstance().getImage(image);
      setWidth(this.image.getWidth());
      setHeight(this.image.getHeight());
    } catch (IOException io) { }
  }
  
  public abstract void onSelected();

  public void render(Graphics g) {
    render(g, getX(), getY());
  }
  
  public void render(Graphics g, int x, int y) {
    if (image != null) {
      g.drawImage(image, x, y, Graphics.LEFT | Graphics.TOP);
    }
  }

}
