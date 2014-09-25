/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;

/**
 *
 * @author ASUS A450LCI5 64
 */
public class Edge implements Serializable{
    private Vertex from;
    private Vertex to;
    private char direction;
    private boolean mark;
    
    public Edge(Vertex from,Vertex to){
        this(from,to,' ');
    }
    
    public Edge(Vertex from,Vertex to,char direction){
        this.from = from;
        this.to = to;
        this.direction = direction;
        mark = false;
    }
    
    public Vertex getTo(){
        return to;
    }
    
    public Vertex getFrom(){
        return from;
    }
    
    public char getDirection(){
        return direction;
    }
}
