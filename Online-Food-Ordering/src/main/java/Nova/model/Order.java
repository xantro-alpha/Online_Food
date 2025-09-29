package Nova.model;

import java.util.Date;
import java.util.List;



import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO)
    private Long id;
    
    //now we are going to create our order entity

    @ManyToOne
    private User customer;


    

    @JsonIgnore 
    @ManyToOne //many orders can be placed to one restaurant
    private Restaurant restaurant;
    

    private  Long totalAmount;


    private String orderStatus; //pending, completed, cancelled


    private Date createdAt;


    @ManyToOne //many orders can be delivered to one address
    private Address deliveryAddress;

    @OneToMany //one order can have multiple items like he can order pizza, burger, coke in one order aisa sab
    private List<Orderitem> items;

    // private Payment payment; // cod, online

    private int totalitem;

    private int totalprice;





        

}
