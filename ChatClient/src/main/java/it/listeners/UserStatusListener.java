package it.listeners;

public interface UserStatusListener extends Listener{
    public void online(String login);
    public void offline(String login);
}
