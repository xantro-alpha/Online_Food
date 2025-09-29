package Nova.model; 

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import Nova.dto.RestaurantDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import Nova.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.ast.Or;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
@Id

@GeneratedValue(strategy=GenerationType.AUTO)
private Long id;
private String fullname;
private String email;

@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
private String password;

private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

// user can have multiple orders and user have onetomany relationship with orders/entity
@JsonIgnore
@OneToMany(cascade = CascadeType.ALL,mappedBy="customer") //we are telling our spring boot don't create any separate table for this mapping

private List<Order> orders = new ArrayList<>();

@ElementCollection
private List<RestaurantDto>favorites=new ArrayList<>();

@OneToMany(cascade= CascadeType.ALL, orphanRemoval= true) //whenever we delete this user it will delete all the addresses saved of this user (cascade) 
//one user can have multiple addresses that's why 
// we use one to many relationship

private List<Address> addresses=new ArrayList<>(); //we will store the user addresses here


}
