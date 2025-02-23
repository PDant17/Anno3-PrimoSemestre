package jms;

import java.io.Serializable;

public class MessageWrapper implements Serializable{
    private static final long serialVersionUID = 1L;
    private int id;
    private boolean restituito;
    
    public MessageWrapper(){
    }

    public MessageWrapper(int id, boolean restituito) {
        this.id = id;
        this.restituito = restituito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean getRestituito() {
        return restituito;
    }

    public void setRestituito(boolean restituito) {
        this.restituito = restituito;
    }
}