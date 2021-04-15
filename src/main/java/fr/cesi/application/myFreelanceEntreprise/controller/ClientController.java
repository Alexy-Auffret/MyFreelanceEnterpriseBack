package fr.cesi.application.myFreelanceEntreprise.controller;

import fr.cesi.application.myFreelanceEntreprise.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Permet la consultation, l’ajout, la modification et la suppression d’un client.
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;


    //ToDo : Je peux gérer mes clients. La suppression d’un client ne supprime pas les factures émises envers lui.


    //ToDo : Je peux classer mes clients selon différentes critères (CA croissant / décroissant, temps moyen de paiement de factures, …)
}
