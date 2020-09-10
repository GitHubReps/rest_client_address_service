package example.demo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ClientDtoWithoutAddress extends AbstractDto {

    private String name;
}
