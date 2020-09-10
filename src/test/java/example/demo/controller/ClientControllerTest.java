package example.demo.controller;


import example.demo.dto.AddressForm;
import example.demo.dto.ClientDto;
import example.demo.dto.PageResult;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClientControllerTest extends TestBase {

    @Before
    public void setUp() throws Exception {

        initClientRegistration();
    }

    @After
    public void tearDown() {

        clientJpaRepository.deleteAll();
    }

    @Test
    public void create() {

        assertThat(newClientDto).isNotNull();
        assertThat(newClientDto.getName()).isEqualTo(CLIENT_NAME);
        assertThat(newClientDto.getAddresses()).isNotNull();
    }

    @Test
    public void get() throws Exception {

        mockMvc
                .perform(get(CLIENT_URL + "/" + newClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc
                .perform(get(CLIENT_URL + "/" + randomClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete() throws Exception {

        mockMvc
                .perform(delete(CLIENT_URL + "/" + newClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc
                .perform(delete(CLIENT_URL + "/" + randomClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void getAddress() throws Exception {

        mockMvc
                .perform(get(CLIENT_URL + "/" + newClientId + "/addresses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc
                .perform(get(CLIENT_URL + "/" + randomClientId + "/addresses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void addAddress() throws Exception {

        AddressForm addressForm = new AddressForm();
        addressForm.setCity("Paris");
        addressForm.setStreet("The Avenue des Champs-Elysees");
        addressForm.setBuilding("17");
        String response = mockMvc
                .perform(post(CLIENT_URL + "/" + newClientId + "/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addressForm)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        ClientDto updatedClientDto = objectMapper.readValue(response, ClientDto.class);
        assertThat(updatedClientDto.getAddresses().stream().anyMatch(c -> c.getCity().equals(addressForm.getCity()))).isTrue();
        assertThat(updatedClientDto.getAddresses().stream().anyMatch(c -> c.getStreet().equals(addressForm.getStreet()))).isTrue();
        assertThat(updatedClientDto.getAddresses().stream().anyMatch(c -> c.getBuilding().equals(addressForm.getBuilding()))).isTrue();
    }

    @Test
    public void getAll() throws Exception {

       ClientDto clientDto = createClientDto("Name", "City", "Street", "Building");
        String response = mockMvc
                .perform(get(CLIENT_URL + "?name=" + clientDto.getName() + "&exact=false" + "&sorted=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        PageResult userPageable = objectMapper.readValue(response, PageResult.class);
        assertThat(userPageable.getSize()).isEqualTo(20);
        assertThat(userPageable.getItems()).size().isGreaterThan(0);
        response = mockMvc
                .perform(get(CLIENT_URL + "?login=" + "test" + "&exact=false" + "&sorted=false")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        userPageable = objectMapper.readValue(response, PageResult.class);
        assertThat(userPageable.getItems().size()).isGreaterThan(1);
        response = mockMvc
                .perform(get(CLIENT_URL + "?login=" + clientDto.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        userPageable = objectMapper.readValue(response, PageResult.class);
        assertThat(userPageable.getItems().size()).isEqualTo(2);
    }

    @Test
    public void deleteAddressById() throws Exception {

        mockMvc
                .perform(delete(CLIENT_URL + "/" + newClientId + "/addresses" + "/" + newClientDto.getAddresses().stream().findFirst().get().getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        mockMvc
                .perform(delete(CLIENT_URL + "/" + randomClientId + "/addresses" + "/" + randomClientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}