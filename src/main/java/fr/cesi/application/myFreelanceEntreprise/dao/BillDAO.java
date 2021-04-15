package fr.cesi.application.myFreelanceEntreprise.dao;

import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDAO extends JpaRepository<Bill, Integer> {
}
