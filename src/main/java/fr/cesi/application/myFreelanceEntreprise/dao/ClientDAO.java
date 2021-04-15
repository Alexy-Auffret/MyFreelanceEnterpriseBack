package fr.cesi.application.myFreelanceEntreprise.dao;

import fr.cesi.application.myFreelanceEntreprise.beans.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Integer> {
}
