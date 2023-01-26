package com.se;

public class Main {

    public static void main(String[] args) {

	    EchoServer server = new EchoServer();
	    server.establish();

	    Client client = new Client();
	    client.establish();
    }
}
