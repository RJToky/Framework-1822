package etu1822.framework.utility;

public class FileUpload {
    String name;
    byte[] file;

    public FileUpload(String name, byte[] file) {
        this.name = name;
        this.file = file;
    }

    public FileUpload() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

}
