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

public class ClientController {

    private Socket aSocket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private ClientView theView;
    private ClientHelper clientHelper;


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

    public void serverResponse(int option) throws IOException, ClassNotFoundException {
        if (option == 1) {// refresh entire page
            theView.setClientData(clientHelper.getClientList());
        }
    }

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
            boolean isCheck = false;
            if(theView.getClientIdRadio().isSelected()) {
                try {
                    int id = Integer.parseInt(theView.getInputSearchText().getText());
                    clientHelper.setRequestNumber(31);
                    clientHelper.setSearchParameter(String.valueOf(id));
                    sendObject(clientHelper);
                } catch (NumberFormatException err) {
                    theView.showMessage("Please enter valid ID");
                    isCheck = true;
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
//            if(list.size() == 0 && !isCheck) {
//                theView.showMessage("Please enter valid search criteria");
//            }
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
//        this.theView.getSaveButton().addActionListener((ActionEvent e) -> {
//            Client model = new Client();
//            model = theView.getClientData(model);
//            if(model.getFirstName().trim().isEmpty() || model.getLastName().trim().isEmpty() || model.getPostalCode().trim().isEmpty()
//                    ||model.getClientType().trim().isEmpty() || model.getAddress().trim().isEmpty() || model.getPhoneNumber().trim().equals("-   -")) {
//                theView.showMessage("Please enter all fields");
//            } else if (model.getFirstName().length() > 20) {
//                theView.showMessage("First name field should be less than 20 characters");
//            } else if (model.getLastName().length() > 20) {
//                theView.showMessage("Last name field should be less than 20 characters");
//            } else if (model.getAddress().length() > 50) {
//                theView.showMessage("Address field should be less than 50 characters");
//            } else {
//                if(model.getId() != 0) {
//                    mainApplication.updateClient(model);
//                } else {
//                    mainApplication.addClient(model);
//                }
//                theView.clearRightSideData();
//            }
//            //mainApplication.addOrUpdateClient();
//        });
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
//        this.theView.getDeleteButton().addActionListener((ActionEvent e) -> {
//            int id = theView.getClientToBeDeleted();
//            ArrayList<Client> list = mainApplication.deleteClient(id);
//            if(list != null && list.size() > 0) {
//                for(int i = 0; i < list.size(); i++) {
//                    if(list.get(i).getId() == id) {
//                        list.remove(i);
//                        theView.setJList(list);
//                    }
//                }
//                theView.clearRightSideData();
//            }
//        });
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
    public void sendObject(ClientHelper aClientHelper) {
        aClientHelper = new ClientHelper(aClientHelper.getResponseNumber(), aClientHelper.getRequestNumber(), aClientHelper.getSearchParameter(), aClientHelper.getClientList());
        try {
            this.output.writeObject(aClientHelper);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) throws IOException {
        ClientController aClient = new ClientController("localhost", 9806);
    }
}
