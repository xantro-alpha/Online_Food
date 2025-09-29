package Nova.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Nova.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
