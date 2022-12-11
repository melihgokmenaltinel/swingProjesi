package my_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class Server {

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

  Server() {

    String port = null;

    while (!portOkey(port)) {
      port = (JOptionPane.showInputDialog("Server için Port Bilgisi Giriniz (Lütfen 1023'ten daha büyük bir sayı giriniz) "));
    }

    ///////////////////////// Client - Server Kod Kısmı /////////////////////////////////

    try { //hata yakalamak için try catch
      ServerSocket server = new ServerSocket(Integer.parseInt(port)); //dışardan aldığımız değerler ile bağlantı kuruluyor
      System.out.print("server aktif ");
      Socket client = server.accept();
      
   /*   ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream()); //client'a nesne göndermek için oluşturduğumuz nesne
      ObjectOutputStream outObject;
      outObject = new ObjectOutputStream(out);
      outObject.writeObject(1);
      outObject.flush();*/
      
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		String inputLine, outputLine;

		while ((inputLine = in.readLine()) != null) { 
			System.out.println("istemciden gelen :" + inputLine);
			outputLine = inputLine.toUpperCase(); 
			out.println(outputLine); 		
		}
		

    } catch (BindException e) {
      JOptionPane.showMessageDialog(null, "Server için Başka Bir Port Bilgisi Giriniz, Bu Port Dolu ");
    } catch (IOException e) {
      e.printStackTrace();
    }
    ///////////////////////// Client - Server Kod Kısmı ///////////////////////////////// 

  }

}