package Nova.service;

import Nova.model.Restaurant;
import Nova.model.User;
import Nova.request.CreateRestaurantRequest;

import java.util.List;

public interface RestaurantService {

    public Restaurant createRestaurant(CreateRestaurantRequest req, User user);


    public Restaurant updateRestaurant(Long restaurantId,CreateRestaurantRequest updateRestaurant) throws Exception;

    public void deleteRestaurant(Long restaurantId) throws Exception;

    public List<Restaurant> getAllRestaurant();

    public List<Restaurant> searchRestaurant();

    public  Restaurant findRestaurantById(Long id) throws Exception;

    public  Restaurant findRestaurantByuserId(Long userId) throws Exception;

    public Restaurant addToFavourites(Long restaurantId,User user) throws Exception;

    public Restaurant updateRestaurantStatus(Long id) throws Exception;


}
