package my_project;

public class Rect extends Shape {

  private int width; //dikdörtgen için en, daire için çap
  private int height; //dikdörtgen için boy

  public Rect() {

    width = rn.nextInt(50) + 50; //rastgele kare/dikdörtgen için en, daire için çap belirlemek için
    height = rn.nextInt(50) + 50; //kare/dikdörtgen için boybelirlemek için
  }

  public int getWidth() { //en değerinin diğer sınıftan ulaşılabilmesi için
    return width;
  }
  public void setWidth(int width) { //en değerinin diğer sınıftan değiştirilebilmesi için
    this.width = width;
  }
  public int getHeight() { //boy değerinin diğer sınıftan ulaşılabilmesi için
    return height;
  }
  public void setHeight(int height) { //boy değerinin diğer sınıftan değiştirilebilmesi için
    this.height = height;
  }
}