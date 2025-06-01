package backend.Service;

public interface UserService {
    Boolean login(String username, String password);

    String register(String username, String password, String confirmPassword);
}
