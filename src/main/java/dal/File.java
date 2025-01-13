package dal;

class File extends Entity {
    long size;

    public File(String name, long size) {
        super(name);
        this.size = size;
    }

    @Override
    public void display(String indent) {
        System.out.println(indent + "File: " + name + ", Size: " + size + " bytes, Created: " + creationDate);
    }
}

