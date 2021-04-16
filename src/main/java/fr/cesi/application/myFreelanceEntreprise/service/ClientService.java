package fr.cesi.application.myFreelanceEntreprise.service;

import fr.cesi.application.myFreelanceEntreprise.beans.Client;
import fr.cesi.application.myFreelanceEntreprise.dao.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ClientService {
    @Autowired
    ClientDAO clientDAO;

    public List<Client> selectAll() {return clientDAO.findAll();}

    public Client selectOne(int id){
        Optional<Client> c = clientDAO.findById(id);
        return c.orElse(null);
    }

    public void save(Client c){ clientDAO.save(c);}

    public void addClient(String name, String adresse, String phoneNumber, boolean active) {
        Client client = new Client();
        client.setName(name);
        client.setAddress(adresse);
        client.setPhoneNumber(phoneNumber);
        client.setActive(active);

        save(client);
    }

    public void updateClient(int id, String name, String adresse, String phoneNumber, boolean active) {
        Optional<Client> client = clientDAO.findById(id);

        if (client.isPresent()) {
            client.get().setName(name);
            client.get().setAddress(adresse);
            client.get().setPhoneNumber(phoneNumber);
            client.get().setActive(active);
            
            save(client.get());
        }
        else
            addClient(name, adresse, phoneNumber, active);
    }

    public boolean archiveClient(int id) {
        Optional<Client> client = clientDAO.findById(id);

        if (client.isPresent()) {
            client.get().setActive(false);

            save(client.get());

            return true;
        }
        else
            return false;
    }


}
