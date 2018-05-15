package com.gladunalexander.clientservice.integration;

import com.gladunalexander.clientservice.domain.Client;
import com.gladunalexander.clientservice.repository.ClientRepository;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;
import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexander Gladun
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ClientServiceIntegrationTest {

    private static final String BASE_API_URL = "/api/clients/";
    private static final String API_FILTER_URL = BASE_API_URL + "/filter";
    private static final ParameterizedTypeReference<List<Client>> PARAMETERIZED_TYPE = new ParameterizedTypeReference<List<Client>>() {};

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ClientRepository clientRepository;

    @Test
    public void test01_getClients() {
        ResponseEntity<List<Client>> entity = testRestTemplate.exchange(BASE_API_URL, HttpMethod.GET, null, PARAMETERIZED_TYPE);
        List<Client> clients = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clients).hasSize(3);
        assertThat(clients.get(0).getEmail()).isEqualTo("vladislav@gmail.com");
    }

    @Test
    public void test02_getClientsByFilterWithoutFilter() {
        ResponseEntity<List<Client>> entity = testRestTemplate.exchange(API_FILTER_URL, HttpMethod.GET, null, PARAMETERIZED_TYPE);
        List<Client> clients = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clients).hasSize(3);

    }

    @Test
    public void test03_getClientsByFilterUsingFirstName() {
        URI uri = UriComponentsBuilder.fromPath(API_FILTER_URL)
                .queryParam("firstName", "Alexander")
                .build()
                .toUri();

        ResponseEntity<List<Client>> entity = testRestTemplate.exchange(uri, HttpMethod.GET, null, PARAMETERIZED_TYPE);
        List<Client> clients = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clients).hasSize(2);
        assertThat(clients.get(0).getFirstName()).isEqualTo("Alexander");
        assertThat(clients.get(1).getFirstName()).isEqualTo("Alexander");
    }

    @Test
    public void test04_getClientsByFilterUsingFullFilter() {
        URI uri = UriComponentsBuilder.fromPath(API_FILTER_URL)
                .queryParam("firstName", "Alexander")
                .queryParam("lastName",  "Boyko")
                .queryParam("middleName", "Anatolievich")
                .queryParam("email", "vladislav@gmail.com")
                .queryParam("phoneNumber", "0931829974")
                .build()
                .toUri();

        ResponseEntity<List<Client>> entity = testRestTemplate.exchange(uri, HttpMethod.GET, null, PARAMETERIZED_TYPE);
        List<Client> clients = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(clients).hasSize(1);
        assertThat(clients.get(0).getFirstName()).isEqualTo("Alexander");
        assertThat(clients.get(0).getPhoneNumber()).isEqualTo("0931829974");
    }

    @Test
    public void test05_createClient() {
        Client client = clientToAdd();
        ResponseEntity<Client> entity = testRestTemplate.exchange(BASE_API_URL, HttpMethod.POST, new HttpEntity<>(client), Client.class);
        Client created = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(clientRepository.findAll()).hasSize(4);
        assertThat(created.getId()).isEqualTo(4);
        assertThat(created.getFirstName()).isEqualTo(client.getFirstName());
        assertThat(created.getLastName()).isEqualTo(client.getLastName());
        assertThat(created.getMiddleName()).isEqualTo(client.getMiddleName());
        assertThat(created.getEmail()).isEqualTo(client.getEmail());
        assertThat(created.getPhoneNumber()).isEqualTo(client.getPhoneNumber());
    }

    @Test
    public void test06_updateClient() {
        Client client = clientToUpdate();
        ResponseEntity<Client> entity = testRestTemplate.exchange(BASE_API_URL + "{id}", HttpMethod.PUT, new HttpEntity<>(client), Client.class, 1);
        Client updated = entity.getBody();

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getFirstName()).isEqualTo(client.getFirstName());
        assertThat(updated.getLastName()).isEqualTo(client.getLastName());
        assertThat(updated.getMiddleName()).isEqualTo(client.getMiddleName());
        assertThat(updated.getEmail()).isEqualTo(client.getEmail());
        assertThat(updated.getPhoneNumber()).isEqualTo(client.getPhoneNumber());


    }

    @Test
    public void test07_removeClient() {
        testRestTemplate.delete(BASE_API_URL + "{id}", 1);
        List<Client> clients = clientRepository.findAll();

        assertThat(clients).hasSize(3);
        assertThat(clients.stream().noneMatch(client -> client.getId() == 1)).isTrue();
    }

    private Client clientToAdd() {
        Client client = new Client();
        client.setFirstName("Oleg");
        client.setLastName("Badun");
        client.setMiddleName("Viktorovich");
        client.setPhoneNumber("0938172781");
        client.setEmail("oleg@ukr.net");
        return client;
    }

    private Client clientToUpdate() {
        Client client = new Client();
        client.setFirstName("Inna");
        client.setLastName("Potapovich");
        client.setMiddleName("Potapovna");
        client.setPhoneNumber("0938171381");
        client.setEmail("inna@ukr.net");
        return client;
    }
}
