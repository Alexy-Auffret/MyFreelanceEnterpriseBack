package fr.cesi.myFreelanceEntreprise.controller;


import fr.cesi.myFreelanceEntreprise.beans.Bill;
import fr.cesi.myFreelanceEntreprise.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

//Permet l’affichage chronologique des recettes encaissées et la facture correspondante.
@Controller
public class JdvController {
    @Autowired
    BillService billService;

    @GetMapping("liste")
    public String affiche() {
        List<Bill> lb = billService.selectAllJdv();
        return "listeFactures";
    }
}
