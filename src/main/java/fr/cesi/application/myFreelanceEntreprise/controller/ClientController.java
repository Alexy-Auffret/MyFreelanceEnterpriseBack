package fr.cesi.application.myFreelanceEntreprise.controller;

import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import fr.cesi.application.myFreelanceEntreprise.beans.Client;
import fr.cesi.application.myFreelanceEntreprise.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

//Permet la consultation, l’ajout, la modification et la suppression d’un client.
@Controller
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public List<Client> listeFactures(){ return clientService.selectAll(); }

}
