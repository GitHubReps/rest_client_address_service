package example.demo.service;

import example.demo.model.Address;
import example.demo.model.Client;
import example.demo.repository.AddressJpaRepository;
import example.demo.repository.ClientJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;

import static example.demo.service.ClientSpecification.filterByName;
import static example.demo.service.ClientSpecification.emptySpecification;

@Service
public class ClientDataService {

    @Autowired
    private ClientJpaRepository clientJpaRepository;

    @Autowired
    private AddressJpaRepository addressJpaRepository;

    @Transactional
    public Client save(Client client) {
        return clientJpaRepository.saveAndFlush(client);
    }

    public Optional<Client> findById(Long id) {
        return clientJpaRepository.findById(id);
    }

    public void delete(Client client) {
        clientJpaRepository.delete(client);
    }

    public Optional<Client> findByName(String name) {
        return clientJpaRepository.findByName(name);
    }

    public Page<Client> findAll(Integer pageNumber, Integer size, boolean exact, String name, boolean sorted) {

        Specification<Client> specification;
        if (name != null && !name.isEmpty()) {
            specification = filterByName(name, exact);
        } else {
            specification = emptySpecification();
        }
        Pageable pageable;
        if (sorted) {
            pageable = PageRequest.of(pageNumber, size, Sort.Direction.ASC, "name");
        } else {
            pageable = PageRequest.of(pageNumber, size);
        }
        return clientJpaRepository.findAll(specification, pageable);
    }

    public void deleteAddress(Address address) {
        addressJpaRepository.delete(address);
    }

    public Optional<Address> findAddressById(Long addressid) {
        return addressJpaRepository.findById(addressid);
    }
}
