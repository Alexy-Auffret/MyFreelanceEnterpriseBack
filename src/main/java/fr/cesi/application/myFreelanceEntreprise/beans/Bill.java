package fr.cesi.application.myFreelanceEntreprise.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.cesi.application.myFreelanceEntreprise.service.ClientService;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Bill implements Serializable, Comparable<Bill> {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private Client client;

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    @Transient
    @JsonProperty("idClient")
    private int client_id;
    private float amount;
    private Date creationDate;
    private Date settlementDate;
    private String step;
    private float vat;

    public Client getClient() {
        /*ClientService cl = new ClientService();
        return cl.selectOne(this.idClient);*/
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }




    public float getAmount() {
        return this.amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getSettlementDate() {
        return this.settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getStep() {
        return this.step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public float getVat() {
        return this.vat;
    }

    public void setVat(float vat) {
        this.vat = vat;
    }

    @Override
    public int compareTo(Bill b) {
        if (getCreationDate() == null || b.getCreationDate() == null){
            return 0;
        }
        return getCreationDate().compareTo(b.getCreationDate());
    }
}
