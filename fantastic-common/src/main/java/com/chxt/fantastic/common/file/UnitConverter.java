package com.chxt.fantastic.common.file;

import java.text.DecimalFormat;

public class UnitConverter {

    public static String B2GB(Long num) {
        DecimalFormat df = new DecimalFormat("#.00");
        return df.format(num/1024.0/1024.0/1024.0);

    }

}
