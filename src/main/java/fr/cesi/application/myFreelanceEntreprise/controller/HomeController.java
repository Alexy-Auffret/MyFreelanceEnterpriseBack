package fr.cesi.application.myFreelanceEntreprise.controller;

import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import fr.cesi.application.myFreelanceEntreprise.service.BillService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class HomeController {
    @Autowired
    BillService billService;

    @RequestMapping(value = "/getMonthBills", method = RequestMethod.GET)
    public List<Bill> getMonthBills() {
        return billService.getMonthBills();
    }
    
    @RequestMapping(value = "/getRevenues", method = RequestMethod.GET)
    public float getRevenues() {
        return billService.getRevenues();
    }

    //ToDo : 5. Un graphique me permet de voir, mois après mois, le chiffre d’affaires réalisé.

    //ToDo : 6. L’application m’indique le montant des charges URSAFF chaque trimestre (22,2%) et le montant de la TVA. Je peux ainsi savoir, le montant exact de ma rémunération mensuelle.
}
