package com.chxt.fantasticmonkey.domain.spring;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Getter
public enum SpringEnum {


    A("hr", Arrays.asList("分享", "hr"), "https://www.aibsaba.xyz/forum-798-1.html"),
    B("yz", Arrays.asList("分享", "亚洲"), "https://www.aibsaba.xyz/forum-134-1.html"),
    C("om", Arrays.asList("分享", "欧美"), "https://www.aibsaba.xyz/forum-135-1.html"),
////    E("yz", Arrays.asList("分享", "高清"), "https://www.aibsaba.xyz/forum-233-1.html"),
    D("dm", Arrays.asList("分享", "动漫"), "https://www.aibsaba.xyz/forum-136-1.html"),
//    E("yz", Arrays.asList("分享", "中文字幕"), "https://www.aibsaba.xyz/forum-70-1.html"),
    G("hr", Arrays.asList("原创", "华人"), "https://www.aibsaba.xyz/forum-280-1.html"),

    ;

    private final String type;
    private final List<String> tags;
    private final String url;


}
