package dto;

public class LoginUserDto {
    private String name;
    private String email;
    private String userId;
    private String password;

    public LoginUserDto(String userId, String password, String name, String email) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.email = email;
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
