package example.demo.dto;

import lombok.Data;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class PageResult {

    private Collection<ClientDtoWithoutAddress> items = new ArrayList<>();
    private Integer pageNumber;
    private Integer size;
}
