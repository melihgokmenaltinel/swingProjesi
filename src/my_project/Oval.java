package my_project;

public class Oval extends Shape {

  private int r; //daire için yarıçap

  public Oval() {

    r = rn.nextInt(50) + 50; //rastgele yarıçap değeri daire için
  }

  public int getR() { //yarıçap değerinin diğer sınıftan ulaşılabilmesi için
    return r;
  }
  public void setR(int r) { //yarıçap değerinin diğer sınıftan değiştirilebilmesi için
    this.r = r;
  }
}