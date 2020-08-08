package it.commands;

import it.core.CmdExecutor;
import it.core.Server;
import it.core.ServerWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class HandleJoin implements CmdExecutor {
    private Map<String, ServerWorker> serverWorkers;
    private String workerName;
    private OutputStream outputStream;

    public HandleJoin(Map<String, ServerWorker> serverWorkers, String workerName, OutputStream outputStream) {
        this.serverWorkers = serverWorkers;
        this.workerName = workerName;
        this.outputStream = outputStream;
    }

    @Override
    public void execute(String[] arguments) throws IOException {

        serverWorkers.get(workerName).getTopicSet().add(arguments[0]);
        serverWorkers.get(workerName).send(String.format("topic_added %s \n",arguments[0]));

    }
}
