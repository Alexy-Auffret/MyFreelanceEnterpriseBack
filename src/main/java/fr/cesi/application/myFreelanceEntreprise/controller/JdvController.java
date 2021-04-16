package fr.cesi.application.myFreelanceEntreprise.controller;


import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import fr.cesi.application.myFreelanceEntreprise.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//Permet l’affichage chronologique des recettes encaissées et la facture correspondante.
@RestController
@RequestMapping("/api")
public class JdvController {
    @Autowired
    BillService billService;

    @GetMapping("/jdv")
    public List<Bill> listeFacturesPayees() { return billService.selectAllJdv(); }

}
