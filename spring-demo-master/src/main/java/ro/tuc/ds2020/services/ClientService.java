package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.handlers.exceptions.model.ResourceNotFoundException;
import ro.tuc.ds2020.dtos.ClientDTO;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.dtos.builders.ClientBuilder;
import ro.tuc.ds2020.entities.Client;
import ro.tuc.ds2020.repositories.ClientRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService implements UserDetailsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClientService.class);
    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientDTO> findClients() {
        List<Client> clientList = clientRepository.findAll();
        return clientList.stream()
                .map(ClientBuilder::toClientDTO)
                .collect(Collectors.toList());
    }

    public ClientDetailsDTO findClientById(UUID id) {
        Optional<Client> clientOptional = clientRepository.findById(id);
        if (!clientOptional.isPresent()) {
            LOGGER.error("Client with id {} was not found in db", id);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }
        return ClientBuilder.toClientDetailsDTO(clientOptional.get());
    }

    public UUID insert(ClientDetailsDTO clientDetailsDTO) {
        Client client = ClientBuilder.toEntity(clientDetailsDTO);
        client = clientRepository.save(client);
        LOGGER.debug("Client with id {} was inserted in db", client.getId());
        return client.getId();
    }

    public UUID update(ClientDetailsDTO clientDetailsDTO) {
        Optional<Client> oldClient = clientRepository.findById(clientDetailsDTO.getId());
        if (!oldClient.isPresent()) {
            LOGGER.error("Client with id {} was not found in db", clientDetailsDTO.getId());
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + clientDetailsDTO.getId());
        }

        Client client = oldClient.get();

        client.setUsername(clientDetailsDTO.getUsername());
        client.setUserRole(clientDetailsDTO.getRole());


            if (!Objects.equals(clientDetailsDTO.getPassword(), ""))
                client.setPassword(clientDetailsDTO.getPassword());



           clientRepository.save(client);
            LOGGER.debug("Client with id {} was inserted in db", client.getId());
            return client.getId();

    }

    public UUID delete(UUID id) {
        Optional<Client> searchedClient = clientRepository.findById(id);
        if (!searchedClient.isPresent()) {
            LOGGER.error("Client with id {} was not found in db", id);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with id: " + id);
        }

            clientRepository.delete(searchedClient.get());

        return id;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Client> searchedClient = Optional.ofNullable(clientRepository.findByUsername(s));
        if (!searchedClient.isPresent()) {
            LOGGER.error("Client with username {} was not found in db", s);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with username: " + s);
        }
        Client client = searchedClient.get();
        ArrayList role = new ArrayList();
        role.add(new SimpleGrantedAuthority(client.getUserRole().toString()));
        return new org.springframework.security.core.userdetails.User(client.getUsername(), client.getPassword(),
                role);
    }

    public Client findClientByUsername(String username){
        Optional<Client> searchedClient = Optional.ofNullable(clientRepository.findByUsername(username));
        if (!searchedClient.isPresent()) {
            LOGGER.error("Client with username {} was not found in db", username);
            throw new ResourceNotFoundException(Client.class.getSimpleName() + " with username: " + username);
        }

        return searchedClient.get();
    }
}
