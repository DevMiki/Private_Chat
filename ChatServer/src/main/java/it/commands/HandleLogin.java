package it.commands;

import com.google.common.collect.HashBiMap;
import it.core.CmdExecutor;
import it.core.ServerWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;


public class HandleLogin implements CmdExecutor {

    private OutputStream outputStream;
    private Map<String,ServerWorker> serverWorkers;
    private Map<String, String> credentials;
    private String workerName;

    public HandleLogin(OutputStream outputStream, Map<String,ServerWorker> serverWorkers, Map<String, String> credentials, String workerName) {
        this.outputStream = outputStream;
        this.serverWorkers = serverWorkers;
        this.credentials = credentials;
        this.workerName = workerName;
    }

    @Override
    public void execute(String[] arguments) throws IOException {

        if (arguments.length == 2) {
            String login = arguments[0];
            String password = arguments[1];
            //credentials are first added in ServerWorker after initialization
            if (password.equalsIgnoreCase(credentials.get(login))) {
                outputStream.write(String.format("login successful %s\n",workerName).getBytes());
                outputStream.write(("My name is: " + workerName + "\n").getBytes());

                serverWorkers.values()
                        .stream()
                        .filter(serverWorker -> !serverWorker.getWorkerName().equals(workerName))
                        .forEach(worker -> {
                            try {
                                worker.send("Online there's: " + workerName + "\n");
                                outputStream.write((String.format("Online there's: %s\n",worker.getWorkerName())).getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

//                serverWorkers.stream().filter(serverWorkers -> !login.equals(serverWorkers.getUsername())).forEach(serverWorker -> {
//                    try {
//                        serverWorker.send("Online there's: " + workerName + "\n");
//                        outputStream.write(("Online there's: " + serverWorker.getWorkerName() + "\n").getBytes());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });


            } else {
                outputStream.write("error login\n".getBytes());
            }
        } else {
            outputStream.write("I don't know what you wrote \n".getBytes());
        }
    }
}
