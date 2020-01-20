package librarysystem.entities;

public class Account {
    private String id;
    private String name;

    public Account() {
    }
    public Account(String name) {
        super();
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return "Account with id="+this.id;
    }
}
