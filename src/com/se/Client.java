package com.se;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.establish();
    }

    private Integer PORT = 8080;

    public Client() {}

    public Client(Integer port) {
        this.PORT = port;
    }

    public void establish() {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String userInput;
        String serverInput;

        try {
            socket = new Socket("0.0.0.0", PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader( new InputStreamReader(System.in));

        try{
            while ((userInput = stdIn.readLine()) != null) {
                out.println(userInput);
                if (userInput.equals("Bye."))
                    break;
            }
            out.close();
            in.close();
            stdIn.close();
            socket.close();
        } catch (IOException ioe) {
            System.out.println("Failed");
            System.exit(-1);
        }
    }
}


