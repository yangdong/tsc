package com.thoughtworks.tools.tsc.out.impl;

import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.out.IOuter;

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
                for (Map.Entry<String, Map<String, Set<MismatchProperties>>> value : result.entrySet()) {
                    for (Map.Entry<String, Set<MismatchProperties>> entry : value.getValue().entrySet()) {
                        bw.write("-----"+entry.getKey() + " [project:" + value.getKey() + "]");
                        for (MismatchProperties properties : entry.getValue()) {
                            bw.newLine();
                            bw.write(properties.toString());
                        }
                        bw.write("\n\n");
                    }
                }
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
