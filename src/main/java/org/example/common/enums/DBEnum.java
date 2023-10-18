package org.example.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public enum DBEnum {
    DBX("jdbc:mysql://x.x.x.x:3306/x?useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "12345678"),
    DBY("jdbc:mysql://y.y.y.y:3306/y?useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "12345678"),
    ;

    private final String url;
    private final String username;
    private final String password;
}