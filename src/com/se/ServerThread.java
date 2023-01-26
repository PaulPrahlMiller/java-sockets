package com.se;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread {

    PrintWriter output;
    BufferedReader input;
    Socket clientSocket=null;
    Integer ID;
    List<ServerThread> connections = new ArrayList<>();

    public ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        initSocketIO();
    }

    public ServerThread(Socket clientSocket, Integer clientId) {
        this.clientSocket=clientSocket;
        this.ID = clientId;
        initSocketIO();
    }

    private void initSocketIO() {
        try {
            this.output = new PrintWriter(clientSocket.getOutputStream(), true);
            this.input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        PrintWriter out=null;
        BufferedReader in = null;
        String inputLine, outputLine;

        try {
            out = new PrintWriter( clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException ioe) {
            System.out.println("Failed to create I/O streams for server thread");
            System.exit(-1);
        }

        try {
            while ((inputLine = in.readLine()) != null) {

                System.out.printf("User %d: %s%n", ID, inputLine);

                // Close the connection on "q" input
                if (inputLine.toLowerCase().equals("q"))
                    break;
            }
        } catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }

        try {
            out.println("Client " + ID + " disconnected");
            clientSocket.close();
        } catch (IOException ioe) {
            System.out.println("Disconnection failed.");
            System.exit(-1);
        }
    }
}
