package example.demo.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class NewClientForm {

    @NotEmpty
    private String name;
    private String city;
    private String street;
    private String building;
}
