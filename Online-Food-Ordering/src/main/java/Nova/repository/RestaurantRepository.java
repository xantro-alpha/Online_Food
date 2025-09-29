package Nova.repository;

import Nova.model.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    @Query("from")
    List<Restaurant> findBySearchQuery(String query);


    Restaurant findByOwnerId(Long userId);
}
