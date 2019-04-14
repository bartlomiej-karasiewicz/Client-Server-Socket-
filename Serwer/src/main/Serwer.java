package main;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Serwer {

    ArrayList klientArrayList;
    PrintWriter printWriter;

    public static void main(String[] args) {
        Serwer s = new Serwer();
        s.startSerwer();

    }

    public void startSerwer(){
        klientArrayList=new ArrayList();

        try {
            ServerSocket serverSocket = new ServerSocket(5000);

            while(true){
                Socket socket=serverSocket.accept();
                System.out.println("Slucham "+serverSocket);
                printWriter = new PrintWriter(socket.getOutputStream());
                klientArrayList.add(printWriter);

                Thread thread = new Thread(new ServerKlient(socket));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class ServerKlient implements Runnable{

        Socket socket;
        BufferedReader bufferedReader;

        public ServerKlient(Socket socketKlient){
            System.out.println("Polaczono.");
            socket=socketKlient;
            try {
                bufferedReader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {

            PrintWriter pw;
            try{
            while ((bufferedReader.readLine()!=null)) {
                System.out.println("Odebrano >>" + bufferedReader.readLine());
                Iterator it=klientArrayList.iterator();
                while (it.hasNext()){
                    pw= (PrintWriter) it.next();
                    pw.println(bufferedReader.readLine());
                    pw.flush();
                }
                }
            } catch (Exception e){

                }
            }

        }
    }


