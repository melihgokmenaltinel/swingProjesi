package my_project;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class mainPage extends JPanel implements ActionListener {

  final int frameX = 400; //JFrame nesnesinin eni 
  final int frameY = 300; //JFrame nesnesinin boyu 
  private JButton btnServer; //işlemi başlatmak için kullancağımız buton
  private JButton btnClient; //işlemi durdurmak için kullancağımız buton

  public mainPage() {

    setLayout(null); //butonun ekrandaki konumlarını belirtmek için kullanılan kod, JFrame'e ait bir fonksiyon

    btnServer = new JButton("Server"); //İşlemi başlatmak için tıkladığımız butonun üzerindeki metni veriyoruz
    btnServer.setBounds(100, 100, 100, 40); //İşlemi başlatmak için tıkladığımız butonun konum ve genişlik bilgileri
    btnServer.addActionListener((ActionListener) this); //İşlemi başlatmak için tıkladığımız butonun Action Listener ile dinlenmesi ve çalışabilmesi için 

    btnClient = new JButton("Client"); //İşlemi durdurmak için tıkladığımız butonun üzerindeki metni veriyoruz
    btnClient.setBounds(200, 100, 100, 40); //İşlemi durdurmak için tıkladığımız butonun konum ve genişlik bilgileri
    btnClient.addActionListener((ActionListener) this); //İşlemi başlatmak için tıkladığımız butonun Action Listener ile dinlenmesi ve çalışabilmesi için 

    add(btnServer); //İşlemi başlatmak için tıkladığımız butonu JFareme'e ekliyoruz
    add(btnClient); //İşlemi durdurmak için tıkladığımız butonun JFareme'e ekliyoruz

  }

  public void actionPerformed(ActionEvent e) { //Start ve Stop butonlarını dinlemesi için  ActionListener interface'inin fonksiyonlarını kullanıyoruz

    if (e.getSource().equals(btnServer)) { //start butonuna tıklanınca

      // Server'ın çalışması ile görsel kısmın çalışmasını farklı thread'lere verdik bu sayede birbirlerini kesmeyecek ve aynı anda çalışacaklar
      Thread t_call_server = new Thread() {
        public void run() {
          Server s = new Server();
        }
      };

      t_call_server.start();

      Thread t_call_main = new Thread() {
        public void run() {
          try {
            Main.main(null);
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } // Şekillerin ve server'ın kodlarını çalıştırmak için
        }
      };

      t_call_main.start();

    }
    if (e.getSource().equals(btnClient)) { //stop butonuna tıklanınca

      Thread t_call_client = new Thread() {
        public void run() {
          try {
            Client.main(null); //client'ın kodlarını çalıştırmak için 
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          } // Şekillerin ve server'ın kodlarını çalıştırmak için
        }
      };

      t_call_client.start();

    } else {
      repaint(); //ekranda paint işleminin yapılması için kullanılan fonksiyon
    }
  }

  public static void main(String[] args) {

    mainPage m = new mainPage(); //Main nesnemizi türettik

    JFrame window = new JFrame(); //JFrame'den nesne türettik
    window.setContentPane(m); //JFrame'i m nesnemizin içine ekledik

    window.setBounds(0, 0, m.frameX, m.frameY); //JFrame'in en ve boy değerini verdik
    window.setTitle("Server/Client Choose"); //JFrame'in başlığı
    window.setVisible(true); //JFrame'i görünür yaptık
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //JFrame'i üstteki çarpı tuşuna basarak kapanabilir hale getirdik
    window.setResizable(false); //JFrame'in boyutunu değiştirilemez yaptık

  }

}