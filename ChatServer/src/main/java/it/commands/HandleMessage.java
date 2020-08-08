package it.commands;

import it.core.CmdExecutor;
import it.core.ServerWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HandleMessage implements CmdExecutor {
    Map<String, ServerWorker> workerList;
    String workerName;

    public HandleMessage(Map<String, ServerWorker> workerList, String workerName) {
        this.workerList = workerList;
        this.workerName = workerName;
    }

    @Override
    public void execute(String[] arguments) throws IOException {
        String sendTo = arguments[0];
        String msgBody = String.join(" ", Arrays.copyOfRange(arguments, 1, arguments.length));
        workerList.get(sendTo).send("msg_received " + workerName + " " + msgBody);
    }
}
