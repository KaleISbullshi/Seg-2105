package edu.seg2105.edu.server.backend;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 


import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.ui.ClientConsole;
import edu.seg2105.client.ui.ServerConsole;
import ocsf.server.*;

import java.io.IOException;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 */
public class EchoServer extends AbstractServer {
  //Class variables *************************************************

  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  static String extractedContent;

  String loginKey = "loginID";

  //Constructors ****************************************************

  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) {
    super(port);
  }




  public void handleMessageFromServer(String message)
  {
    try
    {
      if(message.startsWith("#")) {
        handleCommand(message);
      }
      else {

        display(message);


        sendToAllClients(message);

      }


    }
    catch(IOException e)
    {
      System.out.println
              ("Could not send message to server.  Terminating client.");
      quit();
    }
  }

  private void quit() {

      System.exit(0);

  }

  public void handleCommand(String command) throws IOException {
    if (command.equals("#quit")){
      quit();

    }
    else if (command.equals("#stop")){
      stopListening();

    }
    else if (command.equals("#close")){
      close();


    }
    else if (command.equals("#setport")){
      setPort(getPort());

    }
    else if (command.equals("#start")){
      listen();


    }
    else if (command.equals("#getport")){
      getPort();


    }


  }


  //Instance methods ************************************************

  /**
   * This method handles any messages received from the client.
   *
   * @param msg    The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
  (Object msg, ConnectionToClient client) {
    System.out.println("Message received: " + msg + " from " + client);

    String message = msg.toString();
    if (message.startsWith("#login")) {


      int startIndex = message.indexOf("<");
      int endIndex = message.indexOf(">");
      String extractedContent = message.substring(startIndex + 1, endIndex);
//      System.out.println(extractedContent);



      String loginID = extractedContent;
      client.setInfo(loginKey, loginID);



    } else {

      String loginID1 = (String) client.getInfo(loginKey);

      String prefixedMessage = loginID1 + ": " + message;

      this.sendToAllClients(  prefixedMessage);
    }
  }



  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted() {
    System.out.println
            ("Server listening for connections on port " + getPort());
  }

  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped() {
    System.out.println
            ("Server has stopped listening for connections.");
  }


  /**
   * Hook method called each time a new client connection is
   * accepted. The default implementation does nothing.
   *
   * @param client the connection connected to the client.
   */
  protected void clientConnected(ConnectionToClient client) {
    System.out.println(client.getName() + " is connected to the grand server");


  }

  /**
   * Hook method called each time a client disconnects.
   * The default implementation does nothing. The method
   * may be overridden by subclasses but should remains synchronized.
   *
   * @param client the connection with the client.
   */
  synchronized protected void clientDisconnected(
          ConnectionToClient client) {

    System.out.println(client.getName() + " is disconnected from the grand server");

  }

  public void display(String message) {
    System.out.println("SERVER MSG> " + message);
  }



}



  //Class methods ***************************************************

  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *

   */


//End of EchoServer class
