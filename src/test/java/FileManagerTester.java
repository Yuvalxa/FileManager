import dal.Directory;
import dal.File;
import logic.FileManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.FileSystem;

import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTester {

    FileManager fs;

    @BeforeEach
    void setup() {
        fs = new FileManager();
        fs.addDir("root", "Documents");
        fs.addFile("Documents", "resume.pdf", 50000);
        fs.addFile("Documents", "cover_letter.docx", 20000);
        fs.addDir("root", "Pictures");
        fs.addFile("Pictures", "photo.jpg", 1500000);
        fs.addDir("Pictures", "Vacation");
        fs.addFile("Vacation", "beach.png", 500000);
    }

    @Test
    void testAddFile() {
        fs.addFile("Documents", "new_file.txt", 1000);
        assertEquals(1000, fs.getFileSize("new_file.txt"));
    }

    @Test
    void testGetFileSize() {
        assertEquals(50000, fs.getFileSize("resume.pdf"));
        assertNull(fs.getFileSize("nonexistent_file.txt"));
    }

    @Test
    void testGetBiggestFile() {
        assertEquals("photo.jpg", fs.getBiggestFile());
    }

    @Test
    void testShowFileSystem() {
        // This test should validate output to console. Integration testing or manual verification might be needed.
        fs.showFileSystem();
    }

    @Test
    void testDelete() {
        fs.delete("Vacation");
        assertNull(fs.getFileSize("beach.png"));
        fs.delete("Documents");
        assertNull(fs.getFileSize("resume.pdf"));
    }
}
