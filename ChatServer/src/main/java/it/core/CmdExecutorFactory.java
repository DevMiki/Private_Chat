package it.core;

import it.commands.*;

import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

public class CmdExecutorFactory {

    private Map<Commands, CmdExecutor> cmdMap = new HashMap<>();
    //workerName is set in ServerWorker with the first question
    public CmdExecutorFactory(OutputStream outputStream, Map<String,ServerWorker> serverWorkers, Socket clientSocket, Map<String,String> credentials, String workerName) {

        cmdMap.put(Commands.LOGIN, new HandleLogin(outputStream, serverWorkers, credentials, workerName));
        cmdMap.put(Commands.LOGOUT, new HandleLogout(outputStream, clientSocket, serverWorkers, workerName));
        cmdMap.put(Commands.MSG, new HandleMessage(serverWorkers,workerName));
        cmdMap.put(Commands.MSGT, new HandleMessageTopic(serverWorkers, workerName, outputStream));
        cmdMap.put(Commands.JOIN, new HandleJoin(serverWorkers, workerName, outputStream));
        cmdMap.put(Commands.JOINU, new HandleJoinUser(serverWorkers, outputStream));
        cmdMap.put(Commands.LEAVE, new HandleLeave(serverWorkers,workerName));
    }


        public CmdExecutor getExecutor (String command){
            return cmdMap.get(Commands.valueOf(command.toUpperCase()));
        }
    }
