package Nova.service;

import Nova.Config.JwtProvider;
import Nova.model.User;
import Nova.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;



    @Override
    public User findUserByJustToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtTokens(jwt);
        User user=findUserByEmail(email);
        return user;

    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user=userRepository.findByEmail(email);

        if(user==null){
            throw new Exception("user not found");

        }
        return user;
    }
}
