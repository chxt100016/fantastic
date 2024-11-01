package com.chxt.fantasticmonkey;

import com.chxt.fantastic.common.file.UnitConverter;
import kotlin.Unit;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.DecimalFormat;

public class MethodTest {
    public static void main(String[] args) {
        File[] files = File.listRoots();
        for (File file : files) {
            long totalSpace = file.getTotalSpace();
            long usableSpace = file.getUsableSpace();
            long freeSpace = file.getFreeSpace();
            DecimalFormat df = new DecimalFormat("#.00");
            System.out.println(UnitConverter.B2GB(usableSpace) + "/" + UnitConverter.B2GB(totalSpace));
        }

    }
}
