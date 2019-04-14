package main;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Klient {

    public static final int PORT=5000;
    public static final String IP="127.0.0.1";
    BufferedReader bufferedReader;
    String imie;

    public static void main(String[] args) {
        Klient klient=new Klient();
        klient.startKlient();
    }

    public void startKlient(){
        Scanner sc= new Scanner(System.in);
        System.out.println("Podaj imie: ");
        imie=sc.nextLine();

        try {
            Socket socket = new Socket(IP, PORT);
            System.out.println("Podlączono do "+socket);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            Thread thread = new Thread(new Odbiorca());
            thread.start();

            while(true){
                System.out.println(">>");
                String str= sc.nextLine();
                if(!str.equalsIgnoreCase("q")){
                    printWriter.println(imie+ " "+ str);
                } else {
                    printWriter.println(imie+ " rozłączył się");
                    printWriter.flush();
                    printWriter.close();
                    sc.close();
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class Odbiorca implements Runnable{

        @Override
        public void run() {
            String wiadomosc;

            try {
                while ((wiadomosc = bufferedReader.readLine()) != null) {
                    String substring[]=wiadomosc.split(":");
                    if(!substring[0].equalsIgnoreCase(imie)){
                        System.out.println(wiadomosc);
                        System.out.println(">>  ");
                    }
                }
            }catch (Exception e){

            }
        }
    }
}


