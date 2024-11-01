package com.chxt.fileserver.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileSystemDetail {
    public String totalSpace;

    public String usableSpace;

    public String usedSpace;


}
