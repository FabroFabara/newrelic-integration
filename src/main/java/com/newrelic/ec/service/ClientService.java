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

    public Long saveClient(ClientDto clientDto) {
        Log.info("Saving client: " + clientDto.toString());
        Client client = new Client();
        BeanUtils.copyProperties(clientDto, client);
        return clientRepository.saveAndFlush(client).getId();
    }

    public ClientDto getClient(Long id) {
        Log.info("Getting client with ID: " + id);
        Client client = clientRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Client not found"));
        ClientDto clientDto = new ClientDto();
        BeanUtils.copyProperties(client, clientDto);
        return clientDto;
    }

    public ClientDto updateClient(ClientDto clientDto) {
        Client client = clientRepository.findById(clientDto.getId()).orElseThrow(() -> new RuntimeException("Client not found"));
        BeanUtils.copyProperties(clientDto, client);
        Client response = clientRepository.saveAndFlush(client);
        ClientDto responseDto = new ClientDto();
        BeanUtils.copyProperties(response, responseDto);
        return responseDto;
    }

    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> {
                    ClientDto clientDto = new ClientDto();
                    BeanUtils.copyProperties(client, clientDto);
                    return clientDto;
                })
                .collect(Collectors.toList());
    }

}
