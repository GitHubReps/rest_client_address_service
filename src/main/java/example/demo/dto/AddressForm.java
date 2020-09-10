package example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AddressForm {

    private String city;
    private String street;
    private String building;
}
