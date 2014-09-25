/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author ASUS A450LCI5 64
 */
public class Vertex implements Serializable{
    private ArrayList<Edge> incomingEdges;
    private ArrayList<Edge> outgoingEdges;
    private char name;
    private boolean mark;
    private int markState;
    
    public Vertex(char name){
        incomingEdges = new ArrayList<>();
        outgoingEdges = new ArrayList<>();
        this.name = name;
        mark = false;
    }
    
    public boolean addEdge(Edge e){
        if(e.getFrom() == this){
            outgoingEdges.add(e);
        }
        else if(e.getTo() == this){
            incomingEdges.add(e);
        }
        else
            return false;
        return true;
    } 
    
    public void addOutgoingEdge(Vertex to,char direction){
        Edge out = new Edge(this, to, direction);
        outgoingEdges.add(out);
    }
    
    public void addIncomingEdge(Vertex from,char direction){
        Edge in = new Edge(this, from, direction);
        incomingEdges.add(in);
    }
    
    public boolean hasEdge(Edge e){
        if(e.getFrom() == this)
            return incomingEdges.contains(e);
        else if(e.getTo() == this)
            return outgoingEdges.contains(e);
        else
            return false;
    }
    
    public void setName(char name){
        this.name = name;
    }
    
    public char getName(){
        return name;
    }
    
    public int getIncomingEdgeCount(){
        return incomingEdges.size();
    }
    
    public Edge findEdge(Vertex v){
        for(Edge e : outgoingEdges){
            if(e.getTo() == v)
                return e;
        }
        return null;
    }
    
    public Edge findEdge(Edge e){
        if(outgoingEdges.contains(e))
            return e;
        else
            return null;
    }
}
