package fr.cesi.application.myFreelanceEntreprise.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GestionBill {

    private static BillService billService;

    public static BillService getBillService(){
        if (billService == null){
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
            billService =  context.getBean("gestionBill", BillService.class);
        }
        return billService;
    }
}
