package my_project;

//Melih Gökmen ALTINEL, Simsoft Java Yazılım Mühendisliği İş Başvurusu Ödev Çalışması

//Kullanacağımız kodların ihtiyacı olan kütüphanelerimizi import ediyoruz
import java.awt.Color;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.Serializable;

public class Shape implements Serializable {
  //şekil nesnelerini üretip kullanmak için oluşturduğumuz sınıf, Client-Server arasında nesne gönderebilmek için Serializable interface'inden implement ettik

  private int x; //koordinat için x  
  private int y; //koordinat için y

  private boolean xStatus, yStatus; //şekillerin, JFrame dışına çıkıp çıkmadığını kontrol etmek için kullanacağımız değerler
  private Color color; //renk türünde değişken türettik

  public Color getColor() {
    return color;
  }
  public void setColor(Color color) {
    this.color = color;
  }

  Random rn = new Random(); //rastgele boyut, konum, renk için kullanılacak

  public Shape() { //yapıcı fonksiyon, class new sözcüğü ile üretilince otomatik çalışır

    x = rn.nextInt(200) + 100; //rastgele koordinat x belirlemek için 
    y = rn.nextInt(200) + 100; //rastgele koordinat y belirlemek için 

    color = new Color(rn.nextInt(255) + 1, rn.nextInt(255) + 1, rn.nextInt(255) + 1); //rgb türünde random renk oluşturmak için

    int random = rn.nextInt();
    int random2 = rn.nextInt();

    if (random % 2 == 0) {
      xStatus = true;
    } else {
      xStatus = false;
    }

    if (random2 % 2 == 0) {
      yStatus = true;
    } else {
      yStatus = false;
    }

  }

  public int getX() { //koordinat x değerinin diğer sınıftan ulaşılabilmesi için
    return x;
  }
  public void setX(int x) { //koordinat x değerinin diğer sınıftan değiştirilebilmesi için
    this.x = x;
  }
  public int getY() { //koordinat y değerinin diğer sınıftan ulaşılabilmesi için
    return y;
  }
  public void setY(int y) { //koordinat y değerinin diğer sınıftan değiştirilebilmesi için
    this.y = y;
  }
  public boolean isxStatus() { //xStatus değerinin diğer sınıftan ulaşılabilmesi için
    return xStatus;
  }
  public void setxStatus(boolean xStatus) { //xStatus değerinin diğer sınıftan değiştirilebilmesi için
    this.xStatus = xStatus;
  }
  public boolean isyStatus() { //yStatus  değerinin diğer sınıftan ulaşılabilmesi için
    return yStatus;
  }
  public void setyStatus(boolean yStatus) { //yStatus değerinin diğer sınıftan değiştirilebilmesi için
    this.yStatus = yStatus;
  }

}