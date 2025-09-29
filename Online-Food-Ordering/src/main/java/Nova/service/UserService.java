package Nova.service;

import Nova.model.User;

public interface UserService {
    public User findUserByJustToken(String jwt) throws Exception;

    public User findUserByEmail(String email) throws Exception;

}
