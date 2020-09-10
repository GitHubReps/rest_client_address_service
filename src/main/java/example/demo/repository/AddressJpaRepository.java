package example.demo.repository;

import example.demo.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressJpaRepository extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {
}
