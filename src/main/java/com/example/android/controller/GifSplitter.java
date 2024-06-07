package com.example.android.controller;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;

public class GifSplitter {
    public static void splitGifToFrames(String gifPath, String outputFolder,String a) throws IOException {
        // 打开GIF图片文件
        File gifFile = new File(gifPath+a+".gif");
        ImageInputStream stream = ImageIO.createImageInputStream(gifFile);
        Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("gif");

        if (!readers.hasNext()) {
            throw new IOException("No GIF readers found");
        }

        ImageReader reader = readers.next();
        reader.setInput(stream);

        // 创建输出文件夹
        File outputDir = new File(outputFolder);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        // 获取帧数
        int numFrames = reader.getNumImages(true);

        for (int i = 0; i < numFrames; i++) {
            if(i==0){
                // 读取每一帧
                BufferedImage frame = reader.read(i);
                // 构建每一帧的文件名
                File outputFile = new File(outputFolder, "frame_" + a + ".gif");
                // 保存当前帧
                ImageIO.write(frame, "gif", outputFile);
                break;
            }
        }

        System.out.println("GIF拆分完成，共生成" + numFrames + "个帧文件哦~");
    }
    public static void compressGif(String inputPath, String outputPath, float quality,String a) throws IOException {
        // 读取原始GIF文件
        File inputFile = new File(inputPath+a+".gif");
        BufferedImage originalImage = ImageIO.read(new FileInputStream(inputFile));

        // 获取GIF格式的ImageWriter
        ImageWriter writer = ImageIO.getImageWritersByFormatName("gif").next();
        ImageWriteParam param = writer.getDefaultWriteParam();

        // 设置压缩质量参数（虽然对于GIF来说，压缩参数可能影响不大）
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        // 创建输出文件
        File outputFile = new File(outputPath+a+".gif");
        try (ImageOutputStream outputStream = ImageIO.createImageOutputStream(new FileOutputStream(outputFile))) {
            writer.setOutput(outputStream);

            // 压缩并写入图像
            writer.write(null, new IIOImage(originalImage, null, null), param);
        }

        writer.dispose();
        System.out.println("GIF压缩完成啦~");
    }

    public static void main(String[] args) {
        // 使用示例
        //C:\Users\19674\Desktop\成就
        String gifPath = "C:\\Users\\19674\\Desktop\\output\\achievementGif\\";
        String outputFolder = "C:\\Users\\19674\\Desktop\\output1";

//        try {
//            splitGifToFrames(gifPath, outputFolder,"c3");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        float quality = 0.8f; // 质量参数（0.0 - 1.0），值越高质量越好

        try {
            compressGif(gifPath, outputFolder, quality,"必属精品！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

