# File System Project

## Overview
The File System Project is a Java-based application designed to simulate a hierarchical file system. The application provides functionality to manage directories and files, including adding, retrieving, deleting, and displaying their contents.

This project utilizes object-oriented programming principles to create a robust and flexible file management system. Key classes include `FileManager`, `Directory`, and `File`, where:
- `FileManager` acts as the main controller for file system operations.
- `Directory` represents a folder in the file system.
- `File` represents a file with attributes like name and size.

---

## Features
- **Add Directory**: Add a new directory to a specified parent directory.
- **Add File**: Add a file to a specified directory with a unique name.
- **Retrieve File Size**: Get the size of a file by its name.
- **Get Largest File**: Find and return the name of the largest file in the system.
- **Delete File/Directory**: Delete a file or directory by its name.
- **Display File System**: Display the entire file system hierarchy starting from the root directory.

---

## Installation
### Prerequisites
- Java Development Kit (JDK) 14
- Gradle (Build tool)
- An Integrated Development Environment (IDE) such as IntelliJ IDEA or Eclipse
---


## Class Overview
### `FileManager`
- Main class for managing the file system.
- Methods:
  - `addFile(String parentDirName, String fileName, long fileSize)`
  - `addDir(String parentDirName, String dirName)`
  - `getFileSize(String fileName)`
  - `getBiggestFile()`
  - `showFileSystem()`
  - `delete(String name)`

### `Directory`
- Represents a folder that can contain files and subdirectories.
- Attributes:
  - `name` (String): Name of the directory.
  - `contents` (Map<String, Object>): Contents of the directory (files and subdirectories).

### `File`
- Represents a file with a name and size.
- Attributes:
  - `name` (String): Name of the file.
  - `size` (long): Size of the file in bytes.

---

## Future Enhancements
- **Search Functionality**: Add a method to search for files or directories by partial name match.
- **File System Persistence**: Save the file system structure to a file and load it on application startup.
- **Permission System**: Add support for file and directory permissions.

---
### Running Tests

Example tests snippet: 
you can check more in FileManagerTester
```java

    @Test
    void testDelete() {
        fs.delete("Vacation");
        assertNull(fs.getFileSize("beach.png"));
        fs.delete("Documents");
        assertNull(fs.getFileSize("resume.pdf"));
    }
    @Test
    void testMaxNameLength() {
        String longFileName = "a".repeat(32); // 32-character file name
        fs.addFile("Documents", longFileName, 12345);
        assertEquals(12345, fs.getFileSize(longFileName));
        fs.delete(longFileName);

        String longDirName = "b".repeat(32); // 32-character directory name
        fs.addDir("root", longDirName);
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
```

## Design Details
- **Uniqueness**: Each file and directory must have a unique name within its parent directory.
- **Error Handling**:
  - If a file or directory with the same name already exists, an error is thrown.
  - If a parent directory is not found, appropriate messages are displayed.
- **Recursion**: Many operations use recursion to navigate the hierarchical structure.

---


## License
This project is licensed under the MIT License. See the LICENSE file for more details.

