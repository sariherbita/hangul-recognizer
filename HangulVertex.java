/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Herbita
 */
public class HangulVertex {
    private char vertexId;
    private int x;
    private int y;
    private int arcCount;
    private ArrayList<Arc> linkedArcs;
    private Arc linkedArc;
    private char neighbourDirection[];
    
    public HangulVertex(){
        
    }
    
    public HangulVertex(char c,int count,int x,int y){
        vertexId = c;
        arcCount = count;
        this.x = x;
        this.y = y;
        neighbourDirection = new char[count];
        linkedArcs = new ArrayList<>();
        linkedArc = new Arc();
    }
    
    /*public void setArcDirection(int i, char d){
        linkedArcs[i].setArcDirection(d);
    }*/
    
    public void setNeighbourDirection(int i,char d){
        neighbourDirection[i] = d;
    }
    
    public void setLinkedArc( Arc a){
        linkedArcs.add(a);
    }
    
    public void setLinkedArc(Arc a[]){
        int l = a.length;
        System.arraycopy(a, 0, linkedArc, 0, l);
    }
    
    public void setID(char c){
        this.vertexId = c;
    }
            
    public String getLinkedArcsID(){
        String s = "";
        for(int i=0;i<linkedArcs.size();i++){
            s += linkedArcs.get(i).getArcId();
        }
        return s;
    }
    
    public char getArcDirection(int i){
        return neighbourDirection[i];
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    public char getVertexId(){
        return vertexId;
    }
    
    public char getID(int x,int y){
        if(x==this.x && y==this.y)
            return this.vertexId;
        return ' ';
    }
    
    public int getLinkedArcsCount(){
        return neighbourDirection.length;
    }
}
