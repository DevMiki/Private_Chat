package it.commands;

import it.core.CmdExecutor;
import it.core.ServerWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;


public class HandleLogout implements CmdExecutor {
    private OutputStream outputStream;
    private Socket clientSocket;
    private Map<String,ServerWorker> serverWorkers;
    private String workerName;


    public HandleLogout(OutputStream outputStream, Socket clientSocket, Map<String,ServerWorker> serverWorkers, String workerName) {
        this.outputStream = outputStream;
        this.clientSocket = clientSocket;
        this.serverWorkers = serverWorkers;
        this.workerName = workerName;
    }

    @Override
    public void execute(String[] arguments) throws IOException {
        outputStream.write("Ok bye bye!\n".getBytes());

        serverWorkers.values().stream()
                .filter(serverWorker -> !serverWorker.getWorkerName().equalsIgnoreCase(workerName))
                .forEach(serverWorker -> {
                    try {
                        serverWorker.send("Offline user: " + workerName + "\n");
//                        outputStream.write(("Offline user: " + serverWorker.getWorkerName() + "\n").getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

        clientSocket.close();
    }
}
