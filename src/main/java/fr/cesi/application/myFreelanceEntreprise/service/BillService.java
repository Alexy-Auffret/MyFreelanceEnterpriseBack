package fr.cesi.application.myFreelanceEntreprise.service;

import fr.cesi.application.myFreelanceEntreprise.beans.Bill;
import fr.cesi.application.myFreelanceEntreprise.beans.Step;
import fr.cesi.application.myFreelanceEntreprise.dao.BillDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BillService {
    @Autowired
    BillDAO billDAO;

    @Autowired
    private ClientService service;

    public List<Bill> selectAll() { return billDAO.findAll();}

    public List<Bill> selectAllJdv() {
        List<Bill> lb = billDAO.findAll();

        lb.removeIf(b-> !b.getStep().equals(Step.DONE.name()));

        lb.forEach(bill -> bill.setClient(service.selectOne(bill.getClient_id())));
        Collections.sort(lb);

        return lb;
    }

    public Bill selectOne(int id) {
        Optional<Bill> b = billDAO.findById(id);

        if (b.isPresent())
            b.get().setClient(service.selectOne(b.get().getClient_id()));

        return b.orElse(null);
    }

    public void save(Bill b) { billDAO.save(b);}

    public List<Bill> getYearBills() {
        LocalDate firstDayInYear = LocalDate.now().withDayOfMonth(1).withMonth(1);

        List<Bill> bills = billDAO.findAll();

        
        bills.forEach(bill -> bill.setClient(service.selectOne(bill.getClient_id())));

        return bills.stream().filter(x -> x.getCreationDate().after(Date.from(firstDayInYear.atStartOfDay(ZoneId.systemDefault()).toInstant()))).collect(Collectors.toList());
    }

    public float getRevenues() {
        List<Bill> bills = getYearBills();

        float revenues = 0;

        for (Bill bill:bills)
            revenues += bill.getAmount();

        return revenues;
    }
}
