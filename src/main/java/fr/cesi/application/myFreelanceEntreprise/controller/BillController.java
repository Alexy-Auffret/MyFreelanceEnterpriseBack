package fr.cesi.application.myFreelanceEntreprise.controller;

import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import fr.cesi.application.myFreelanceEntreprise.beans.Client;
import fr.cesi.application.myFreelanceEntreprise.service.BillService;
import fr.cesi.application.myFreelanceEntreprise.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

//Permet la consultation, l’ajout ou la modification d’une facture.
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BillController {
    @Autowired
    BillService billService;
    @Autowired
    ClientService clientService;

    @GetMapping("/factures")
    public List<Bill> listeFactures(){ return billService.selectAll(); }

    @PostMapping("/createFacture")
    public String ajoutFacture(@RequestBody Bill billToCreate) {

        /*Date dateCrea=Date.from(LocalDate.parse(billToCreate.getCreationDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy")).atStartOfDay(ZoneId.systemDefault()).toInstant());*/
        Bill b =  new Bill();
        Client billClient = clientService.selectById(billToCreate.getClient_id());
        b.setClient(billClient);
        b.setAmount(billToCreate.getAmount());
        b.setCreationDate(billToCreate.getCreationDate());
        b.setStep("WAITING");
        b.setVat(billToCreate.getVat());
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été ajoutée.";
    }

    @PostMapping("/updateFacture")
    public String modifierFacture(@RequestBody Bill billToCreate){
        /*Bill b = billService.selectOne(billToCreate.);
        Client billClient = new ClientService().selectById(billToCreate.getClient_id());
        b.setClient(billClient);
        b.setAmount(billToCreate.getAmount());
        b.setCreationDate(billToCreate.getCreationDate());
        b.setVat(billToCreate.getVat());*/
        billToCreate.setClient(clientService.selectById(billToCreate.getClient_id()));
        billService.save(billToCreate);

        return "La facture du client " + billToCreate.getClient().getName() + " d'un montant de " + billToCreate.getAmount() + " € a bien été modifiée.";
    }

    @PostMapping("/payFacture")
    public String payerFacture(@PathVariable int idFacture, @PathVariable Date settlementDate){
        Bill b = billService.selectOne(idFacture);
        b.setSettlementDate(settlementDate);
        b.setStep("DONE");
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été payée.";
    }

    @PostMapping("/leaveFacture")
    public String abbandonnerFacure(@PathVariable int idFacture) {
        Bill b = billService.selectOne(idFacture);
        b.setStep("FAILED");
        billService.save(b);

        return "La facture du client " + b.getClient().getName() + " d'un montant de " + b.getAmount() + " € a bien été abandonnée.";
    }
}
