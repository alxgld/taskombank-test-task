package com.gladunalexander.adminservice.web;

import com.gladunalexander.adminservice.domain.Client;
import com.gladunalexander.adminservice.domain.ClientFilter;
import com.gladunalexander.adminservice.service.ClientService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.List;

/**
 * @author Alexander Gladun
 */

@RestController
@RequestMapping("/api/admin")
@Api(value="Admin", description = "Admin API for clients management", tags=("admin"))
public class AdminController {

    private final ClientService clientService;

    public AdminController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    @ApiOperation(value="Get All Clients", notes="Gets all clients in the system")
    public List<Client> getClients() {
        return clientService.getClients();
    }

    @GetMapping("/filter")
    @ApiOperation(value="Get Clients By Filter", notes="Gets all clients for a specific filter")
    public List<Client> getClientsByFilter(@ModelAttribute ClientFilter clientFilter) {
        return clientService.findByFilter(clientFilter);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value="Create New Client", notes="Adds new client to the system")
    public Client createClient(@RequestBody Client client) {
        return clientService.addClient(client);
    }

    @PutMapping("/{id}")
    @ApiOperation(value="Update Client", notes="Updates existing client")
    public Client updateClient(@PathVariable int id,
                               @RequestBody Client client) {
        return clientService.updateClient(id, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value="Remove Client", notes="Removes client from the system")
    public void removeClient(@PathVariable int id) {
        clientService.removeClient(id);
    }


    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<String> handleRestTemplateException(HttpStatusCodeException e) {
        return new ResponseEntity<>(e.getResponseBodyAsString(), e.getStatusCode());
    }
}
