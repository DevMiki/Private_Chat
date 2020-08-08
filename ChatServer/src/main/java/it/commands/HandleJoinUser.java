package it.commands;

import it.core.CmdExecutor;
import it.core.ServerWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HandleJoinUser implements CmdExecutor {

    private Map<String, ServerWorker> serverWorkers;
    private OutputStream outputStream;

    public HandleJoinUser(Map<String, ServerWorker> serverWorkers, OutputStream outputStream) {
        this.serverWorkers = serverWorkers;
        this.outputStream = outputStream;
    }

    @Override
    public void execute(String[] arguments) throws IOException {
        String workerName = arguments[0];
        String topic = arguments[1];
        serverWorkers.get(workerName).getTopicSet().add(topic);
        serverWorkers.get(workerName).send(String.format("topic_added %s",topic));
    }

}
