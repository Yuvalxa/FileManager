package dal;

import java.util.Date;

public abstract class Entity {
    String name;
    Date creationDate;

    public Entity(String name) {
        this.name = name;
        this.creationDate = new Date();
    }
    public abstract void display(String indent);

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
