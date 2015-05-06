package com.thoughtworks.tools.tsc.out.impl;

import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.out.IOuter;
import com.thoughtworks.tools.tsc.util.StringUtils;

import java.io.*;
import java.util.Map;
import java.util.Set;

public class FileLogImpl implements IOuter {

    private String saveFilePath;


    public FileLogImpl(String saveFilePath) {
        File saveFile = new File(saveFilePath);
        if (saveFile.isDirectory()) {
            throw new IllegalArgumentException("Save file path is a directory!");
        }
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.saveFilePath = saveFilePath;
    }

    @Override
    public void output(Map<String, Map<String, Set<MismatchProperties>>> result) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveFilePath)));
            try {
                bw.write(StringUtils.generateMsg(result));
            } finally {
                if (null != bw) {
                    bw.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
