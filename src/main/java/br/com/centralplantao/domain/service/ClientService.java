package br.com.centralplantao.domain.service;

import br.com.centralplantao.domain.exception.ResourceNotFoundException;
import br.com.centralplantao.domain.model.Client;
import br.com.centralplantao.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client saveClient(Client client) {
        log.info("[CLIENT-SERVICE] - Creating new client: {}", client.getTradeName());
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        log.info("[CLIENT-SERVICE] - Fetching all clients");
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        log.info("[CLIENT-SERVICE] - Finding client by ID: {}", id);
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("[CLIENT-SERVICE] - Client not found with ID: {}", id);
                    return new ResourceNotFoundException("Client not found with ID: " + id);
                });
    }
}
