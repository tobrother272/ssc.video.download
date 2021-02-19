/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ssc.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import ssc.socket.ToolSocket;

/**
 *
 * @author topman garbuja,It represents each new connection
 */
public class TaskClientConnection implements Runnable {

    Socket socket;
    public boolean isShudown(){
        return socket.isClosed();
    }
    public void shutdown(){
        try {
             socket.close(); 
        } catch (Exception e) {
        }
    }
    // Create data input and output streams
    DataInputStream input;
    DataOutputStream output;
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskClientConnection(Socket socket) {
        this.socket = socket;
    }
    String message = "";

    public String currentMessage() {
        return message;
    }

    public void setCurrentMessage(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        try {

            // Create data input and output streams
            input = new DataInputStream(
                    socket.getInputStream());
            output = new DataOutputStream(
                    socket.getOutputStream());
            while (true) {
                if (!socket.isConnected()) {
                    System.out.println("Mất kết nối client ");
                    break;
                }
                if (socket.isInputShutdown()) {
                    System.out.println("isInputShutdown client ");
                    break;
                }
                if (socket.isOutputShutdown()) {
                    System.out.println("isOutputShutdown client ");
                    break;
                }
                if (socket.isClosed()) {
                    System.out.println("isClosed client ");
                    break;
                }
                // Get message from the client
                
                if (name.length() == 0) {
                    try {
                        String message= input.readUTF();
                        if (message.startsWith("#connected#")) {
                            name= message.replace("#connected#", "");
                            ToolSocket.getInstance().showNotifi(name);
                        }
                    } catch (Exception e) {
                    }
                }
                try {
                    message=input.readUTF();  
                   // System.out.println("nhận message "+message);
                } catch (Exception e) {
                   // System.out.println("exception client ");
                    break;
                }

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

        }
    }

    //send message back to client
    public void sendMessage(String message) {
        try {
            //System.out.println("#" + "#" + message);
            output.writeUTF(message);
            output.flush();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }
    }
}
