package com.gladunalexander.adminservice.service;

import com.gladunalexander.adminservice.domain.Client;
import com.gladunalexander.adminservice.domain.ClientFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;
import java.util.List;

/**
 * @author Alexander Gladun
 */

@Service
@Slf4j
public class ClientService {

    private static final ParameterizedTypeReference<List<Client>> PARAMETERIZED_TYPE = new ParameterizedTypeReference<List<Client>>() {};

    private static final String BASE_API_URL = "http://client-service:8081/api/clients/";

    private static final String SEARCH_BY_FILTER_URL =
            "http://client-service:8081/api/clients/filter?firstName={firstName}" +
                    "&lastName={lastName}&middleName={middleName}&email={email}&phoneNumber={phoneNumber}";

    private final RestTemplate restTemplate;

    public ClientService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Client> getClients() {
        log.info("Retrieving all clients...");
        return restTemplate
                .exchange(BASE_API_URL, HttpMethod.GET, null, PARAMETERIZED_TYPE)
                .getBody();
    }

    public List<Client> findByFilter(ClientFilter clientFilter) {
        URI uri = new UriTemplate(SEARCH_BY_FILTER_URL).expand(clientFilter.getFirstName(),
                clientFilter.getLastName(), clientFilter.getMiddleName(), clientFilter.getEmail(), clientFilter.getPhoneNumber());

        log.info("Retrieving clients by filter: " + clientFilter);
        return restTemplate
                .exchange(uri, HttpMethod.GET, null, PARAMETERIZED_TYPE)
                .getBody();
    }

    public Client addClient(Client client) {
        Client created = restTemplate
                .exchange(BASE_API_URL, HttpMethod.POST, new HttpEntity<>(client), Client.class)
                .getBody();
        log.info("Client {} has been created", created);
        return created;

    }

    public Client updateClient(int id, Client client) {
        Client updated = restTemplate
                .exchange(BASE_API_URL + "{id}", HttpMethod.PUT, new HttpEntity<>(client), Client.class, id)
                .getBody();
        log.info("Client {} has been updated", updated);
        return updated;
    }

    public void removeClient(int id) {
        restTemplate.delete(BASE_API_URL + "{id}", id);
        log.info("Client with id {} has been removed", id);
    }
}
