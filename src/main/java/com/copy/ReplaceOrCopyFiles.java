package com.copy;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReplaceOrCopyFiles {
    private static final String templatePortal = "comm_png";
    private static final String[] targetPortals = {"comm_sony", "comm_bacardi", "comm_jnj", "comm_barclays", "comm_kelloggs",
            "comm_ee", "comm_coty", "comm_default", "comm_nestle", "comm_keyaccts", "comm_samsung"};

 /* private static final String templatePortal = "ergo-nz-ps";
    private static final String[] targetPortals = {"ergo-unilever-au",
            "ergo-unilever-nz",
            "ergo-qld",
            "ergo-qld-health",
            "ergo-qld-qut",
            "ergo-si",
            "ergo-pacbrands"};*/
    public static List<String> getTargetAbsolutePaths(String path) {
        List<String> paths = new ArrayList<>();
        for (String targetPortal : targetPortals) {
            String copy = path;
            paths.add(copy.replaceFirst(templatePortal, targetPortal));
        }
        return paths;
    }

    public static void crateFileBaseOnAbsolutePath(String path) {
        File file = new File(path);
        file.getParentFile().mkdirs();
        try {
            boolean isSuccess = file.createNewFile();
            if (!isSuccess) {
                //System.out.println("already exist!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*public static void replaceFile(String fromAbsolutePath, String targetAbsolutePath) {
        File targetFile = new File(targetAbsolutePath);
        if (targetFile.exists()) {
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(fromAbsolutePath);
                fileOutputStream = new FileOutputStream(targetAbsolutePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileInputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(targetAbsolutePath + " isn't exist");
        }
    }*/
    public static void replaceFile(String fromAbsolutePath, String targetAbsolutePath) {
        File targetFile = new File(targetAbsolutePath);
        if (targetFile.exists()) {
            FileInputStream fileInputStream = null;
            FileOutputStream fileOutputStream = null;
            try {
                fileInputStream = new FileInputStream(fromAbsolutePath);
                fileOutputStream = new FileOutputStream(targetAbsolutePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fileInputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, length);
                }
                //fileOutputStream.write("Happy Friday".getBytes(StandardCharsets.UTF_8));
                //continueWrite(fileOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    fileInputStream.close();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println(targetAbsolutePath + " isn't exist");
        }
    }
    private static void continueWrite(FileOutputStream fileOutputStream) throws IOException {
        fileOutputStream.write("Happy 2022!!!".getBytes(StandardCharsets.UTF_8));
    }


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        String page1 = "N:\\sprint-enterprise\\modules\\resource\\ui\\comm_png\\en\\procurement\\estimating\\estimate\\customization\\ViewEstimateItemEnvironment.html";
        String page2 = "N:\\sprint-enterprise\\modules\\resource\\ui\\comm_png\\en\\procurement\\ordering\\change\\customization\\ViewChgOrderItemEnvironment.html";
        String page3 = "N:\\sprint-enterprise\\modules\\resource\\ui\\comm_png\\en\\procurement\\ordering\\order\\customization\\ViewOrderItemEnvironment.html";
        list.add(page1);
        list.add(page2);
        list.add(page3);
        for (String fromPath : list) {
            for (String path : getTargetAbsolutePaths(fromPath)) {
                System.out.println(path);
                crateFileBaseOnAbsolutePath(path);
                replaceFile(fromPath, path);
            }
        }
    }
}
