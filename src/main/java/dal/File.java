package dal;

public class File extends Entity {
    long size;

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    public long getSize() {
        return size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "File: " + name + ", Size: " + size + " bytes, Created: " + creationDate);
    }
}

