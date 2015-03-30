package org.moon.utils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * 图片工具类
 * @author:Gavin
 * @date 2015/3/21 0021
 */
public class Images {
    /**
     * 压缩图片到文件
     * @param originImage
     * @param quality
     * @param file
     * @throws java.io.IOException
     */
    public static void compressImageToFile(BufferedImage originImage,float quality,File file) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = writers.next();
        ImageWriteParam param = imageWriter.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);
        imageWriter.setOutput(ImageIO.createImageOutputStream(new FileInputStream(file)));
        imageWriter.write(null,new IIOImage(originImage,null,null),param);
    }

    /**
     * 压缩图片
     * @param originImage
     * @param quality
     * @return
     * @throws IOException
     */
    public static BufferedImage compressImage(BufferedImage originImage,float quality) throws IOException {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = writers.next();
        ImageWriteParam param = imageWriter.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageOutputStream imageOut = ImageIO.createImageOutputStream(out);
        imageWriter.setOutput(imageOut);
        imageWriter.write(null,new IIOImage(originImage,null,null),param);
        imageOut.flush();
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());

        BufferedImage image = ImageIO.read(in);
        out.close();
        imageOut.close();
        in.close();

        return image;
    }


    /**
     * 将图片转换为正方形尺寸
     * @param originImage
     * @return
     */
    public static BufferedImage toSquare(BufferedImage originImage){
        int width = originImage.getWidth(),height = originImage.getHeight(),
                x = 0 , y = 0;
        System.out.println(width+"..."+height+"..."+x+"...."+y);
        //如果为奇数，则左边或者上边少一像素,以保证不会画到边界去
        if(width>height){//宽度更大，以高度为准
            x = (width-height)/2;
            width = height;
        }else{
            y = (height-width)/2;
            height = width;
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(originImage,0,0,width,height,x,y,width,height,null);
        System.out.println(width+"..."+height+"..."+x+"...."+y);
        return image;
    }

    /**
     * 剪裁图像
     * @param originImage
     * @param x
     * @param y
     * @param width
     * @param height
     * @return
     */
    public static BufferedImage clipImage(BufferedImage originImage ,int x, int y, int width , int height){
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.getGraphics().drawImage(originImage,0,0,width,height,x,y,width,height,null);
        return image;
    }

    /**
     * 缩放图片
     * @param originImage
     * @param width
     * @param height
     * @param ratio 是否等比，如果为true,则width意义为maxWidth,height意义为maxHeight
     * @return
     */
    public static BufferedImage scaleImage(BufferedImage originImage , int width , int height,boolean ratio){
        if(ratio){
            int originWidth = originImage.getWidth(),
                    originHeight = originImage.getHeight();
            double widthScale = (double)originWidth/(double)width,heightScale = (double)originHeight/(double)height;
            if(widthScale > heightScale){//宽度缩放比例更大，以宽度为基准
                height = (int)(originHeight/widthScale);
            }else{
                width = (int)(originWidth/heightScale);
            }
        }
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics().drawImage(originImage.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
        return image;
    }
}
