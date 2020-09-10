package example.demo.service;

import example.demo.model.Client;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {

    public static Specification<Client> filterByName(String name, boolean exact) {
        return ((root, query, criteriaBuilder) ->
                exact ? criteriaBuilder.equal(root.get("name"), name)
                : criteriaBuilder.like(root.get("name"), "%" + name + "%"));
    }

    public static Specification<Client> emptySpecification() {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.and());
    }
}
