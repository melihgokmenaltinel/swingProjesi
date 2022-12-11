package my_project;

//Melih Gökmen ALTINEL, Simsoft Java Yazılım Mühendisliği İş Başvurusu Ödev Çalışması

//Kullanacağımız kodların ihtiyacı olan kütüphanelerimizi import ediyoruz
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Main extends JPanel implements ActionListener, Serializable {
  //sınıfımızı JPanel sınıfından ve ActionListener interface'inden miras alarak oluşturduk bu sayede JPanel ve ActionListener'ın sahip olduğu özellikleri kullanabileceğiz
  // Client-Server arasında nesne gönderebilmek için Serializable interface'inden miras alarak oluşturduk

  //aşağıdaki değer tanımlamaları integer olamaz çünkü kullanıcı dışardan sayı olmayan değer girebilir bunu aşağıda kontrol edip mutlaka sayı girmesini sağlıyoruz
  public static String numberOval; //kullanıcıdan alınan veriyi tutmak için
  public static String numberSquare; //kullanıcıdan alınan veriyi tutmak için 
  public static String numberRectangle; //kullanıcıdan alınan veriyi tutmak için 

  public static int totalNumberOfShapes; //toplam kaç adet şekil oluşturulacağını tutmak için

  final int frameX = 600; //JFrame nesnesinin eni 
  final int frameY = 600; //JFrame nesnesinin boyu 

  Timer timer; //Zamanlayıcı nesnesi türettik 

  private JButton btnStart; //işlemi başlatmak için kullancağımız buton
  private JButton btnStop; //işlemi durdurmak için kullancağımız buton

  private int numOfShapes; //maksimum kaç adet şekil olabilir ekranda bu değeri tutmak için
  private int speed; //şekillerin ekranda hareket hızını değiştirmek için kullanacağımız değişken

  private Shape[] shape; //Diğer class'ımızdan nesne türettik
  private Oval[] oval; //Oval class'ımızdan nesne türettik
  private Square[] square; //Square class'ımızdan nesne türettik
  private Rect[] rect; //Rect class'ımızdan nesne türettik

  Random r = new Random(); //Random nesnesi türettik, renk hız boyut için rastgele değerler üretip kullanmak için

  public Main() { //Yapıcı fonksiyonumuz, programı çalıştırdığımızda otomatik tetiklenerek çalışır

    setLayout(null); //butonun ekrandaki konumlarını belirtmek için kullanılan kod, JFrame'e ait bir fonksiyon

    btnStart = new JButton("Start"); //İşlemi başlatmak için tıkladığımız butonun üzerindeki metni veriyoruz
    btnStart.setBounds(200, 500, 80, 20); //İşlemi başlatmak için tıkladığımız butonun konum ve genişlik bilgileri
    btnStart.addActionListener(this); //İşlemi başlatmak için tıkladığımız butonun Action Listener ile dinlenmesi ve çalışabilmesi için 

    btnStop = new JButton("Stop"); //İşlemi durdurmak için tıkladığımız butonun üzerindeki metni veriyoruz
    btnStop.setBounds(300, 500, 80, 20); //İşlemi durdurmak için tıkladığımız butonun konum ve genişlik bilgileri
    btnStop.addActionListener(this); //İşlemi durdurmak için tıkladığımız butonun Action Listener ile dinlenmesi ve çalışabilmesi için 
    btnStop.setEnabled(false); //Başlangıçta pasif olarak gelir çünkü önce işlemin başlatılması gerekli

    //  add(btnStart); //İşlemi başlatmak için tıkladığımız butonu JFareme'e ekliyoruz
    // add(btnStop); //İşlemi durdurmak için tıkladığımız butonun JFareme'e ekliyoruz

    timer = new Timer(10, this); //zamanlayıcı nesnemize gecikme süremizi verdik
    numOfShapes = totalNumberOfShapes; //maksimum oluşturulacak Shape nesnesi adetini dışarıdan girilen toplam şekil adeti ile belirtiyoruz
    speed = 5; //şekilere hareket hızlarını tanımlıyoruz

    timer.start(); //hareketi başlatmak için timer'ı başlatır

    this.shape = new Shape[numOfShapes];
    this.oval = new Oval[Integer.parseInt(numberOval)];
    this.square = new Square[Integer.parseInt(numberSquare)];
    this.rect = new Rect[Integer.parseInt(numberRectangle)];

    //dışardan girilen toplam şekil adeti kadar Shape nesnesi oluşturuyoruz çünkü ortak değerler X ve Y bu sınıfta
    for (int i = 0; i < shape.length; i++) {

      shape[i] = new Shape(); //oluşturduğumuz her nesneyi bir diziye ekliyoruz

    }
    //Oval için R değerini kullanacağız şekli oluştururken 
    for (int i = 0; i < oval.length; i++) {

      oval[i] = new Oval(); //oluşturduğumuz her nesneyi bir diziye ekliyoruz

    }
    //Kare için width, height değerini kullanacağız şekli oluştururken 
    for (int i = 0; i < square.length; i++) {

      square[i] = new Square(); //oluşturduğumuz her nesneyi bir diziye ekliyoruz

    }
    //Dikdörtgen için width, height değerini kullanacağız şekli oluştururken 
    for (int i = 0; i < rect.length; i++) {

      rect[i] = new Rect(); //oluşturduğumuz her nesneyi bir diziye ekliyoruz

    }
  }

  public void move() { //şekillerin hareketini gerçekleştirmek için oluşturduğumuz fonksiyon

    setNewStatus(); //her işlemde nesneler dışarı çıkamasın diye çalışan fonksiyonumuz

    for (int i = 0; i < shape.length; i++) { //başlangıç konumlarını(x ve y) oluşturmak için çalışan kod kısmı

      if (shape[i].isxStatus() && shape[i].isyStatus()) {
        int x = shape[i].getX();
        int y = shape[i].getY();

        shape[i].setX(x + r.nextInt(speed) + 1);
        shape[i].setY(y + r.nextInt(speed) + 1);
      } else if (!shape[i].isxStatus() && shape[i].isyStatus()) {
        int x = shape[i].getX();
        int y = shape[i].getY();

        shape[i].setX(x - r.nextInt(speed) + 1);
        shape[i].setY(y + r.nextInt(speed) + 1);

      } else if (shape[i].isxStatus() && !shape[i].isyStatus()) {
        int x = shape[i].getX();
        int y = shape[i].getY();

        shape[i].setX(x + r.nextInt(speed) + 1);
        shape[i].setY(y - r.nextInt(speed) + 1);
      } else {
        int x = shape[i].getX();
        int y = shape[i].getY();

        shape[i].setX(x - r.nextInt(speed) + 1);
        shape[i].setY(y - r.nextInt(speed) + 1);
      }
    }
  }

  public void setNewStatus() { //her işlemde nesneler dışarı çıkamasın diye oluşturduğumuz fonksiyonumuz

    int width_or_r;

    for (int k = 0; k < shape.length; k++) {

      if (k < Integer.parseInt(numberOval)) {
        width_or_r = oval[k].getR();
      } else if (Integer.parseInt(numberOval) <= k && k < Integer.parseInt(numberSquare) + Integer.parseInt(numberOval)) {
        width_or_r = square[k - Integer.parseInt(numberOval)].getWidth();

      } else {

        width_or_r = rect[k - Integer.parseInt(numberOval) - Integer.parseInt(numberSquare)].getWidth();

      }

      if (shape[k].getX() <= 0) { //şekilin enini kontrol etmek için  çalışır
        shape[k].setxStatus(true);
      } else if (shape[k].getX() >= frameX - width_or_r - 10) {
        //şekilin eni ile JFrame'in enine bakarak çalışır dışarı çıktıysa setxStatus değerini false yapar yani x değeri yanlış durumda
        shape[k].setxStatus(false);
      }
      if (shape[k].getY() <= 0) { //şekilin boyunu kontrol etmek için çalışır
        shape[k].setyStatus(true);
      } else if (shape[k].getY() >= frameY - width_or_r - 30) {
        //şekilin boyu ile JFrame'in boyuna bakarak çalışır dışarı çıktıysa setYStatus değerini false yapar yani y değeri yanlış durumda
        shape[k].setyStatus(false);

      }

    }
  }

  public void paint(Graphics g) { //ekrana çizim işlemini yapan fonksiyonumuz

    super.paint(g); //önce ekranı temizlerki her şekil hareketinde, önceki hareketteki görüntü kalmasın

    move(); //şekillerin hareketini gerçekleştirmek için çağırdığımız fonksiyon

    int width = 0, height = 0, r = 0;

    for (int i = 0; i < shape.length; i++) { //toplam şekil adeti kadar dönecek olan döngümüz

      if (i < Integer.parseInt(numberOval)) {
        r = oval[i].getR();

      } else if (Integer.parseInt(numberOval) <= i && i < Integer.parseInt(numberSquare) + Integer.parseInt(numberOval)) {
        width = square[i - Integer.parseInt(numberOval)].getWidth();
        height = square[i - Integer.parseInt(numberOval)].getHeight();

      } else {
        width = rect[i - Integer.parseInt(numberOval) - Integer.parseInt(numberSquare)].getWidth();
        height = rect[i - Integer.parseInt(numberOval) - Integer.parseInt(numberSquare)].getHeight();

      }

      if (i < Integer.parseInt(numberOval)) { //daire adeti kadar daire oluşturmak için
        g.setColor(shape[i].getColor()); //daire oluştururken rengini veririz
        g.fillOval(shape[i].getX(), shape[i].getY(), r, r); //daire oluştururken konum ve boyutunu veririz
        //fillOval: x , y , x genişliği ,y genişliği
      } else if (Integer.parseInt(numberOval) <= i && i < Integer.parseInt(numberSquare) + Integer.parseInt(numberOval)) { //kare adeti kadar kare oluşturmak için
        g.setColor(shape[i].getColor()); //kare oluştururken rengini veririz
        g.fillRect(shape[i].getX(), shape[i].getY(), width, width); //kare oluştururken konum ve boyutunu veririz
      } else { //dikdörtgen adeti kadar dikdörtgen oluşturmak için
        g.setColor(shape[i].getColor()); //dikdörtgen oluştururken rengini veririz

        if (width == height) { //en ve boy eşit ise rastgele bir sayı ekle (kare olmasını engellemek için)
          Random rn = new Random();

          rect[i - Integer.parseInt(numberOval) - Integer.parseInt(numberSquare)].setHeight(rect[i - Integer.parseInt(numberOval) - Integer.parseInt(numberSquare)].getHeight() + rn.nextInt(50) + 50);
          g.fillRect(shape[i].getX(), shape[i].getY(), width, rect[i - Integer.parseInt(numberOval) - Integer.parseInt(numberSquare)].getHeight()); //Boy değerine rastgele bir sayı ekliyoruz 50-100 arası
        } else {
          g.fillRect(shape[i].getX(), shape[i].getY(), width, height); //dikdörtgen oluştururken konum ve boyutunu veririz
        }
      }

    }

  }

  @Override
  public void actionPerformed(ActionEvent e) { //Start ve Stop butonlarını dinlemesi için  ActionListener interface'inin fonksiyonlarını kullanıyoruz

    if (e.getSource().equals(btnStart)) { //start butonuna tıklanınca
      timer.start(); //hareketi başlatmak için timer'ı başlatır
      btnStart.setEnabled(false); //start butonunu pasif yapar
      btnStop.setEnabled(true); //stop butonunu aktif yapar			 		
    }
    if (e.getSource().equals(btnStop)) { //stop butonuna tıklanınca
      timer.stop(); //hareketi durdurmak için timer'ı başlatır
      btnStart.setEnabled(true); //start butonunu aktif yapar
      btnStop.setEnabled(false); //stop butonunu pasif yapar
    } else {
      repaint(); //ekranda paint işleminin yapılması için kullanılan fonksiyon
    }
  }

  public static boolean InInputOkey(String s) { //dışarıdan girilen değerlerin 0 veya büyük bir sayı olup olmadığını kontrol etmek için 

    if (s == null || s.equals("")) { //dışardan girilen değer boş ise false gönderir
      return false;
    }
    try {
      int iVal = Integer.parseInt(s); //eğer değer sayı ise ancak 0 veya daha büyük değilse false gönderir
      if (!(iVal >= 0)) {
        return false;
      } else { //diğer koşullardan birine takılmadıysa true gönderir		      
        return true;
      }
    } catch (NumberFormatException e) {}
    return false;
  }

  public static void main(String[] args) throws IOException { //class'ın ana sınıfı, try catch kullandığımız için IOException ile bağlantı kurduk

    //kullanıcıya kaç adet daire,kare,dikdörtgen oluşmasını istediğini sorarız, 0 veya pozitif bir sayı girilmediği sürece değeri kabul etmez
    while (!InInputOkey(numberOval)) {
      numberOval = (JOptionPane.showInputDialog("Kaç Adet Daire İstiyorsunuz (Lütfen 0 veya daha büyük bir sayı giriniz) "));
    }
    while (!InInputOkey(numberSquare)) {
      numberSquare = (JOptionPane.showInputDialog("Kaç Adet Kare İstiyorsunuz (Lütfen 0 veya daha büyük bir sayı giriniz) "));
    }
    while (!InInputOkey(numberRectangle)) {
      numberRectangle = (JOptionPane.showInputDialog("Kaç Adet Dikdörtgen İstiyorsunuz (Lütfen 0 veya daha büyük bir sayı giriniz) "));
    }

    totalNumberOfShapes = Integer.parseInt(numberOval) + Integer.parseInt(numberSquare) + Integer.parseInt(numberRectangle); //toplam şekil adetimiz

    Main m = new Main(); //Main nesnemizi türettik

    JFrame window = new JFrame(); //JFrame'den nesne türettik
    window.setContentPane(m); //JFrame'i m nesnemizin içine ekledik

    window.setBounds(400, 0, m.frameX, m.frameY); //JFrame'in en ve boy değerini verdik
    window.setTitle("Swing Job"); //JFrame'in başlığı
    window.setVisible(true); //JFrame'i görünür yaptık
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame'i üstteki çarpı tuşuna basarak kapanabilir hale getirdik
    window.setResizable(false); //JFrame'in boyutunu değiştirilemez yaptık

    //   Server s=new Server();

  }

}