package com.callfire.api11.client.api.common.model.request;

import java.io.File;

/**
 * Request with file attachment
 */
public interface FileAttachment {
    File getFile();

    String getFileParamName();
}
