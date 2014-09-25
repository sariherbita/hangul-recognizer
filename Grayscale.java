/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.HangulImage;

/**
 *
 * @author Herbita
 */
public final class Grayscale {
    public Grayscale(HangulImage imageFile){
        doGrayscale(imageFile);
    }
    
    public void doGrayscale(HangulImage imageFile){
        int pixelMatrix[][] = new int[imageFile.getHeight()][imageFile.getWidth()];
        for(int y=0;y<imageFile.getHeight();y++){
            for(int x=0;x<imageFile.getWidth();x++){
                pixelMatrix[x][y] = (int) ((0.21*imageFile.getRedPixelMatrix(x, y))+(0.71*imageFile.getGreenPixelMatrix(x, y))+
                               (0.07*imageFile.getBluePixelMatrix(x, y)));
            }
        }
        imageFile.setPixelMatrix(pixelMatrix);
    }
}
