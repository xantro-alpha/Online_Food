package Nova.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class IngredientsItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne //like mustard sauce , tomato sauce they have the same category of "sauce "
    private IngredientCategory category;

    @JsonIgnore
    @ManyToOne //one restaurant have multiple ingredient
    private Restaurant restaurant;

    private boolean inStoke=true;




}
