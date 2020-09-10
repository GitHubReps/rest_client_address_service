package example.demo.controller;

import com.sun.istack.NotNull;
import example.demo.dto.*;
import example.demo.exceptions.ClientExistsException;
import example.demo.model.Address;
import example.demo.model.Client;
import example.demo.service.ClientDataService;
import example.demo.exceptions.EntityNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.apache.commons.collections.CollectionUtils;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(tags = "Client Controller")
@RequestMapping(path = "/api/client", produces = {MediaType.APPLICATION_JSON_VALUE})
public class ClientController {

    @Autowired
    private ClientDataService clientDataService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiOperation(value = "Create a client (case sensitive)", notes = "Returns created client", response = ClientDto.class)
    @PostMapping
    public ResponseEntity<MappingJacksonValue> create(@Valid @RequestBody NewClientForm newClientForm) throws ClientExistsException {

        Client client = new Client();
        return clientSetup(newClientForm, client);
    }

    @NotNull
    private ResponseEntity<MappingJacksonValue> clientSetup(@Valid @RequestBody NewClientForm newClientForm, Client client) throws ClientExistsException {

        if (clientDataService.findByName(newClientForm.getName()).isEmpty()) {
            List<Address> clientAddress = new ArrayList<>();
            Address address = new Address();
            clientAddress.add(address);
            address.setCity(newClientForm.getCity());
            address.setStreet(newClientForm.getStreet());
            address.setBuilding(newClientForm.getBuilding());
            address.setClient(client);
            client.setAddresses(clientAddress);
            client.setName(newClientForm.getName());
            Client createdClient = clientDataService.save(client);
            ClientDto clientDto = modelMapper.map(createdClient, ClientDto.class);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(clientDto);
            return ResponseEntity.ok(mappingJacksonValue);
        } else {
            throw new ClientExistsException("Client with name " + newClientForm.getName() + " already exists");
        }
    }


    @ApiOperation(value = "Get client by ID (numeric)", notes = "Returns client if exists", response = ClientDto.class)
    @GetMapping(path = "/{id}")
    public ResponseEntity<MappingJacksonValue> get(@Valid @PathVariable("id") Long id) throws EntityNotFoundException {

        Optional<Client> clientOptional = clientDataService.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            ClientDto clientDto = modelMapper.map(client, ClientDto.class);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(clientDto);
            return ResponseEntity.ok(mappingJacksonValue);
        } else {
            throw new EntityNotFoundException("Client with id " + id + " not found");
        }
    }

    @ApiOperation(value = "Delete a client", notes = "Returns deleted client", response = ClientDto.class)
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) throws EntityNotFoundException {

        Optional<Client> clientOptional = clientDataService.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            clientDataService.delete(client);
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Client with id " + id + " not found");
        }
    }

    @ApiOperation(value = "Get client's addresses by ID (numeric)", notes = "Returns client's addresses", response = AddressDto.class)
    @GetMapping(path = "/{id}/addresses")
    public ResponseEntity<MappingJacksonValue> getAddress(@Valid @PathVariable("id") Long id) throws EntityNotFoundException {

        Optional<Client> clientOptional = clientDataService.findById(id);
        if (clientOptional.isPresent()) {
            List<AddressDto> addressList = clientOptional.get().getAddresses().stream().map(
                    a -> modelMapper.map(a, AddressDto.class)).collect(Collectors.toList());
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(addressList);
            return ResponseEntity.ok(mappingJacksonValue);
        } else {
            throw new EntityNotFoundException("Client with id " + id + " not found");
        }
    }

    @ApiOperation(value = "Add address to a client (case sensitive)", notes = "Returns updated client", response = ClientDto.class)
    @PostMapping(path = "/{id}/addresses")
    public ResponseEntity<MappingJacksonValue> addAddress(@Valid @RequestBody AddressForm addressForm, @PathVariable("id") Long id) throws EntityNotFoundException {

        Optional<Client> clientOptional = clientDataService.findById(id);
        if (clientOptional.isPresent()) {
            Client client = clientOptional.get();
            List<Address> clientAddresses = client.getAddresses();
            Address address = new Address();
            address.setCity(addressForm.getCity());
            address.setStreet(addressForm.getStreet());
            address.setBuilding(addressForm.getBuilding());
            address.setClient(client);
            clientAddresses.add(address);
            client.setAddresses(clientAddresses);
            Client updatedClient = clientDataService.save(client);
            ClientDto clientDto = modelMapper.map(updatedClient, ClientDto.class);
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(clientDto);
            return ResponseEntity.ok(mappingJacksonValue);
        } else {
            throw new EntityNotFoundException("Client with id " + id + " not found");
        }
    }

    @ApiOperation(value = "Get all clients by name unordered (case sensitive)", notes = "Returns all matching clients", response = PageResult.class)
    @GetMapping
    public ResponseEntity<MappingJacksonValue> getAll(@ApiParam("name") @RequestParam(required = false) final String name,
                                                      @ApiParam("exactName") @RequestParam(required = false, defaultValue = "true") boolean exact,
                                                      @ApiParam("sorted") @RequestParam(required = false, defaultValue = "false") boolean sorted,
                                                      @ApiParam("pageNumber") @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                                      @ApiParam("size") @RequestParam(required = false, defaultValue = "20") Integer size) {

        Page<Client> userPage = clientDataService.findAll(pageNumber, size, exact, name, sorted);
        Collection<ClientDtoWithoutAddress> clientDtoCollection;
        if (CollectionUtils.isNotEmpty(userPage.getContent())) {
            clientDtoCollection = userPage.getContent().stream()
                    .map(u -> modelMapper
                            .map(u, ClientDtoWithoutAddress.class))
                    .collect(Collectors.toList());
        } else {
            clientDtoCollection = Collections.EMPTY_LIST;
        }
        PageResult pageResult = new PageResult();
        pageResult.setItems(clientDtoCollection);
        pageResult.setPageNumber(pageNumber);
        pageResult.setSize(size);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(pageResult);
        return ResponseEntity.ok(mappingJacksonValue);
    }

    @ApiOperation(value = "Delete address by ID", notes = "Returns updated client")
    @DeleteMapping(path = "/{id}/addresses/{addressid}")
    public ResponseEntity<?> deleteAddress(@PathVariable("id") Long id, @PathVariable("addressid") Long addressid) throws EntityNotFoundException {

        Optional<Client> clientOptional = clientDataService.findById(id);
        Optional<Address> addressOptional = clientDataService.findAddressById(addressid);
        if (clientOptional.isPresent() && addressOptional.isPresent()) {
            Client client = clientOptional.get();
            Address address = addressOptional.get();
            if (!address.getClient().equals(client)) {
                throw new EntityNotFoundException("No connection between an address and a client");
            } else {
                clientDataService.deleteAddress(address);
            }
            return ResponseEntity.noContent().build();
        } else {
            throw new EntityNotFoundException("Entity with id " + id + " not found");
        }
    }
}
