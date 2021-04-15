package fr.cesi.application.myFreelanceEntreprise.beans;

public enum Step {
    CREATED, /* Facture créée en attente d'action */
    DENIED, /* Facture refusée */
    WAITING, /* Facture en attente de paiement */
    DONE, /* Facture payée */
    FAILED /* Facture annulée */
}
