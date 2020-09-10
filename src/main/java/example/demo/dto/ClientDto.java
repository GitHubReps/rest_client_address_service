package example.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientDto extends AbstractDto {

    private String name;
    private List<AddressDto> addresses;
}
