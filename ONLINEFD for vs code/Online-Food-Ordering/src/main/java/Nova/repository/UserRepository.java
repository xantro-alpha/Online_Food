package Nova.repository;

import Nova.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


//user is the entity , and unique identifier=Long

public interface UserRepository extends JpaRepository<User,Long> {
    // we need to provide some custom methods here ,actually it will provide us
    //method for all the cloud operations
    //delete, add, retrieve data, find by id etc......


    public User findByEmail(String username);
    //it will find user by email
    // at the place of email i can also write fullname , role...which is there in User.java file



}
