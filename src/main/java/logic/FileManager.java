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
    /**
     * Adds a file to a specified directory if the directory exists and the file name is unique.
     * Time Complexity: O(n), where n is the total number of directories (find duplication) ,
     * Space Complexity: O(1).
     */
    public void addFile(String parentDirName, String fileName, long fileSize) {
        Directory parentDir = findDirectory(parentDirName);
        if (parentDir != null) {
            if (parentDir.getContents().containsKey(fileName)) { // Todo throw error already exists
                throw new IllegalArgumentException("File or directory with the name '" + fileName + "' already exists in directory: " + parentDirName);
            } else {
                parentDir.addEntity(new File(fileName, fileSize));
            }
        } else {
            System.out.println("Parent directory not found: " + parentDirName);
        }
    }
    /**
     * Adds a directory to a specified parent directory if it exists and the directory name is unique.
     * Time Complexity: O(n), where n is the total number of directories. (check for unique)
     * Space Complexity: O(1).
     */
    public void addDir(String parentDirName, String dirName) {
        Directory parentDir = findDirectory(parentDirName);
        if (parentDir != null) {
            if (parentDir.getContents().containsKey(dirName)) { // Todo throw error already exists
                throw new IllegalArgumentException("Directory  with the name '" + dirName + "' already exists in directory: " + parentDirName);
            } else {
                parentDir.addEntity(new Directory(dirName));
            }
        } else {
            System.out.println("Parent directory not found: " + parentDirName);
        }
    }
    /**
     * Retrieves the size of a file by its name.
     * Time Complexity: O(n + m), where n is the number of directories and m is the number of files.
     * Space Complexity: O(1).
     */
    public Long getFileSize(String fileName) {
        File file = findFile(fileName);
        return file != null ? file.getSize() : null;
    }
    /**
     * Finds the biggest file in the entire file system.
     * Time Complexity: O(n + m), where n is the number of directories and m is the number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
    public String getBiggestFile() {
        return findBiggestFile(root).map(file -> file.getName()).orElse(null);
    }
    /**
     * Helper method to find the biggest file within a directory and its subdirectories.
     * Time Complexity: O(n + m), where n is the total number of directories and m is the total number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
    private Optional<File> findBiggestFile(Directory dir) {
        return dir.getContents().values().stream()
                .flatMap(entity -> entity instanceof File
                        ? Stream.of((File) entity)
                        : findBiggestFile((Directory) entity).stream())
                .max(Comparator.comparingLong(file -> file.getSize()));
    }
    /**
     * Displays the file system hierarchy starting from the root directory.
     * Time Complexity: O(n + m), where n is the number of directories and m is the number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
    public void showFileSystem() {
        root.display("");
    }
    /**
     * Deletes a file or directory by name if it exists.
     * Time Complexity: O(n + m), where n is the number of directories and m is the number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
    public void delete(String name) {
        deleteEntity(root, name);
    }
    /**
     * Helper method to delete an entity (file or directory) by name.
     * Time Complexity: O(n + m), where n is the number of directories and m is the number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
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
    /**
     * Finds a directory by name.
     * Time Complexity: O(n), where n is the total number of directories.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
    private Directory findDirectory(String name) {
        return findDirectory(root, name);
    }
    /**
     * Recursive helper method to find a directory by name.
     * Time Complexity: O(n), where n is the total number of directories.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
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
    /**
     * Finds a file by name.
     * Time Complexity: O(n + m), where n is the total number of directories and m is the total number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
    private File findFile(String name) {
        return findFile(root, name);
    }
    /**
     * Recursive helper method to find a file by name.
     * Time Complexity: O(n + m), where n is the total number of directories and m is the total number of files.
     * Space Complexity: O(h), where h is the maximum depth of the directory tree.
     */
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
