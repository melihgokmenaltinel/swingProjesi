package my_project;

//Melih Gökmen ALTINEL, Simsoft Java Yazılım Mühendisliği İş Başvurusu Ödev Çalışması

//Kullanacağımız kodların ihtiyacı olan kütüphanelerimizi import ediyoruz
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Client extends Thread {

  private static PrintWriter out;

public static boolean ipOkey(String s) { //dışarıdan girilen değerlerin 0 veya büyük bir sayı olup olmadığını kontrol etmek için 

    if (s == null || s.equals("")) { //dışardan girilen değer boş ise false gönderir
      return false;
    } else { //diğer koşullardan birine takılmadıysa true gönderir		      
      return true;
    }

    /*try {
        int iVal = Integer.parseInt(s); //eğer değer sayı ise ancak 0 veya daha büyük değilse false gönderir
        if (!(iVal >= 0)) {
            return false;
        } else { //diğer koşullardan birine takılmadıysa true gönderir		      
            return true;
        }
    } catch (NumberFormatException e) {}
    return false;*/
  }

  public static boolean portOkey(String s) { //dışarıdan girilen değerlerin 0 veya büyük bir sayı olup olmadığını kontrol etmek için 

    if (s == null || s.equals("")) { //dışardan girilen değer boş ise false gönderir
      return false;
    }
    try {
      int iVal = Integer.parseInt(s); //eğer değer sayı ise ancak 0 veya daha büyük değilse false gönderir
      if (!(iVal >= 1024)) {
        //socket programlama için 1024 veya daha üstü port kullanılmalı çünkü 0-1023 arası diğer programlar tarafından kullanılıyor olabilir
        return false;
      } else { //diğer koşullardan birine takılmadıysa true gönderir		      
        return true;
      }
    } catch (NumberFormatException e) {}
    return false;
  }

  public static void main(String[] args) throws IOException { //class'ın ana sınıfı, try catch kullandığımız için IOException ile bağlantı kurduk

    String ip = null;
    String port = null;

    while (!ipOkey(ip)) {
      ip = (JOptionPane.showInputDialog("Client için Ip Bilgisi Giriniz"));
    }
    while (!portOkey(port)) {
      port = (JOptionPane.showInputDialog("Client için Port Bilgisi Giriniz (Lütfen 1023'ten daha büyük bir sayı giriniz) "));
    }

	PrintWriter out = null;
	BufferedReader in = null;
	
    try (Socket client = new Socket(ip, Integer.parseInt(port))) {
      System.out.println("Client aktif");
      out = new PrintWriter(client.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      while (true) { //sürekli dinleme yapsın diye sonsuz döngü yazdık

        //serverdan gelen verileri(object gelecek) almak için yazdığımız kodlar
  /*      InputStreamReader isr = new InputStreamReader(client.getInputStream());
        BufferedReader br = new BufferedReader(isr);
        Reader isread = new InputStreamReader(client.getInputStream());
        BufferedReader input = new BufferedReader(isread);
        System.out.println("JFrame object " + input.readLine());*/
        
    	  
		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String userInput;	
		while (!(userInput = stdIn.readLine()).equals("bye")) {
			out.println(userInput);
			System.out.println("Sunucudan gelen: " + in.readLine());
		}

		
      }
    } catch (IOException e) {

      JOptionPane.showMessageDialog(null, "Client , Server'a bağlanamadı Ip ve Port tekrar giriniz");
      ip = null;
      port = null;

      while (!ipOkey(ip)) {
        ip = (JOptionPane.showInputDialog("Client için Ip Bilgisi Giriniz"));
      }
      while (!portOkey(port)) {
        port = (JOptionPane.showInputDialog("Client için Port Bilgisi Giriniz (Lütfen 1023'ten daha büyük bir sayı giriniz) "));
      }
      System.out.println("Client aktif ");

      //	e.printStackTrace();
    }
  }

  public void run() {
    Client cl = new Client();
  }

}