package it.commands;

import it.core.CmdExecutor;
import it.core.ServerWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HandleMessageTopic implements CmdExecutor {
    Map<String,ServerWorker> serverWorkers;
    String workerName;

    public HandleMessageTopic(Map<String,ServerWorker> serverWorkers, String workerName, OutputStream outputStream) {
        this.serverWorkers = serverWorkers;
        this.workerName = workerName;
    }

    @Override
    public void execute(String[] arguments) throws IOException {
        String topic = arguments[0];
        final boolean hasTopic = serverWorkers.get(workerName)
                .getTopicSet().contains(topic);
        if (!hasTopic) {
            return;
        }

        String body = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));

        serverWorkers.values().stream()
                .filter(worker -> worker.getTopicSet().contains(topic) && (!worker.getWorkerName().equalsIgnoreCase(workerName)))
                .forEach(worker -> {
                    try {
                        worker.send("msg_topic " + topic + " " + "user " + workerName + " " + body);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

}

