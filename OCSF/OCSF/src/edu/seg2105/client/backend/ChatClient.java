// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package edu.seg2105.client.backend;

import ocsf.client.*;

import java.io.*;

import edu.seg2105.client.common.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************

  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI;
  String loginId;


  //Constructors ****************************************************

  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */

  public ChatClient(String loginId,String host, int port, ChatIF clientUI)
          throws IOException
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginId = loginId;
    openConnection();
  }


  //Instance methods ************************************************

  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg)
  {
    clientUI.display(msg.toString());


  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
    try
    {
      if(message.startsWith("#")) {
        handleCommand(message);
      }
      else {

        sendToServer(message);}
    }
    catch(IOException e)
    {
      clientUI.display
              ("Could not send message to server.  Terminating client.");
      quit();
    }
  }



  public void handleCommand(String command) throws IOException {
    if (command.equals("#quit")){
      quit();

    }
    else if (command.equals("#logoff")){
      closeConnection();


    }
    else if (command.equals("#sethost")){
      setHost(getHost());

    }
    else if (command.equals("#setport")){
      setPort(getPort());

    }
    else if (command.equals("#login")){
      if(!isConnected()){
        openConnection();
      }


    }
    else if (command.equals("#gethost")){
      getHost();

    }
    else if (command.equals("#getport")){
      getPort();

    }

  }

  @Override
  protected void connectionEstablished() throws IOException {
    super.connectionEstablished();


    try {
      sendToServer("#login <" + this.loginId + ">");
    } catch (IOException e) {
    }
  }

  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
}
//End of ChatClient class
