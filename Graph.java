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
public class Graph implements Serializable{
    private ArrayList<Vertex> verticies;
    private ArrayList<Edge> edges;
    private Vertex rootVertex;
    
    public Graph(){
        verticies = new ArrayList<Vertex>();
        edges = new ArrayList<>();
    }
    
    public boolean isEmpty(){
        return verticies.size() == 0;
    }
    
    public boolean addVertex(Vertex v){
        boolean added = false;
        if(verticies.contains(v) == false){
            added = verticies.add(v);
        }
        return added;
    }
    
    public boolean addEdge(Vertex from,Vertex to,char direction) throws IllegalArgumentException{
        if(verticies.contains(from) == false)
            throw new IllegalArgumentException("from is not in graph");
        if(verticies.contains(to) == false)
            throw new IllegalArgumentException("to is not in graph");
        Edge e = new Edge(from, to, direction);
        if(from.findEdge(to) != null)
            return false;
        else{
            from.addEdge(e);
            to.addEdge(e);
            edges.add(e);
            return true;
        }
    }
    
    public ArrayList getVerticies(){
        return this.verticies;
    }
    
    public ArrayList getEdges(){
        return this.edges;
    }
    
    public Edge getEdge(int i){
        return edges.get(i);
    }
    
    public int getEdgeSize(){
        return edges.size();
    }
}
