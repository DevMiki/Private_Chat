package it.core;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerWorker extends Thread {
    //Name given by initialization
    private String workerName;
    private String username;
    private String password;
    //Origin server passed with this in the SW instance
    private Server server;
    //I did accept on it
    private Socket clientSocket;
    private Set<String> topicSet = new HashSet<>();


    public Set<String> getTopicSet() {
        return topicSet;
    }

    public ServerWorker(Socket clientSocket, Server server) {
        this.workerName = "";
        this.server = server;
        this.clientSocket = clientSocket;
    }


    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getWorkerName() {
        return workerName;
    }

    public void handleClientSocket() throws IOException {

        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        InitializeUser(bufferedReader, server);
        askQuestion(workerName);
        final CmdExecutorFactory workerFactory = new CmdExecutorFactory(clientSocket.getOutputStream(), server.getServerWorkers(), clientSocket, server.getCredentials(), workerName);

        String line;
        while ((line = bufferedReader.readLine()) != null) {

            final String[] tokenizedCommand = line.split(" ");
            final String command = tokenizedCommand[0];
            final String[] args = Arrays.copyOfRange(tokenizedCommand, 1, tokenizedCommand.length);

            if (Commands.isCommand(command)) {
                workerFactory.getExecutor(command).execute(args);
            } else {
                send("Invalid input, retry \n");
            }
            askQuestion(workerName);
        }
    }

    private void InitializeUser(BufferedReader bufferedReader, Server server) throws IOException {
        System.out.println("Starting user initialize");
        send("Hi, tell me your name ");
        workerName = bufferedReader.readLine();
        send("Choose a username: ");
        username = bufferedReader.readLine();
        send("Choose a password: ");
        password = bufferedReader.readLine();
        server.getCredentials().put(username, password);

        server.getServerWorkers().put(workerName, this);
    }

    public void send(String msg) throws IOException {
        clientSocket.getOutputStream().write((msg + "\n").getBytes());
    }


    private void askQuestion(String workerName) throws IOException {
        send("What are you gonna do " + workerName + "? Or type 'logout' to exit:");
    }


}
