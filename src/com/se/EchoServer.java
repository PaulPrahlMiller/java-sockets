package com.se;
import java.io.*;
import java.net.*;

public class EchoServer {

    public static void main(String[] args) {
        EchoServer server = new EchoServer();
        server.establish();
    }

    private Integer PORT = 8080;

    public EchoServer() {}

    // Constructor with PORT
    public EchoServer(Integer port) {
        this.PORT = port;
    }

    /**  **/
    public void establish() {

        ServerSocket serverSocket = null;
        Socket clientSocket = null;

        PrintWriter out = null;
        BufferedReader in = null;

        String inputLine, outputLine;

        /* Create a server socket bound to the given port */
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server socket listening on port " + PORT);
        }catch (IOException e) {
            System.out.println("Could not listen on port: " + PORT);
            System.exit(-1);
        }

        /* Listens for a connection to be made to the socket and accepts it */
        try {
            clientSocket = serverSocket.accept();
        }catch (IOException e) {
            System.out.println("Accept failed on port " + PORT);
            System.exit(-1);
        }

        try {

            /* Create output stream writer */
            out = new PrintWriter(
                    clientSocket.getOutputStream(), true);

            /* Input stream */
            in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));

        }catch (IOException ioe) {
            System.out.println("Failed to create I/O streams");
            System.exit(-1);
        }

        /* Read from input stream */
        try {
            while ((inputLine = in.readLine()) != null) {
                out.println(inputLine);
                if (inputLine.equals("Bye."))
                    break;
            }
        }catch (IOException ioe) {
            System.out.println("Failed in reading, writing");
            System.exit(-1);
        }

        /* Close the connection */
        try {
            clientSocket.close();
            serverSocket.close();
        }catch (IOException e) {
            System.out.println("Could not close");
            System.exit(-1);
        }}
}
