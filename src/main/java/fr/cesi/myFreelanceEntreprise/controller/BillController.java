package fr.cesi.myFreelanceEntreprise.controller;

import fr.cesi.myFreelanceEntreprise.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//Permet la consultation, l’ajout ou la modification d’une facture.
@Controller
public class BillController {
    @Autowired
    BillService billService;


    //ToDo : Je peux créer une facture.
}
