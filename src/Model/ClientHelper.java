package Model;

import java.io.Serializable;

public class ClientHelper implements Serializable {

    private Client theClient;
    private int responseNumber;
    private String line;
    static final long serialVersionUID = 1;

    public Client getTheClient() {
        return theClient;
    }

    public void setTheClient(Client theClient) {
        this.theClient = theClient;
    }

    public int getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(int responseNumber) {
        this.responseNumber = responseNumber;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }



}
