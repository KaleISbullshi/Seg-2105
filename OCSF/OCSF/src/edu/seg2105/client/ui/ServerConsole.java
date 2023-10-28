package edu.seg2105.client.ui;

import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.common.ChatIF;
import edu.seg2105.edu.server.backend.EchoServer;

import java.io.IOException;
import java.util.Scanner;

public class ServerConsole implements ChatIF {

    final public static int DEFAULT_PORT = 5555;
    Scanner fromConsole;

    EchoServer server;



    public ServerConsole(  int port)
    {

            server = new EchoServer(port) ;





        // Create scanner object to read from console
        fromConsole = new Scanner(System.in);
    }



    public void display(String message)
    {
        System.out.println("SERVER MSG> " + message);
    }




    public void accept()
    {
        try
        {

            String message;

            while (true)
            {
                message = fromConsole.nextLine();
                server.handleMessageFromServer(message);
            }
        }
        catch (Exception ex)
        {
            System.out.println
                    ("Unexpected error while reading from console!");
        }
    }



    public static void main(String[] args) {
        int port = 0; //Port to listen on

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        } catch (Throwable t) {
            port = DEFAULT_PORT; //Set port to 5555
        }

        EchoServer sv = new EchoServer(port);

        try {
            sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }



        ServerConsole chat= new ServerConsole(port);
        chat.accept();

    }

























}
