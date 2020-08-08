package it.commands;

import it.core.CmdExecutor;
import it.core.ServerWorker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class HandleLeave implements CmdExecutor {

    private Map<String, ServerWorker> serverWorkers;
    private String workerName;

    public HandleLeave(Map<String, ServerWorker> serverWorkers, String workerName) {
        this.serverWorkers = serverWorkers;
        this.workerName = workerName;
    }


    @Override
    public void execute(String[] arguments) throws IOException {

        serverWorkers.get(workerName).getTopicSet().remove(arguments[0]);
        serverWorkers.get(workerName).send(String.format("topic_removed %s \n",arguments[0]));
    }
}
