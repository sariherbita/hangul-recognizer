/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.HangulImage;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Herbita
 */
public class HangulImageController {
    private HangulImage imageFile;
    private String fileName;
    
    public HangulImage open(){
        JFileChooser jfc = new JFileChooser("dataset");
        jfc.setFileFilter(new FileNameExtensionFilter("Image File", "jpg"));
        int returnVal = jfc.showOpenDialog(null);
        if(returnVal==JFileChooser.APPROVE_OPTION){
            try {
                File file = jfc.getSelectedFile();
                BufferedImage bufferedImage = ImageIO.read(file);
                fileName = file.getName();
                imageFile = new HangulImage(bufferedImage, file);
                readImagePixel(imageFile);
            } catch (IOException ex) {
                Logger.getLogger(HangulImageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return imageFile;
    }
    
    public void readImagePixel(HangulImage imageFile){
        BufferedImage image = imageFile.getImage();
        int height = image.getHeight();
        int width = image.getWidth();
        int redPixel[][] = new int[width][height];
        int greenPixel[][] = new int[width][height];
        int bluePixel[][] = new int[width][height];
        for(int y=0;y<height;y++){
            for(int x=0;x<width;x++){
                Color color = new Color(image.getRGB(x, y));
                redPixel[x][y] = color.getRed();
                greenPixel[x][y] = color.getGreen();
                bluePixel[x][y] = color.getBlue();
            }
        }
        imageFile.setHeight(height);
        imageFile.setWidth(width);
        imageFile.setRedPixelMatrix(redPixel);
        imageFile.setGreenPixelMatrix(greenPixel);
        imageFile.setBluePixelMatrix(bluePixel);
    }
    
    public void refreshImage(){
        int pixel;
        BufferedImage image = imageFile.getImage();
        for(int y=0;y<imageFile.getHeight();y++){
            for(int x=0;x<imageFile.getWidth();x++){
                pixel = imageFile.getPixelMatrix(x, y);
                pixel = (pixel<<16) | (pixel<<8) | pixel;
                image.setRGB(x, y, pixel);
            }
        }
    }
    
    public String getFileName(){
        return fileName;
    }
}
