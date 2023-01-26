package com.se;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MultiClientServer extends Thread {

    public static void main(String[] args) {
        MultiClientServer server = new MultiClientServer();
        server.start();
    }

    private Integer PORT = 8080;
    final private AtomicInteger CLIENT_ID = new AtomicInteger(0);
    private ArrayList<ServerThread> connections = new ArrayList<>();

    public MultiClientServer() {}

    public MultiClientServer(Integer port) {
        this.PORT = port;
    }

    public void start() {

        ServerSocket serverSocket = null;
        try {
            serverSocket= new ServerSocket(PORT);
            // Server start message
            System.out.printf("""
                    Server listening on port %s
                    inet address: %s
                    local port: %s
                    socket address: %s
                    Domain name: %s
                    Host address: %s
                    Host name: %s                   
                    """,
                    PORT,
                    serverSocket.getInetAddress(),
                    serverSocket.getLocalPort(),
                    serverSocket.getLocalSocketAddress(),
                    serverSocket.getInetAddress().getCanonicalHostName(),
                    serverSocket.getInetAddress().getHostAddress(),
                    serverSocket.getInetAddress().getHostName()
            );
        }catch (IOException e) {
            System.out.println("Could not listen on port: " + PORT);
            System.exit(-1);
        }

        Socket clientSocket = null;

        while (true) {
            try {
                /* Blocking method to listen for a connection and accept it */
                clientSocket = serverSocket.accept();
                System.out.printf("""
                        
                        Client Socket created
                        Inet address: %s
                        local socket address: %s
                        remote port: %s
                        Domain name: %s
                        Host address: %s
                        Host name: %s
                        
                        """,
                        clientSocket.getInetAddress(),
                        clientSocket.getLocalSocketAddress(),
                        clientSocket.getPort(),
                        clientSocket.getInetAddress().getCanonicalHostName(),
                        clientSocket.getInetAddress().getHostAddress(),
                        clientSocket.getInetAddress().getHostName()
                        );

                var id = CLIENT_ID.addAndGet(1);

                System.out.println("Client " + id + " connected to port " + PORT);

                // Create a new server thread to interact with the client
                ServerThread serverThread = new ServerThread(clientSocket, id);
                connections.add(serverThread);

                // Run the server thread
                serverThread.start();

            } catch (IOException e) {
                System.out.println("Accept failed on port " + PORT);
                System.exit(-1);
            }
        }
    }
}
