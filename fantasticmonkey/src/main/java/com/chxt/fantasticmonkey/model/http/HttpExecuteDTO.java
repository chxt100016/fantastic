package com.chxt.fantasticmonkey.model.http;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpExecuteDTO {
    private String uri;
    private List<NameValuePair> params;
    private String message;
    private Object entity;
    private String path;
    private Map<String, String> header;

    private HttpRequestBase httpRequest;
}
