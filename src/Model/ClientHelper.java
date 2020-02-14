package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The type Client helper.
 */
public class ClientHelper implements Serializable {

    private int responseNumber;
    private int requestNumber;
    private String searchParameter;
    private ArrayList<Client> clientList;
    /**
     * The Serial version uid.
     */
    static final long serialVersionUID = 1;

    /**
     * Instantiates a new Client helper.
     */
    public ClientHelper() {
        clientList = new ArrayList<Client>();
    }

    /**
     * Instantiates a new Client helper.
     *
     * @param responseNumber  the response number
     * @param requestNumber   the request number
     * @param searchParameter the search parameter
     * @param clientList      the client list
     */
    public ClientHelper(int responseNumber, int requestNumber, String searchParameter, ArrayList<Client> clientList) {
        this.responseNumber = responseNumber;
        this.requestNumber = requestNumber;
        this.searchParameter = searchParameter;
        this.clientList = clientList;
    }


    /**
     * Gets response number.
     *
     * @return the response number
     */
    public int getResponseNumber() {
        return responseNumber;
    }

    /**
     * Sets response number.
     *
     * @param responseNumber the response number
     */
    public void setResponseNumber(int responseNumber) {
        this.responseNumber = responseNumber;
    }

    /**
     * Gets request number.
     *
     * @return the request number
     */
    public int getRequestNumber() {
        return requestNumber;
    }

    /**
     * Sets request number.
     *
     * @param requestNumber the request number
     */
    public void setRequestNumber(int requestNumber) {
        this.requestNumber = requestNumber;
    }

    /**
     * Gets client list.
     *
     * @return the client list
     */
    public ArrayList<Client> getClientList() {
        return clientList;
    }

    /**
     * Sets client list.
     *
     * @param clientList the client list
     */
    public void setClientList(ArrayList<Client> clientList) {
        this.clientList = clientList;
    }

    /**
     * Gets search parameter.
     *
     * @return the search parameter
     */
    public String getSearchParameter() {
        return searchParameter;
    }

    /**
     * Sets search parameter.
     *
     * @param searchParameter the search parameter
     */
    public void setSearchParameter(String searchParameter) {
        this.searchParameter = searchParameter;
    }
}
