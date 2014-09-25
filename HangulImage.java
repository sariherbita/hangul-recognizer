/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.awt.image.BufferedImage;
import java.io.File;

/**
 *
 * @author Herbita
 */
public class HangulImage {
    private final BufferedImage image;
    private final File file;
    private int height;
    private int width;
    private int redPixelMatrix[][];
    private int greenPixelMatrix[][];
    private int bluePixelMatrix[][];
    private int pixelMatrix[][];
    
    public HangulImage(){
        image = null;
        file = null;
    }
    
    public HangulImage(BufferedImage image,File file){
        this.image = image;
        this.file = file;
    }
    
    public BufferedImage getImage(){
        return image;
    }
    
    public void setHeight(int height){
        this.height = height;
    }
    
    public void setWidth(int width){
        this.width = width;
    }
    
    public void setRedPixelMatrix(int[][] redPixelMatrix){
        this.redPixelMatrix = redPixelMatrix;
    }
    
    public void setGreenPixelMatrix(int[][] greenPixelMatrix){
        this.greenPixelMatrix = greenPixelMatrix;
    }
    
    public void setBluePixelMatrix(int[][] bluePixelMatrix){
        this.bluePixelMatrix = bluePixelMatrix;
    }
    
    public void setPixelMatrix(int[][] pixelMatrix){
        this.pixelMatrix = pixelMatrix;
    }
    
    public void setPixelMatrix(int x,int y,int pixel){
        this.pixelMatrix[x][y] = pixel;
    }
    
    public int getHeight(){
        return this.height;
    }
    
    public int getWidth(){
        return this.width;
    }
    
    public int getRedPixelMatrix(int x,int y){
        return redPixelMatrix[x][y];
    }
    
    public int getGreenPixelMatrix(int x,int y){
        return greenPixelMatrix[x][y];
    }
    
    public int getBluePixelMatrix(int x,int y){
        return bluePixelMatrix[x][y];
    }
    
    public int getPixelMatrix(int x,int y){
        return pixelMatrix[x][y];
    }
    
    public int[][] getPixelMatrix(){
        return pixelMatrix;
    }
}
