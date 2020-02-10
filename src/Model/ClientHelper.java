package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientHelper implements Serializable {

    private int responseNumber;
    private int requestNumber;
    private String searchParameter;
    private ArrayList<Client> clientList;
    static final long serialVersionUID = 1;

    public ClientHelper() {
        clientList = new ArrayList<Client>();
    }

    public ClientHelper(int responseNumber, int requestNumber, String searchParameter, ArrayList<Client> clientList) {
        this.responseNumber = responseNumber;
        this.requestNumber = requestNumber;
        this.searchParameter = searchParameter;
        this.clientList = clientList;
    }


    public int getResponseNumber() {
        return responseNumber;
    }

    public void setResponseNumber(int responseNumber) {
        this.responseNumber = responseNumber;
    }

    public int getRequestNumber() {
        return requestNumber;
    }

    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    public ArrayList<Client> getClientList() {
        return clientList;
    }

    public void setClientList(ArrayList<Client> clientList) {
        this.clientList = clientList;
    }

    public String getSearchParameter() {
        return searchParameter;
    }

    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }
}
