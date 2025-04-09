package com.chxt.fantasticmonkey.model.aqara;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class AqaraCallBackResponse {
    private Integer code;
}
