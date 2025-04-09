package com.chxt.fileserver.file;


import com.chxt.fantastic.common.file.UnitConverter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/file")
public class FileSystemController {

    @Value("${root.path}")
    private String prefix;

    @RequestMapping("/system")
    public List<FileSystemDetail> system() {
        File[] files = File.listRoots();

        List<FileSystemDetail> res = new ArrayList<>();

        for (File file : files) {
            FileSystemDetail item = new FileSystemDetail();
            long totalSpace = file.getTotalSpace();
            long usableSpace = file.getUsableSpace();

            item.setTotalSpace(UnitConverter.B2GB(totalSpace));
            item.setUsableSpace(UnitConverter.B2GB(usableSpace));
            item.setUsedSpace(UnitConverter.B2GB(totalSpace - usableSpace));
            res.add(item);
        }
        return res;
    }

    @RequestMapping("/ls")
    public List<FileInfo> ls(String path) {
        path = prefix + (path == null ? "/" : path);
        File[] files = new File(path).listFiles();
        if (files == null) return new ArrayList<>();
        List<FileInfo> res = this.setList(files);
        res = res.stream().sorted(Comparator.comparing(FileInfo::getName)).collect(Collectors.toList());
        return res;
    }

    @RequestMapping("/getFile")
    public List<FileInfo> getFile(@RequestBody FileInfoParam param) {
        File[] files = new File(prefix + param.getPath()).listFiles();
        if (files == null) return new ArrayList<>();
        if (param.getType() != 0) {
            boolean isDir = param.getType() == 2;
            files = Arrays.stream(files).filter(item -> item.isDirectory() == isDir).toArray(File[]:: new);
        }
        List<FileInfo> res = this.setList(files);
        res = res.stream().sorted(Comparator.comparing(FileInfo::getName)).collect(Collectors.toList());

        String[] wordArr = param.getSearch().split(" ");
        res = res.stream().filter(item-> {
            for (String word : wordArr) {
                if (item.getName().toLowerCase().contains(word.toLowerCase())) return true;
            }
            return false;
        }).collect(Collectors.toList());
        return res;
    }

    private List<FileInfo> setList(File[] files) {
        List<FileInfo> res = Arrays.stream(files).filter(item->!item.isHidden()).map(item -> {
            String detect = "";
            String size = "";
            try {
                if (item.isFile()) {
                    detect = new Tika().detect(item);
                    size = this.getDisplaySize(item.length());
                } else {
                    detect = "directory";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return FileInfo.builder()
                .name(item.getName())
                .size(size)
                .isDirectory(item.isDirectory())
                .type(detect)
                .lastModifiedDate(DateFormatUtils.format(new Date(item.lastModified()), "yyyy-MM-dd HH:mm:ss"))
                .path("/".equals(prefix) ? item.getPath() : item.getPath().replaceFirst(this.prefix, ""))
                .build();
        }).collect(Collectors.toList());
        return res;
    }

    @RequestMapping("/getSize")
    public String getSize(String path) {
        File file = new File(this.getRealPath(path));
        long l = FileUtils.sizeOfDirectory(file);
        return this.getDisplaySize(l);
    }

    @RequestMapping("/delete")
    public boolean delete(String path) throws IOException {
        File file = new File(this.getRealPath(path));
        if (!file.exists()) return false;
        if (file.isFile()) {
            return file.delete();
        } else {
            FileUtils.deleteDirectory(file);
            return true;
        }
    }

    @RequestMapping("/rename")
     public boolean rename(String path, String name) {
        path = this.getRealPath(path);
        String newPath = path.substring(0, path.lastIndexOf("/")+1) + name;
        File file = new File(path);
        return file.renameTo(new File(newPath));
    }

    @RequestMapping("/move")
    public boolean move(String newPath, String oldPath) throws IOException {
        File src = new File(this.getRealPath(oldPath));
        File dest = new File(this.getRealPath(newPath));
        FileUtils.moveToDirectory(src, dest, false);
        return true;
    }

    @RequestMapping("/addFolder")
    public boolean addFolder(String path){
        File file = new File(this.getRealPath(path));
        return file.mkdirs();
    }

    @RequestMapping("/downloadFile")
    public void downloadFile(String path, HttpServletResponse response) throws IOException {
        File file = new File(this.getRealPath(path));
        String filename = file.getName();

        FileInputStream fileInputStream = new FileInputStream(file);
        InputStream fis = new BufferedInputStream(fileInputStream);
        byte[] buffer = new byte[fis.available()];
        int length = fis.read(buffer);
        fis.close();

        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20"));
        response.addHeader("Content-Length", "" + length);
        response.getOutputStream().write(buffer);
    }

    @RequestMapping("/upload")
    public void upload(@RequestParam("path") String path, @RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        path = this.getRealPath(path, fileName);
        File dest = new File(path);
        file.transferTo(dest);

    }

    private String getRealPath(String path, String name) {
        path = this.getRealPath(path);
        if ("/".equals(path) || "".equals(path)) return "/" + name;
        else return path + "/" + name;
    }

    private String getRealPath(String path) {
        if ("/".equals(path) || "".equals(path)) return prefix;
        else return prefix + path;
    }

    private String getDisplaySize(Long size){
        Double num = Double.valueOf(size);
        int count = 0;
        String unit = "";
        while(count < 4) {
            Double tmp = num / 1024;
            if (tmp > 1) {
                count ++;
                num = tmp;
            } else {
                break;
            }
        }
        if (count == 0) {
            unit = "B";
        } else if (count == 1) {
            unit = "KB";
        } else if(count == 2) {
            unit = "MB";
        } else if (count == 3) {
            unit = "GB";
        } else if (count ==4) {
            unit = "TB";
        }
        return String.format("%.2f %s", num, unit);
    }
}
