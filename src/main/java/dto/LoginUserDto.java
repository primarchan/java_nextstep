package dto;

public class LoginUserDto {
    private String name;
    private String email;
    private String userId;
    private String password;

    public LoginUserDto(String userId, String email, String name, String password) {
        this.userId = userId;
        this.password = email;
        this.name = name;
        this.email = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginUserDto: [" + "name = " + name + ", email = " + email + ", userId = " + userId + ", password = " + password + ']';
    }

}
