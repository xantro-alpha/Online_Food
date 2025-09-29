package Nova.model;
import jakarta.persistence.*;
import Nova.model.Category;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private  String name;

    private  String description;
    private Long price;

    @ManyToOne
    private Category foodcategory;

    @Column(length = 1000)
    @ElementCollection //it will create a separate table for food images
    private List<String> images;

    private boolean available;


    @ManyToOne
    private Restaurant restaurant;

    private boolean isvegetarian;
    private boolean isSeasonal;


    @ManyToMany //many foods have multiple ingredients and that ingredients can also be used by other food like agar pizza banana hai to usme use hone wala ingredients burger me bhi use ho skta hai
    private List<IngredientsItem> ingredients=new ArrayList<>();



    private Date creationDate;












}
