package fr.cesi.myFreelanceEntreprise.dao;

import fr.cesi.myFreelanceEntreprise.beans.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientDAO extends JpaRepository<Client, Integer> {
}
