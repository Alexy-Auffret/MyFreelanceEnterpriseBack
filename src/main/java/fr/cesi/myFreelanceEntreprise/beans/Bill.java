package fr.cesi.myFreelanceEntreprise.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Bill implements Serializable, Comparable<Bill> {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int idClient;
    @ManyToOne
    private Client client;
    private float amount;
    private Date creationDate;
    private Date settlementDate;
    private String step;
    private float vat;

    public Client getClient() {
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

    public int getIdClient() {
        return this.idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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
