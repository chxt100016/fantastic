package com.chxt.fileserver.file;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileInfoParam {
    // 0:all 1:file 2:directory
    private int type;
    private String path;
    private String search;
}
