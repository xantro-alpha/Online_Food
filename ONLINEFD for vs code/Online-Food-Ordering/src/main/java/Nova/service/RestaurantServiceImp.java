package Nova.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.CodecConfigurer.CustomCodecs;
import org.springframework.stereotype.Service;

import Nova.model.Address;
import Nova.model.Restaurant;
import Nova.model.User;
import Nova.repository.AddressRepository;
import Nova.repository.RestaurantRepository;
import Nova.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImp implements RestaurantService {

    
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;
    
    @Autowired
    private UserService userService;


    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address =addressRepository.save(req.getAddress()); ///address will be saved here

        Restaurant restaurant=new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);
         



        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updateRestaurant) throws Exception {

        Restaurant restaurant=findRestaurantById(restaurantId);
        if(restaurant.getCuisineType()!=null){
            restaurant.setCuisineType(updateRestaurant.getCuisineType());

        }
        if(restaurant.getDescription()!=null){
            restaurant.setDescription(updateRestaurant.getDescription());

        }
        if(restaurant.getName()!=null){
            restaurant.setName(updateRestaurant.getName());

        }


        
        return restaurantRepository.save(restaurant);
    }
    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {  
        Restaurant restaurant=findRestaurantById(restaurantId);

        restaurantRepository.delete(restaurant);


    }
    

    @Override
    public java.util.List<Restaurant> getAllRestaurant() {
        
        return restaurantRepository.findAll();
    }

    @Override
    public java.util.List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt= restaurantRepository.findById(id);
        if(opt.isEmpty()){
            throw new Exception("Restaurant not found with id "+id);
        }

        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant=restaurantRepository.findByOwnerId(userId);
        if(restaurant==null){
            throw new Exception("Restaurant not found for user id "+userId);
        }
        return restaurant;
    }

    @Override
    public Restaurant addToFavourites(Long restaurantId, User user) throws Exception {
        
        return null;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws Exception {
        return null;
    }


}


