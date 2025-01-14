import dal.Directory;
import dal.File;
import logic.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTester {

    FileManager fs;

    @BeforeEach
    void setup() {
        //Basic Test adding folder and files
        fs = new FileManager();
        fs.addDir("root", "Documents");
        fs.addFile("Documents", "cover_letter.docx", 20000);
        fs.addDir("root", "Pictures");
        fs.addDir("Pictures", "Vacation");
        fs.addFile("Vacation", "beach.png", 500000);
    }
    @Test
    void testAddFoldersAndSubfolders() {
        fs.addDir("root", "ParentFolder");
        fs.addDir("ParentFolder", "ChildFolder");
        fs.addFile("ChildFolder", "file.txt", 1000);

        // Verify hierarchy
        assertEquals(1000, fs.getFileSize("file.txt"));

        // Cleanup
        fs.delete("ChildFolder");
        fs.delete("ParentFolder");
    }
    @Test
    void testAddFile() {
        fs.addFile("Documents", "new_file.txt", 1000);
        assertEquals(1000, fs.getFileSize("new_file.txt"));
    }
    @Test
    void testGetFileSize() {
        fs.addFile("Documents", "resume.pdf", 50000);
        assertEquals(50000, fs.getFileSize("resume.pdf"));
        fs.delete("resume.pdf");
        assertNull(fs.getFileSize("nonexistent_file.txt")); // not a real file
    }
    @Test
    void testNegativeFileSize() {
        assertThrows(IllegalArgumentException.class,()->fs.addFile("Documents", "resume.pdf", -500));
    }
    @Test
    void testGetBiggestFile() {
        fs.addFile("Pictures", "photo.jpg", 15000000);
        fs.addFile("Pictures", "photo2.jpg", 1);
        assertEquals("photo.jpg", fs.getBiggestFile());
        fs.delete("photo.jpg");
        fs.delete("photo2.jpg");
    }
    @Test
    void testDeleteFolder() { // Case: if folder deleted files inside gets deletes as well
        fs.delete("Vacation");
        assertNull(fs.getFileSize("beach.png"));
    }
    @Test
    void testLongNames() {
        String longFileName = "a".repeat(64); //Over  32-character file name
        assertThrows(IllegalArgumentException.class,()->fs.addFile("Documents", longFileName, 12345));
        fs.delete(longFileName);

        String longDirName = "b".repeat(64); //Over 32-character directory name
        assertThrows(IllegalArgumentException.class,()-> fs.addDir("root", longDirName));
        fs.delete(longDirName);
    }
    @Test
    void testUniqueFileName() {
        fs.addFile("Documents", "unique_file.txt", 25000);
        assertThrows(IllegalArgumentException.class, () -> fs.addFile("Documents", "unique_file.txt", 5000));
        fs.delete("unique_file.txt");
    }
    @Test
    void testUniqueDirName() {
        fs.addDir("root", "UniqueDirectory");
        assertThrows(IllegalArgumentException.class, () -> fs.addDir("root", "UniqueDirectory"));
        fs.delete("UniqueDirectory");
    }
}
