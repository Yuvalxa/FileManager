package dal;

import java.util.HashMap;
import java.util.Map;

// Directory class
public class Directory extends Entity {
    Map<String, Entity> contents = new HashMap<>();

    public Directory(String name) {
        super(name);
    }

    public void addEntity(Entity entity) {
        if (contents.get(entity.name) != null)
            throw new IllegalArgumentException(entity.name+" already exists");
        contents.put(entity.name, entity);
    }

    public void removeEntity(String name) {
        contents.remove(name);
    }

    public Entity getEntity(String name) {
        return contents.get(name);
    }

    public Map<String, Entity> getContents() {
        return contents;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Directory: " + name + ", Created: " + creationDate);
        contents.values().forEach(entity -> entity.display(indent + "  "));
    }
}
