package com.newrelic.ec.controller;

import com.esotericsoftware.minlog.Log;
import com.newrelic.ec.dto.ClientDto;
import com.newrelic.ec.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Client Controller", description = "APIs related to Client operations")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/client")
    @Operation(summary = "Save a new client", responses = {
            @ApiResponse(responseCode = "200", description = "Successful save of the client")
    })
    public ResponseEntity<Long> saveClient(@RequestBody ClientDto clientDto) {
        Log.info("Saving client: " + clientDto.toString());
        return ResponseEntity.ok(clientService.saveClient(clientDto));
    }

    @GetMapping("/client/list")
    @Operation(summary = "Get a list of all clients", responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of client list")
    })
    public ResponseEntity<List<ClientDto>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/client/{id}")
    @Operation(summary = "Get a client by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of the client"),
            @ApiResponse(responseCode = "404", description = "Client with given ID not found")
    })
    public ResponseEntity<ClientDto> getClient(
            @Parameter(description = "ID of the client to be retrieved") @PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClient(id));
    }

    @PutMapping("/client/{id}")
    @Operation(summary = "Update a client by its ID", responses = {
            @ApiResponse(responseCode = "200", description = "Successful update of the client"),
            @ApiResponse(responseCode = "404", description = "Client with given ID not found")
    })
    public ResponseEntity<ClientDto> updateClient(
            @Parameter(description = "ID of the client to be updated") @PathVariable Long id,
            @RequestBody ClientDto clientDto) {
        clientDto.setId(id);
        return ResponseEntity.ok(clientService.updateClient(clientDto));
    }

    @GetMapping("/hello")
    @Operation(summary = "Simple Hello World endpoint", responses = {
            @ApiResponse(responseCode = "200", description = "Successful greeting")
    })
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello World");
    }
}
