package com.chxt.fantasticmonkey.model.file;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {
    public String name;
    public String size;
    public boolean isDirectory;
    public String type;
    public String lastModifiedDate;
    public String path;
}
