package fr.cesi.myFreelanceEntreprise.dao;

import fr.cesi.myFreelanceEntreprise.beans.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillDAO extends JpaRepository<Bill, Integer> {
}
