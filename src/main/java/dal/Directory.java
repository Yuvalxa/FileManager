package dal;

import java.util.HashMap;
import java.util.Map;

// Directory class
class Directory extends Entity {
    Map<String, Entity> contents = new HashMap<>();

    public Directory(String name) {
        super(name);
    }

    public void addEntity(Entity entity) {
        contents.put(entity.name, entity);
    }

    public void removeEntity(String name) {
        contents.remove(name);
    }

    public Entity getEntity(String name) {
        return contents.get(name);
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "Directory: " + name + ", Created: " + creationDate);
        contents.values().forEach(entity -> entity.display(indent + "  "));
    }
}
