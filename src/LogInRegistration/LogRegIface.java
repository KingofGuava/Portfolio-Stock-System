package LogInRegistration;

//This is a facade pattern interface
public interface LogRegIface {
    void runLogIn(String username, String password);
    void runRegister(String name, String password, String fullname, String phone, String address, String email);
}


