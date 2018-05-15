package com.gladunalexander.clientservice.controller;

import com.gladunalexander.clientservice.domain.Client;
import com.gladunalexander.clientservice.domain.ClientFilter;
import com.gladunalexander.clientservice.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * @author Alexander Gladun
 */

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/filter")
    public List<Client> getClientsByFilter(@ModelAttribute ClientFilter clientFilter) {
        return clientService.findByFilter(clientFilter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client createClient(@Valid @RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PutMapping("/{id}")
    public Client updateClient(@PathVariable int id,
                               @Valid @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeClient(@PathVariable int id) {
        clientService.removeClient(id);
    }
}
