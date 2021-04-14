package fr.cesi.myFreelanceEntreprise.beans;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class Bill implements Serializable, Comparable<Bill> {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private int idClient;
    @ManyToOne
    private Client client;
    private float amount;
    private Timestamp creationDate;
    private Timestamp settlementDate;
    private Step step;
    private Vat vat;

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

    public Timestamp getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getSettlementDate() {
        return this.settlementDate;
    }

    public void setSettlementDate(Timestamp settlementDate) {
        this.settlementDate = settlementDate;
    }

    public Step getStep() {
        return this.step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Vat getVat() {
        return this.vat;
    }

    public void setVat(Vat vat) {
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
