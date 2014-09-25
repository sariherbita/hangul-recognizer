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
public final class Binarization {
    int histogram[] = new int[256];
    double pixelProbability[] = new double[256];
    double zerothCM = 0;
    double firstCM = 0;
    double mean = 0;
    double variance = 0;
    double maxVariance = 0;
    int threshold = 0;
    HangulImage imageFile;
    
    public Binarization(HangulImage imageFile){
        this.imageFile = imageFile;
        computeHistogram();
        computeOtsuThreshold();
        binarize();
    }
    
    public void computeHistogram(){
        int pixel;
        for(int y=0;y<imageFile.getHeight();y++){
            for(int x=0;x<imageFile.getWidth();x++){
                pixel = imageFile.getPixelMatrix(x, y);
                histogram[pixel]++;
            }
        }
    }
    
    public void computeOtsuThreshold(){
        for(int i=0;i<pixelProbability.length;i++){
            pixelProbability[i] = (double) histogram[i] / (imageFile.getHeight()*imageFile.getWidth());
        }
        for(int i=0;i<pixelProbability.length;i++){
            mean += i * pixelProbability[i];
        }
        for(int i=0;i<pixelProbability.length;i++){
            zerothCM += pixelProbability[i];
            firstCM += pixelProbability[i] * i;
            variance = Math.pow((mean*zerothCM)-firstCM, 2) / (zerothCM*(1-zerothCM));
            if(variance > maxVariance){
                maxVariance = variance;
                threshold = i;
            } else {
            }
        }
    }
    
    public void binarize(){
        for(int y=0;y<imageFile.getHeight();y++){
            for(int x=0;x<imageFile.getWidth();x++){
                if(imageFile.getPixelMatrix(x, y) > threshold |
                        imageFile.getPixelMatrix(x, y) == threshold){
                    imageFile.setPixelMatrix(x, y, 255);
                }
                else{
                    imageFile.setPixelMatrix(x, y, 0);
                }
            }
        }
    }
}
