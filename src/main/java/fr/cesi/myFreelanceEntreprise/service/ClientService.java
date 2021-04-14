package fr.cesi.myFreelanceEntreprise.service;

import fr.cesi.myFreelanceEntreprise.beans.Client;
import fr.cesi.myFreelanceEntreprise.dao.ClientDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class ClientService {
    @Autowired
    ClientDAO clientDAO;

    public List<Client> selectAll() {return clientDAO.findAll();}

    public Client selectOne(int id){
        Optional<Client> c = clientDAO.findById(id);
        return c.orElse(null);
    }

    public void save(Client c){ clientDAO.save(c);}
}
