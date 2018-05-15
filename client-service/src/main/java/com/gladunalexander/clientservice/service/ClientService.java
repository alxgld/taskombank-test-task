package com.gladunalexander.clientservice.service;

import com.gladunalexander.clientservice.domain.Client;
import com.gladunalexander.clientservice.domain.ClientFilter;
import java.util.List;

/**
 * @author Alexander Gladun
 */
public interface ClientService {

    List<Client> getClients();

    List<Client> findByFilter(ClientFilter clientFilter);

    Client addClient(Client client);

    Client updateClient(int id, Client client);

    void removeClient(int id);
}
