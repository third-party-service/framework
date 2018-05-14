package com.jzg.framework.dfs.fastdfs;

/**
 * Created by JZG on 2016/11/29.
 */
public class DFSFile {

    private String name;

    private byte[] content;

    private String ext;

    private String height;

    private String width;

    private String author;

    public DFSFile(String name, byte[] content, String ext, String height,
                   String width, String author) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.height = height;
        this.width = width;
        this.author = author;
    }

    public DFSFile(String name, byte[] content, String ext) {
        super();
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
