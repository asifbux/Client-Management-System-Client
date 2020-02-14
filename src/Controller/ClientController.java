package Controller;

import Model.Client;
import Model.ClientHelper;
import View.ClientView;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The type Client controller.
 */
public class ClientController {

    private Socket aSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ClientView theView;
    private ClientHelper clientHelper;


    /**
     * Instantiates a new Client controller.
     *
     * @param serverName the server name
     * @param portNumber the port number
     */
    public ClientController(String serverName, int portNumber) {
        try {
            aSocket = new Socket(serverName, portNumber);
            theView = new ClientView();
            output = new ObjectOutputStream(aSocket.getOutputStream());
            input = new ObjectInputStream(aSocket.getInputStream());
            clientHelper = new ClientHelper();
            attachListeners();

            while (true) {
                clientHelper = (ClientHelper) input.readObject();
                serverResponse(clientHelper.getResponseNumber());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Server Error");
        }

    }

    /**
     * Server response.
     *
     * @param option the option
     * @throws IOException            the io exception
     * @throws ClassNotFoundException the class not found exception
     */
    public void serverResponse(int option) throws IOException, ClassNotFoundException {
        if (option == 1) {// refresh entire page

            theView.setClientData(clientHelper.getClientList());
        }
        if(option == 3) {
            theView.setClientData(clientHelper.getClientList());
            theView.showMessage(clientHelper.getSearchParameter());
        }
    }

    /**
     * Attach listeners.
     */
    public void attachListeners() {
        attachSearchListener();
        attachClearSearchListener();
        attachNewClientListener();
        saveClientListener();
        clearDataListener();
        deleteClientListener();
        attachValidations();
    }

    /**
     * Attach search listener.
     */
    public void attachSearchListener() {
        this.theView.getSearchButton().addActionListener((ActionEvent e) -> {
            ArrayList<Client> list = new ArrayList<Client>();
            clientHelper.getClientList().clear();
            if(theView.getClientIdRadio().isSelected()) {
                try {
                    int id = Integer.parseInt(theView.getInputSearchText().getText());
                    clientHelper.setRequestNumber(31);
                    clientHelper.setSearchParameter(String.valueOf(id));
                    sendObject(clientHelper);
                } catch (NumberFormatException err) {
                    theView.showMessage("Please enter valid ID");
                }
            } else if(theView.getlNameRadio().isSelected()) {
                clientHelper.setRequestNumber(32);
                clientHelper.setSearchParameter(theView.getInputSearchText().getText());
                sendObject(clientHelper);
            } else if(theView.getClientTypeRadio().isSelected()) {
                clientHelper.setRequestNumber(33);
                clientHelper.setSearchParameter(theView.getInputSearchText().getText());
                sendObject(clientHelper);
            }
        });
    }

    /**
     * Attach clear search listener.
     */
    public void attachClearSearchListener() {
        this.theView.getClearSearchButton().addActionListener((ActionEvent e) -> {
            this.theView.clearSearchCriteria();
        });
    }

    /**
     * Attach new client listener.
     */
    public void attachNewClientListener() {
        this.theView.getAddNewButton().addActionListener((ActionEvent e) -> {
            this.theView.clearRightSideData();
        });
    }

    /**
     * Save client listener.
     */
    public void saveClientListener() {
        this.theView.getSaveButton().addActionListener((ActionEvent e) -> {
            Client client = new Client();
            client = theView.getClientData(client);
            if(client.getFirstName().trim().isEmpty() || client.getLastName().trim().isEmpty() || client.getPostalCode().trim().isEmpty()
                    ||client.getClientType().trim().isEmpty() || client.getAddress().trim().isEmpty() || client.getPhoneNumber().trim().equals("-   -")) {
                theView.showMessage("Please enter all fields");
            } else if (client.getFirstName().length() > 20) {
                theView.showMessage("First name field should be less than 20 characters");
            } else if (client.getLastName().length() > 20) {
                theView.showMessage("Last name field should be less than 20 characters");
            } else if (client.getAddress().length() > 50) {
                theView.showMessage("Address field should be less than 50 characters");
            } else {
                clientHelper.getClientList().clear();
                clientHelper.getClientList().add(client);
                clientHelper.setRequestNumber(1);
                theView.clearRightSideData();
                sendObject(clientHelper);
            }
        });
    }

    /**
     * Clear data listener.
     */
    public void clearDataListener() {
        this.theView.getClearInfoButton().addActionListener((ActionEvent e) -> {
            this.theView.clearRightSideData();
        });
    }

    /**
     * Delete client listener.
     */
    public void deleteClientListener() {
        this.theView.getDeleteButton().addActionListener((ActionEvent e) -> {
            int id = theView.getClientToBeDeleted();
            clientHelper.setRequestNumber(2);
            clientHelper.setSearchParameter(String.valueOf(id));
            sendObject(clientHelper);
            //ArrayList<Client> list = mainApplication.deleteClient(id);
            if(clientHelper.getClientList() != null && clientHelper.getClientList().size() > 0) {
                for(int i = 0; i < clientHelper.getClientList().size(); i++) {
                    if(clientHelper.getClientList().get(i).getId() == id) {
                        clientHelper.getClientList().remove(i);
                        theView.setJList(clientHelper.getClientList());
                    }
                }
                theView.clearRightSideData();
            }
        });
    }

    /**
     * Attach validations.
     */
    public void attachValidations() {
        this.theView.getfNameText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (theView.getfNameText().getText().length() >= 20 ){
                    e.consume();
                }
            }
        });
        this.theView.getlNameText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (theView.getlNameText().getText().length() >= 20 ){
                    e.consume();
                }
            }
        });
        this.theView.getAddressText().addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (theView.getAddressText().getText().length() >= 50 ){
                    e.consume();
                }
            }
        });
    }

    /**
     * Send object.
     *
     * @param aClientHelper the a client helper
     */
    public void sendObject(ClientHelper aClientHelper) {
        aClientHelper = new ClientHelper(aClientHelper.getResponseNumber(), aClientHelper.getRequestNumber(), aClientHelper.getSearchParameter(), aClientHelper.getClientList());
        try {
            this.output.writeObject(aClientHelper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        ClientController aClient = new ClientController("10.13.182.7", 9806);
    }
}
