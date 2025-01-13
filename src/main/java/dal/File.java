package dal;

public class File extends Entity {
    long size;

    public File(String name, long size) {
        super(name);
        setSize(size);
    }

    public void setSize(long size) {
        if(size <0)
            throw new IllegalArgumentException("Name cannot exceed 32 characters.");
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

