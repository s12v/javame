
package graphics;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import javax.microedition.lcdui.Image;

public class ImageFactory {
  private Hashtable hash;
  private static ImageFactory self;

  private ImageFactory () {
    hash = new Hashtable(30);
  }
  
  public static ImageFactory getInstance() {
    if (self == null) {
      self = new ImageFactory();
    }
    return self;
  }

  public Image getImage(String path) throws IOException {
    if (hash.containsKey(path)) {
      return (Image)hash.get(path);
    } else {
      Image image = loadImage(path);
      hash.put(path, image);
      return image;
    }
  }
  
  private Image loadImage(String path) throws IOException {
    InputStream in = path.getClass().getResourceAsStream(path);
    return Image.createImage(in);
  }

}
