package logic;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import dal.Directory;
import dal.File;

public class FileManager {
    private final Directory root;

    public FileManager() {
        this.root = new Directory("root");
    }

    public void addFile(String parentDirName, String fileName, long fileSize) {
        Directory parentDir = findDirectory(parentDirName);
        if (parentDir != null) {
            parentDir.addEntity(new File(fileName, fileSize));
        } else {
            System.out.println("Parent directory not found: " + parentDirName);
        }
    }

    public void addDir(String parentDirName, String dirName) {
        Directory parentDir = findDirectory(parentDirName);
        if (parentDir != null) {
            parentDir.addEntity(new Directory(dirName)); // could throw error file already exists
        } else {
            System.out.println("Parent directory not found: " + parentDirName);
        }
    }

    public Long getFileSize(String fileName) {
        File file = findFile(fileName);
        return file != null ? file.getSize() : null;
    }

    public String getBiggestFile() {
        return findBiggestFile(root).map(file -> file.getName()).orElse(null);
    }

    private Optional<File> findBiggestFile(Directory dir) {
        return dir.getContents().values().stream()
                .flatMap(entity -> entity instanceof File
                        ? Stream.of((File) entity)
                        : findBiggestFile((Directory) entity).stream())
                .max(Comparator.comparingLong(file -> file.getSize()));
    }

    public void showFileSystem() {
        root.display("");
    }

    public void delete(String name) {
        deleteEntity(root, name);
    }

    private boolean deleteEntity(Directory dir, String name) {
        if (dir.getContents().containsKey(name)) {
            dir.getContents().remove(name);
            return true;
        }
        return dir.getContents().values().stream()
                .filter(entity -> entity instanceof Directory)
                .map(entity -> (Directory) entity)
                .anyMatch(subDir -> deleteEntity(subDir, name));
    }

    private Directory findDirectory(String name) {
        return findDirectory(root, name);
    }

    private Directory findDirectory(Directory dir, String name) {
        if (dir.getName().equals(name)) {
            return dir;
        }
        return dir.getContents().values().stream()
                .filter(entity -> entity instanceof Directory)
                .map(entity -> (Directory) entity)
                .map(subDir -> findDirectory(subDir, name))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private File findFile(String name) {
        return findFile(root, name);
    }

    private File findFile(Directory dir, String name) {
        return dir.getContents().values().stream()
                .flatMap(entity -> entity instanceof File
                        ? Stream.of((File) entity)
                        : entity instanceof Directory
                        ? Stream.ofNullable(findFile((Directory) entity, name))
                        : Stream.empty())
                .filter(file -> file != null && file.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
