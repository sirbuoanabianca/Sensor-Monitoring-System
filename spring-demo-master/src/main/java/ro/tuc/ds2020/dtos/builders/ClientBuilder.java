package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.ClientDTO;
import ro.tuc.ds2020.dtos.ClientDetailsDTO;
import ro.tuc.ds2020.entities.Client;

public class ClientBuilder {

    private ClientBuilder() {
    }

    public static ClientDTO toClientDTO(Client client) {
        return new ClientDTO(client.getId(), client.getUsername(), client.getUserRole());
    }

    public static ClientDetailsDTO toClientDetailsDTO(Client client) {
        return new ClientDetailsDTO(client.getId(), client.getUsername(), client.getPassword(), client.getUserRole());
    }

    public static Client toEntity(ClientDetailsDTO clientDetailsDTO) {
        return new Client(clientDetailsDTO.getId(),clientDetailsDTO.getUsername(),clientDetailsDTO.getPassword(),clientDetailsDTO.getRole());
    }
}
