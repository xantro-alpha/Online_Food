package Nova.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor 

public class Orderitem {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne// many order items can belong to one order
    private Food food; // food entity 

    private int quantity;

    private Long totalprice;


    private List<String> ingredients;



}
