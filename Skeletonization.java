/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.HangulImage;
import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author Herbita
 */
public final class Skeletonization {
    boolean sign;
    int thinningMatrix[][];
    ArrayList<Point> points = new ArrayList<>();
    HangulImage imageFile;
    
    public Skeletonization(HangulImage imageFile){
        this.imageFile = imageFile;
        thinningMatrix = new int[imageFile.getWidth()][imageFile.getHeight()];
        matrixConversion(imageFile.getPixelMatrix(), thinningMatrix);
        do{
            sign = false;
            for(int y=1;y<imageFile.getHeight()-1;y++){
                for(int x=1;x<imageFile.getWidth()-1;x++){
                    int a = calcAThinning(x, y);
                    int b = calcBThinning(x, y);
                    firstDeletion(a, b, x, y);
                }
            }
            deleteOuterLine();
            for(int y=1;y<imageFile.getHeight()-1;y++){
                for(int x=1;x<imageFile.getWidth()-1;x++){
                    int a = calcAThinning(x, y);
                    int b = calcBThinning(x, y);
                    secondDeletion(a, b, x, y);
                }
            }
            deleteOuterLine();
        } while(sign);
        matrixConversion(thinningMatrix, imageFile.getPixelMatrix());
        //writeImageToFile();
    }
    
    public void matrixConversion(int[][] oldMatrix,int[][] newMatrix){
        if(oldMatrix == imageFile.getPixelMatrix() && newMatrix == thinningMatrix){
            for(int y=0;y<imageFile.getHeight();y++){
                for(int x=0;x<imageFile.getWidth();x++){
                    if(oldMatrix[x][y]==0) newMatrix[x][y] = 1;
                    else newMatrix[x][y] = 0;
                }
            }
        }
        else if(oldMatrix == thinningMatrix && newMatrix == imageFile.getPixelMatrix()){
            for(int y=0;y<imageFile.getHeight();y++){
                for(int x=0;x<imageFile.getWidth();x++){
                    if(oldMatrix[x][y]==0) newMatrix[x][y] = 255;
                    else newMatrix[x][y] = 0;
                }
            }
        }
    }
    
    public int calcAThinning(int x,int y){
        int a = 0;
        if(thinningMatrix[x+1][y]==0 && thinningMatrix[x+1][y+1]==1) a++;
        if(thinningMatrix[x+1][y+1]==0 && thinningMatrix[x][y+1]==1) a++;
        if(thinningMatrix[x][y+1]==0 && thinningMatrix[x-1][y+1]==1) a++;
        if(thinningMatrix[x-1][y+1]==0 && thinningMatrix[x-1][y]==1) a++;
        if(thinningMatrix[x-1][y]==0 && thinningMatrix[x-1][y-1]==1) a++;
        if(thinningMatrix[x-1][y-1]==0 && thinningMatrix[x][y-1]==1) a++;
        if(thinningMatrix[x][y-1]==0 && thinningMatrix[x+1][y-1]==1) a++;
        if(thinningMatrix[x+1][y-1]==0 && thinningMatrix[x+1][y]==1) a++;
        return a;
    }
    
    private int calcBThinning(int x,int y){
        int b;
        b = thinningMatrix[x+1][y]+thinningMatrix[x+1][y+1]+thinningMatrix[x][y+1]+
                thinningMatrix[x-1][y+1]+thinningMatrix[x-1][y]+thinningMatrix[x-1][y-1]+
                thinningMatrix[x][y-1]+thinningMatrix[x+1][y-1];
        return b;
    }
    
    private void firstDeletion(int a,int b,int x,int y){
        if(thinningMatrix[x][y]==1 &&
            2<=b && b<=6 && a==1 &&
            thinningMatrix[x+1][y] * thinningMatrix[x][y+1] * thinningMatrix[x][y-1] == 0 &&
            thinningMatrix[x+1][y] * thinningMatrix[x-1][y] * thinningMatrix[x][y-1] == 0){
                points.add(new Point(x, y));
                sign = true;
        }
    }
    
    private void secondDeletion(int a,int b,int x,int y){
        if(thinningMatrix[x][y]==1 &&
            2<=b && b<=6 && a==1 &&
            thinningMatrix[x+1][y] * thinningMatrix[x][y+1] * thinningMatrix[x][y-1] == 0 &&
            thinningMatrix[x+1][y] * thinningMatrix[x-1][y] * thinningMatrix[x][y-1] == 0){
                points.add(new Point(x, y));
                sign = true;
        }
    }
    
    public void deleteOuterLine(){
        for(Point p : points){
                thinningMatrix[(int)p.getX()][(int)p.getY()] = 0;
            }
            points.clear();
    }
    
    /*public void writeImageToFile(){
        int pixel[][] = new int[imageFile.getWidth()][imageFile.getHeight()];
        for(int y=0;y<imageFile.getHeight();y++){
            for(int x=0;x<imageFile.getWidth();x++){
               pixel[x][y] = imageFile.getPixelMatrix(x, y);
            }
        }
        String string = "";
        String s = "";
        for(int y=0;y<imageFile.getHeight();y++){
            for(int x=0;x<imageFile.getWidth();x++){
                s = String.valueOf(thinningMatrix[x][y]);
                string += s;
            }
            string += "\n";
        }
        WriteToFile wtf = new WriteToFile(string);
    }*/
}
