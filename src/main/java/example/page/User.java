package example.page;



public class User {
    private static int count = 0;

    private int id;
    private String name;
    private String email;
    private String removeLink;  // New field

    public User(String name, String email, String removeLink) {
        this.id = count++;
        this.name = name;
        this.email = email;
        this.removeLink = removeLink;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRemoveLink() {  // New method
        return removeLink;
    }
}

