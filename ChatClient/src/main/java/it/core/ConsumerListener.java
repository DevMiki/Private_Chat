package it.core;

import it.listeners.Listener;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerListener implements Consumer<List<Listener>> {

    private String[] args;

    public ConsumerListener(String[] args) {
        this.args = args;
    }

    @Override
    public void accept(List<Listener> listeners){
        listeners.forEach(new Consumer<Listener>(){
            @Override
            public void accept(Listener listener){
                listener.execute(args);
            }
        });
    }
}
