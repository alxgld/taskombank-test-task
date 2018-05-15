package com.gladunalexander.clientservice.service.impl;

import com.gladunalexander.clientservice.domain.Client;
import com.gladunalexander.clientservice.domain.ClientFilter;
import com.gladunalexander.clientservice.repository.ClientRepository;
import com.gladunalexander.clientservice.service.ClientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * @author Alexander Gladun
 */

@Service
@Transactional(readOnly = true)
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;

    public ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Client> getClients() {
        return repository.findAll();
    }

    @Override
    public List<Client> findByFilter(ClientFilter clientFilter) {
        checkFilterForEmptyStrings(clientFilter);
        return repository.findClientsByFilter(clientFilter.getFirstName(), clientFilter.getLastName(),
                                              clientFilter.getMiddleName(), clientFilter.getEmail(),
                                              clientFilter.getPhoneNumber());
    }

    @Override
    @Transactional
    public Client addClient(Client client) {
        validateClient(client);
        return repository.save(client);
    }

    @Override
    @Transactional
    public Client updateClient(int id, Client client) {
        validateClient(client);
        Client existing = getClient(id);
        existing.setFirstName(client.getFirstName());
        existing.setLastName(client.getLastName());
        existing.setMiddleName(client.getMiddleName());
        existing.setEmail(client.getEmail());
        existing.setPhoneNumber(client.getPhoneNumber());
        return repository.save(existing);
    }

    @Override
    @Transactional
    public void removeClient(int id) {
        Client client = getClient(id);
        repository.delete(client);
    }

    private Client getClient(int id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Client with id " + id + " not found."));
    }

    private void validateClient(Client client) {
        List<Client> clients = repository.findByEmailOrPhoneNumber(client.getEmail(), client.getPhoneNumber());
        if (!CollectionUtils.isEmpty(clients)) {
            throw new IllegalArgumentException("Client with provided email or phone number already exists.");
        }
    }

    private void checkFilterForEmptyStrings(ClientFilter clientFilter) {
        String emptyString = "";
        if (emptyString.equals(clientFilter.getFirstName())) {
            clientFilter.setFirstName(null);
        }
        if (emptyString.equals(clientFilter.getLastName())) {
            clientFilter.setLastName(null);
        }
        if (emptyString.equals(clientFilter.getMiddleName())) {
            clientFilter.setMiddleName(null);
        }
        if (emptyString.equals(clientFilter.getEmail())) {
            clientFilter.setEmail(null);
        }
        if (emptyString.equals(clientFilter.getPhoneNumber())) {
            clientFilter.setPhoneNumber(null);
        }
    }
}
