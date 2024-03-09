public class User {
    private String username;
    private String password;

    // Constructor to initialize a User with a username and password
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter method to retrieve the username of the User
    public String getUsername() {
        return username;
    }

    // Setter method to set or update the username of the User
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter method to retrieve the password of the User
    public String getPassword() {
        return password;
    }

    // Setter method to set or update the password of the User
    public void setPassword(String password) {
        this.password = password;
    }

}
