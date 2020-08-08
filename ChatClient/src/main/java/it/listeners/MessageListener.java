package it.listeners;

public interface MessageListener extends Listener{
    public void onMessage(String fromLogin, String msgBody);
}
