/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.storage.model;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author jan.horky
 */
public class Document {

    private long id;
    private String extension;

    public Document() {
    }

    public Document(long id, String extension) {
        this.id = id;
        this.extension = extension;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getPath(int depth) {
        final int hexChunk = depth * 2;
        String hexId = String.format("%0" + Integer.toString(hexChunk) + "X", id);
        String paddedHexId = StringUtils.leftPad(hexId, getHexIdSize(hexChunk, hexId.length()), "0");
        return String.format("/%s/%d.%s", Joiner.on("/").join(Splitter.fixedLength(hexChunk).split(paddedHexId)), id, extension);
    }

    private int getHexIdSize(int hexChunk, int hexIdLength) {
        if (hexIdLength <= hexChunk) {
            return hexChunk;
        }

        int hexChunkOriginal = hexChunk;
        int result = hexChunk;

        while (result <= hexIdLength) {
            result += hexChunkOriginal;
        }
        return result;
    }
}
