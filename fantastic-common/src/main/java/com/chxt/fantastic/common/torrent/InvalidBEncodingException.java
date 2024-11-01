

package com.chxt.fantastic.common.torrent;

import java.io.IOException;

public class InvalidBEncodingException extends IOException {

    public static final long serialVersionUID = -1;

    public InvalidBEncodingException(String message) {
        super(message);
    }
}
