package it.core;


import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Server {
    private int port;
    private Map<String,ServerWorker> serverWorkers = new HashMap<>();
    private Map<String,String> credentials = new HashMap<>();

    public Server(int port){
        this.port = port;
    }

    public Map<String,ServerWorker> getServerWorkers() {
        return serverWorkers;
    }

    public Map<String, String> getCredentials() {
        return credentials;
    }


    public void startConnection() {

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                System.out.println("Waiting for a client to connect");
                Socket clientsocket = serverSocket.accept();
                System.out.println("Finally someone has arrived! It's name is: " +clientsocket);
                ServerWorker serverWorker = new ServerWorker(clientsocket,this);
                serverWorker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
