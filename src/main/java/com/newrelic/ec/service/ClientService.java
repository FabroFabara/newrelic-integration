package com.newrelic.ec.service;

import com.esotericsoftware.minlog.Log;
import com.newrelic.ec.dto.ClientDto;
import com.newrelic.ec.model.Client;
import com.newrelic.ec.repository.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private NewRelicEventService newRelicEventService;

    public Long saveClient(ClientDto clientDto) {
        Log.info("Saving client: " + clientDto.toString());
        long startTime = System.currentTimeMillis();

        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        Long clientId = clientRepository.saveAndFlush(client).getId();

        newRelicEventService.createCriticalAlarm("saveClient", "Client with ID " + clientId + " saved successfully.");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        newRelicEventService.createCriticalAlarm("saveClient", "Client with ID " + clientId + " retrieved successfully. Execution Time (ms): " + executionTime);


        return clientId;
    }

    public ClientDto getClient(Long id) {
        Log.info("Getting client with ID: " + id);
        long startTime = System.currentTimeMillis();

        newRelicEventService.createCriticalAlarm("getClient", "Client with ID " + id + " retrieved successfully.");

        Client client = clientRepository.findById(id).orElseThrow(() -> new RuntimeException("Client not found"));
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client, clientDto);
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        newRelicEventService.createCriticalAlarm("getClient", "Client with ID " + id + " retrieved successfully. Execution Time (ms): " + executionTime);

        return clientDto;
    }

    public ClientDto updateClient(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId()).orElseThrow(() -> new RuntimeException("Client not found"));
        long startTime = System.currentTimeMillis();

        BeanUtils.copyProperties(clientDto, client);
        Client response = clientRepository.saveAndFlush(client);
        ClientDto responseDto = new ClientDto();
        BeanUtils.copyProperties(response, responseDto);

        newRelicEventService.createCriticalAlarm("updateClient", "Client with ID " + clientDto.getId() + " updated successfully.");
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        newRelicEventService.createCriticalAlarm("updateClient", "Client with ID " +  clientDto.getId()  + " retrieved successfully. Execution Time (ms): " + executionTime);

        return responseDto;
    }

    public List<ClientDto> getAllClients() {
        newRelicEventService.createCriticalAlarm("getAllClients", "All clients retrieved successfully.");

        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(client -> {
            ClientDto clientDto = new ClientDto();
            BeanUtils.copyProperties(client, clientDto);
            return clientDto;
        }).collect(Collectors.toList());
    }
}
