package dal;

import java.util.Date;

public abstract class Entity {
    protected String name;
    protected Date creationDate;

    public Entity(String name) {
        setName(name);
        this.creationDate = new Date();
    }
    public abstract void display(String indent);

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name.length() > 32) {
            throw new IllegalArgumentException("Name cannot exceed 32 characters.");
        }
        this.name = name;
    }
    public Date getCreationDate() {
        return creationDate;
    }
}
