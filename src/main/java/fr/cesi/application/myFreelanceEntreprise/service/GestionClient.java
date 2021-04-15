package fr.cesi.application.myFreelanceEntreprise.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GestionClient {
    private static ClientService clientService;

    public static ClientService getClientService(){
        if (clientService == null) {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
            clientService = context.getBean("gestionClient", ClientService.class);
        }
        return clientService;
    }
}
