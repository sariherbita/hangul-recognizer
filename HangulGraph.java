/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.ArrayList;

/**
 *
 * @author Herbita
 */
public class HangulGraph {
    private final ArrayList<HangulVertex> vertexs;
    private final ArrayList<Arc> arcs;
    
    public HangulGraph(){
        vertexs = new ArrayList<>();
        arcs = new ArrayList<>();
    }
    
    public int checkVertexExistence(HangulVertex v){
        for(int i=0;i<vertexs.size();i++){
            if(vertexs.get(i) == v)
                return i;
        }
        return -1;
    }
    
    public void addSubGraph(HangulVertex v1,HangulVertex v2,Arc a){
        int existVertexIndex;
        char arcDirection = ' ';
        existVertexIndex = checkVertexExistence(v1);
        if(existVertexIndex == -1){
            v1.setLinkedArc(a);
            vertexs.add(v1);
            arcDirection = checkArcDirection(v1, v2, a.getLength());
            a.setLinkedVertex(v1, v2);
        }
        else if(existVertexIndex > -1){
            HangulVertex v = vertexs.get(existVertexIndex);
            v.setLinkedArc(a);
            vertexs.remove(existVertexIndex);
            vertexs.add(existVertexIndex, v);
            arcDirection = checkArcDirection(v1, v2, a.getLength());
            a.setLinkedVertex(v, v2);
        }
        a.setArcDirection(arcDirection);
        v2.setLinkedArc(a);
        vertexs.add(v2);
        arcs.add(a);
    }
    
    public void addSubGraph(HangulVertex v1,HangulVertex v2,Arc a[]){
        v1.setLinkedArc(a);
        vertexs.add(v1);
        v2.setLinkedArc(a);
        vertexs.add(v2);
    }
    
    public char checkArcDirection(HangulVertex v1, HangulVertex v2, int length){
        if(v2.getX()-v1.getX()==length && v2.getY()-v1.getY()==0){
            return '0';
        }
        else if(v2.getX()-v1.getX()==length && v2.getY()-v1.getY()==length){
            return '1';
        }
        else if(v2.getX()-v1.getX()==0 && v2.getY()-v1.getY()==length){
            return '2';
        }
        else if(v2.getX()-v1.getX()==-length && v2.getY()-v1.getY()==length){
            return '3';
        }
        else if(v2.getX()-v1.getX()==-length && v2.getY()-v1.getY()==0){
            return '4';
        }
        else if(v2.getX()-v1.getX()==-length && v2.getY()-v1.getY()==-length){
            return '5';
        }
        else if(v2.getX()-v1.getX()==0 && v2.getY()-v1.getY()==-length){
            return '6';
        }
        else if(v2.getX()-v1.getX()==length && v2.getY()-v1.getY()==-length){
            return '7';
        }
        return ' ';
    }
    
    public ArrayList getVertexs(){ return vertexs; }
    public ArrayList getArcs(){ return arcs; }
}
