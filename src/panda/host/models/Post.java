package panda.host.models;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.NotNull;
import panda.host.utils.Panda;

import java.io.File;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Post {
    private int id;
    private String authorId;
    private String message;
    private String tags; // List of tags separated by the Panda.DEFAULT_SPLIT_CHAR

    private String fileId; // Automatically generated

    private String fileName;
    private String fileExt;
    private long fileSize;
    private byte[] fileToBytes;

    private Timestamp uploadDate;
    private Timestamp lastUpdate;

    public Post(String authorId, String message, String tags) {
        this.authorId = authorId;
        this.message = message;
        this.tags = tags;
        this.uploadDate = Timestamp.from(Instant.now());
        this.lastUpdate = Timestamp.from(Instant.now());
    }

    public Post(String authorId, String message, String tags, File file){
        this.authorId = authorId;
        this.message = message;
        this.tags = tags;
        setFile(file);
        this.uploadDate = Timestamp.from(Instant.now());
        this.lastUpdate = Timestamp.from(Instant.now());
    }

    public Post(String authorId, String message, String tags, File file, Timestamp uploadDate, Timestamp lastUpdate){
        this.authorId = authorId;
        this.message = message;
        this.tags = tags;
        setFile(file);
        this.uploadDate = uploadDate;
        this.lastUpdate = lastUpdate;
    }

    public Post(String authorId, File file){
        this.authorId = authorId;
        setFile(file);
        this.uploadDate = Timestamp.from(Instant.now());
        this.lastUpdate = Timestamp.from(Instant.now());
    }

    public Post(int id, String authorId, String message, String tags, String fileId, String fileName, String fileExt, long fileSize, Timestamp uploadDate, Timestamp lastUpdate) {
        this.id = id;
        this.authorId = authorId;
        this.message = message;
        this.tags = tags;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.lastUpdate = lastUpdate;
    }

    public Post(int id, String authorId, String message, String tags, String fileId, String fileName, String fileExt, long fileSize, byte[] fileToBytes, Timestamp uploadDate, Timestamp lastUpdate) {
        this.id = id;
        this.authorId = authorId;
        this.message = message;
        this.tags = tags;
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileExt = fileExt;
        this.fileSize = fileSize;
        this.fileToBytes = fileToBytes;
        this.uploadDate = uploadDate;
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", authorId='" + authorId + '\'' +
                ", message='" + message + '\'' +
                ", tags='" + tags + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileExt='" + fileExt + '\'' +
                ", fileSize=" + Panda.convertLongSizeToString(fileSize) +
                ", uploadDate=" + uploadDate +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj.getClass().equals(Post.class)) {
            return id == ((Post) obj).getId();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, authorId);
    }

    public boolean containsAFile(){
        return fileName != null;
    }

    public boolean containsAValidFile(){
        return containsAFile() && fileToBytes != null;
    }

    public void setFile(File file){
        try{
            // e.g: If the file is "src/host/files/folder/document.txt
            this.fileName = FilenameUtils.getBaseName(file.getName()); // fileName = "document"
            this.fileExt = FilenameUtils.getExtension(file.getName()); // fileExt = "txt"
            this.fileSize = file.length(); // fileSize = Size of document.txt in bytes
            // Converts the file to an array of bytes (byte[]), then store it in the fileToBytes property
            this.fileToBytes = FileUtils.readFileToByteArray(file);

        } catch (Exception e) {
            System.out.println("[Post] | Something went wrong while adding the file to the post");
            System.err.println(e.getMessage());
            deleteFileProperties();
        }
    }

    public void deleteFileProperties(){
        this.fileName = null;
        this.fileExt = null;
        this.fileSize = 0;
        this.fileToBytes = null;
    }

    public List<String> getTagsToList(){
        return Arrays.asList(tags.split(Panda.DEFAULT_SPLIT_CHAR));
    }

    public String getTagsToString(){
        var tagsToList = getTagsToList();
        StringBuilder tagsToString = new StringBuilder();
        int i = 0;
        for(var tag : tagsToList){
            if (i < tagsToList.size() - 1){
                tagsToString.append(tag).append(", ");

            } else {
                tagsToString.append(tag);
            }
            i++;
        }
//        System.out.println(tagsToString.toString());
        return tagsToString.toString();
    }

    public String getCompleteFileName(){
        return fileName + "." + fileExt;
    }



    public int getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileExt() {
        return fileExt;
    }

    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileToBytes() {
        return fileToBytes;
    }

    public void setFileToBytes(byte[] fileToBytes) {
        this.fileToBytes = fileToBytes;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
