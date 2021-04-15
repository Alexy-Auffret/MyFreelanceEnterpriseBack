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
}
