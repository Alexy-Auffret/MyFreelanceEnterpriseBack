package fr.cesi.myFreelanceEntreprise.controller;

import fr.cesi.myFreelanceEntreprise.beans.Bill;
import fr.cesi.myFreelanceEntreprise.beans.Client;
import fr.cesi.myFreelanceEntreprise.service.BillService;
import fr.cesi.myFreelanceEntreprise.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

//Permet la consultation, l’ajout ou la modification d’une facture.
@RestController
@RequestMapping("/api")
public class BillController {
    @Autowired
    BillService billService;

    //ToDo: afficher la liste des factures
    @GetMapping("/factures")
    public List<Bill> listeFactures(){ return billService.selectAll(); }

    @GetMapping("/facture/{id}")
    public Bill afficheFacture(@PathVariable int id) { return billService.selectOne(id); }

    //ToDo : Je peux créer une facture.
    public String ajoutFacture(@PathVariable int idClient, float amount, Date creationDate, float vat) {
        Bill b =  new Bill();
        Client billClient = new ClientService().selectOne(idClient);
        b.setClient(billClient);
        b.setAmount(amount);
        b.setCreationDate(creationDate);
        b.setStep("WAITING");
        b.setVat(vat);
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été ajoutée.";
    }

    //ToDo : Modifier une facture
    public String modifierFacture(@PathVariable int idFacture, int idClient, float amount, Date creationDate, float vat){
        Bill b = billService.selectOne(idFacture);
        Client billClient = new ClientService().selectOne(idClient);
        b.setClient(billClient);
        b.setAmount(amount);
        b.setCreationDate(creationDate);
        b.setVat(vat);
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été modifiée.";
    }

    //ToDo : Payer une facture
    public String payerFacture(@PathVariable int idFacture, Date settlementDate){
        Bill b = billService.selectOne(idFacture);
        b.setSettlementDate(settlementDate);
        b.setStep("DONE");
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été payée.";
    }

    //ToDo : Abbandoner une facture
    public String abbandonnerFacure(@PathVariable int idFacture) {
        Bill b = billService.selectOne(idFacture);
        b.setStep("FAILED");
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été abandonnée.";
    }
}
