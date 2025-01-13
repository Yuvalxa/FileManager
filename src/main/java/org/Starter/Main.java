package org.Starter;

import logic.FileManager;

public class Main {
    public static void main(String[] args) {
        FileManager fs = new FileManager();
        fs.addDir("root", "Documents");
        fs.addFile("Documents", "resume.pdf", 50000);
        fs.addFile("Documents", "cover_letter.docx", 20000);
        fs.addDir("root", "Pictures");
        fs.addFile("Pictures", "photo.jpg", 1500000);
        fs.addDir("Pictures", "Vacation");
        fs.addFile("Vacation", "beach.png", 500000);

        System.out.println("File system structure:");
        fs.showFileSystem();

        System.out.println("Biggest file: " + fs.getBiggestFile());
        System.out.println("Size of 'resume.pdf': " + fs.getFileSize("resume.pdf") + " bytes");

        fs.delete("Vacation");
        System.out.println("After deleting 'Vacation':");
        fs.showFileSystem();    }
}