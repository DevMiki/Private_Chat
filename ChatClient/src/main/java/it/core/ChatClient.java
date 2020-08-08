package it.core;

import it.listeners.Listener;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public class ChatClient {
    String name;
    private Socket socket;
    private String serverName;
    int port;
    private Map<String, List<Listener>> listenersMap = new HashMap<>();
    private Thread thread;

    public String getName() {
        return name;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public ChatClient(String serverName, int port) throws IOException {
        this.serverName = serverName;
        this.port = port;
    }

    public void registerListener(String cmd, Consumer<String[]> handler) {
        listenersMap
                .computeIfAbsent(cmd, new Function<String, List<Listener>>() {
                    @Override
                    public List<Listener> apply(String s) {
                        return new ArrayList<>();
                    }
                })
                .add(new Listener() {
                    @Override
                    public void execute(String[] args) {
                        handler.accept(args);
                    }
                });

//                This is what compute would have done
//                if (!listenersMap.containsKey(cmd)) {
//                    listenersMap.put(cmd, new ArrayList<>());
//                }
//                listenersMap.get(cmd)
    }


    public void login(String name, String username, String password) throws IOException {
        socket.getOutputStream().write((name + "\n").getBytes());
        this.name = name;
        socket.getOutputStream().write((username + "\n").getBytes());
        socket.getOutputStream().write((password + "\n").getBytes());
        socket.getOutputStream().write((String.format("login %s %s \n",username,password)).getBytes());
    }

    public void logout(String username) throws IOException {
        socket.getOutputStream().write((username + "\n").getBytes());
        socket.getOutputStream().write((String.format("logout %s \n",username)).getBytes());
        socket.shutdownInput();
    }

    public void joinTopic(String topic) throws IOException {
        socket.getOutputStream().write(String.format("join %s" +"\n",topic).getBytes());
    }


    public void leaveTopic(String topic) throws IOException {
        socket.getOutputStream().write(String.format("leave %s" +"\n",topic).getBytes());
    }

    public void inviteTopic(String topic, String name) throws IOException {
        socket.getOutputStream().write(String.format("joinu %s %s" +"\n",name,topic).getBytes());
    }


    public boolean connect() {
        try {
            this.socket = new Socket(serverName, port);
            System.out.println("Client connected to the port: " + socket.getLocalPort());
            startReader();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void startReader() {
        thread = new Thread(this::loopReader);
        thread.setName("Loop Thread");
        thread.start();
    }

    private void loopReader() {

        String line;
        try (BufferedReader bufferIn = new BufferedReader(new InputStreamReader(getInputstream()))) {

            while ((line = bufferIn.readLine()) != null) {
                String[] tokenizedLine = line.split(" ");
                String cmd = tokenizedLine[0];
                String[] args = Arrays.copyOfRange(tokenizedLine, 1, tokenizedLine.length);
                System.out.print("il comando è: " + cmd + "  ||  ");
                System.out.println("Gli args sono: " + Arrays.toString(args));


                Optional.ofNullable(listenersMap.get(cmd)).ifPresent(new Consumer<List<Listener>>() {
                    @Override
                    public void accept(List<Listener> listeners){
                        listeners.forEach(listener -> {listener.execute(args);});
                    }
                });

//                What really means writing a consumer
//                Optional.ofNullable(listenersMap.get(cmd)).ifPresent(new ConsumerListener(args));

//                Bad example but working
//                new ArrayList<>(listenersMap.getOrDefault(cmd, new ArrayList<>())).forEach(listener -> listener.execute(args));

            }
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("error in the I/O", ex);
        }
    }


    private InputStream getInputstream() {
        try {
            return socket.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error in the I/O", e);
        }
    }

    private OutputStream getOutputstream() {
        try {
            return socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("error in the I/O", e);
        }
    }

}

