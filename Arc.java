/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Herbita
 */
public class Arc {
    private char arcId = ' ';
    private char direction = ' ';
    private int length = 0;
    private HangulVertex v1;
    private HangulVertex v2;
    
    public Arc(){
        direction = ' ';
        length = 0;
    }
    
    public Arc(char id, char direction,int length){
        arcId = id;
        this.direction = direction;
        this.length = length;
    }
    
    public Arc(char id, int length){
        arcId = id;
        this.length = length;
    }
    
    public void setArcDirection(char d){
        direction = d;
    }
    
    public void setLength(int length){
        this.length = length;
    }
    
    public void setLinkedVertex(HangulVertex v1,HangulVertex v2){
        this.v1 = v1;
        this.v2 = v2;
    }
    
    public char getArcId(){ return arcId;}
    public char getDirection(){ return direction;}
    public int getLength(){ return length;}
    public char getV1Id(){ return v1.getVertexId(); }
    public char getV2Id(){ return v2.getVertexId(); }
    public int getVX(HangulVertex v){
        return v.getX();
    }
    
    public int getVY(HangulVertex v){
        return v.getY();
    }
    
    public HangulVertex getV1(){
        return v1;
    }
    
    public HangulVertex getV2(){
        return v2;
    }
}
