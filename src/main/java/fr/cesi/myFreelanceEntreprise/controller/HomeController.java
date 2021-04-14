package fr.cesi.myFreelanceEntreprise.controller;

import fr.cesi.myFreelanceEntreprise.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {
    @Autowired
    BillService billService;


    //ToDo : Gérer les informations de la page d’accueil du site.


    // ToDo : La barre de progression représente le chiffre d’affaires hors taxe encaissé cette année. Un auto-entrepreneur ne peux encaisser plus de 70000 euros par an sans changer de statut.


    //ToDo : Lorsqu’une facture est payée, elle vient augmenter la barre de progression sur la page d’accueil du site.


    //ToDo : Un graphique me permet de voir, mois après mois, le chiffre d’affaires réalisé.


    //ToDo : L’application m’indique le montant des charges URSAFF chaque trimestre (22,2%) et le montant de la TVA. Je peux ainsi savoir, le montant exact de ma rémunération mensuelle.
}
