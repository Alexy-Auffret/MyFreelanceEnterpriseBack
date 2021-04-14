package fr.cesi.myFreelanceEntreprise.service;

import fr.cesi.myFreelanceEntreprise.beans.Bill;
import fr.cesi.myFreelanceEntreprise.dao.BillDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class BillService {
    @Autowired
    BillDAO billDAO;

    public List<Bill> selectAll() { return billDAO.findAll();}

    public Bill selectOne(int id) {
        Optional<Bill> b = billDAO.findById(id);
        return b.orElse(null);
    }

    public void save(Bill b) { billDAO.save(b);}

}
