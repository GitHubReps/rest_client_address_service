package example.demo.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import example.demo.dto.ClientDto;
import example.demo.dto.NewClientForm;
import example.demo.repository.ClientJpaRepository;
import example.demo.service.ClientDataService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.concurrent.ThreadLocalRandom;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class TestBase {

    protected static final String CLIENT_URL = "/api/client";

    protected static final String CLIENT_NAME = "clientName";
    protected static final String CLIENT_CITY = "New York";
    protected static final String CLIENT_STREET = "Broadway";
    protected static final String CLIENT_BUILDING = "5A";

    @Autowired
    protected ClientJpaRepository clientJpaRepository;

    @Autowired
    protected ClientDataService clientDataService;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected ClientDto newClientDto;
    protected long newClientId;
    protected long randomClientId = ThreadLocalRandom.current().nextLong(10, 100000);

    protected MockHttpServletRequestBuilder get(String url) {
        return MockMvcRequestBuilders.get(url);
    }


    protected MockHttpServletRequestBuilder post(String url) {
        return MockMvcRequestBuilders.post(url);
    }


    protected MockHttpServletRequestBuilder put(String url) {
        return MockMvcRequestBuilders.put(url);
    }

    protected MockHttpServletRequestBuilder delete(String url) {
        return MockMvcRequestBuilders.delete(url);
    }

    public ClientDto createClientDto(String name, String city, String street, String building) throws Exception {
        NewClientForm newClientForm = new NewClientForm();
        newClientForm.setName(name);
        newClientForm.setCity(city);
        newClientForm.setStreet(street);
        newClientForm.setBuilding(building);
        return createClientDto(newClientForm);
    }

    public ClientDto createClientDto(NewClientForm newClientForm) throws Exception {
        String response = mockMvc
                .perform(post(CLIENT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newClientForm)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        return objectMapper.readValue(response, ClientDto.class);
    }

    protected void initClientRegistration() throws Exception {
        ClientDto newClientDto = createClientDto(CLIENT_NAME, CLIENT_CITY, CLIENT_STREET, CLIENT_BUILDING);
        this.newClientDto = newClientDto;
        this.newClientId = newClientDto.getId();
    }

    @Test
    @Ignore
    public void initialTest() {
        // Initialisation
    }

}
