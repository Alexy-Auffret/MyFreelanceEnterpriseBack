package fr.cesi.application.myFreelanceEntreprise.controller;

import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import fr.cesi.application.myFreelanceEntreprise.beans.Client;
import fr.cesi.application.myFreelanceEntreprise.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

//Permet la consultation, l’ajout, la modification et la suppression d’un client.
@Controller
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<Client> listeFactures(){ return clientService.selectAll(); }

    @PostMapping("/createClient")
    public String ajouterClient(@PathVariable String name, String adresse, String phoneNumber, boolean active){
        Client c = new Client();
        c.setName(name);
        c.setAddress(adresse);
        c.setPhoneNumber(phoneNumber);
        c.setActive(active);
        clientService.save(c);

        return "Le client " + c.getName() + " a bien été ajouté.";
    }

    @PostMapping("/updateClient")
    public String modifierClient(@PathVariable int id, String name, String adresse, String phoneNumber, boolean active){
        Client c = clientService.selectOne(id);
        c.setName(name);
        c.setAddress(adresse);
        c.setPhoneNumber(phoneNumber);
        c.setActive(active);
        clientService.save(c);

        return "Le client " + c.getName() + " a bien été modifié.";
    }
}
