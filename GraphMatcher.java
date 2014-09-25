/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.Edge;
import entity.Graph;
import entity.Vertex;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Herbita
 */
public class GraphMatcher {
    private DataBaseConnector dbConnector;
    private Graph[] graph;
    private ResultSet resultSet;
    
    public GraphMatcher(Graph[] graph){
        this.graph = graph;
        dbConnector = new DataBaseConnector();
        for(Graph g : graph){
            if(!g.isEmpty()){
                resultSet = dbConnector.selectFromDB();
                graphMatching(g);
            }
        }
    }
    
    public void graphMatching(Graph graph){
        try {
            while(resultSet.next()){
                Object object = toObject(resultSet.getBytes("graph"));
                String s = resultSet.getString("hangul_character");
                boolean mark = false;
                if(object instanceof Graph){
                    Graph dbGraph = (Graph) object;
                    ArrayList<Edge> edges = graph.getEdges();
                    ArrayList<Edge> dbEdges = dbGraph.getEdges();
                    if(edges.size()==dbEdges.size()){
                        for(int i=0;i<edges.size();i++){
                            mark = edges.get(i).getDirection()== dbEdges.get(i).getDirection();
                            if(mark){
                                //System.out.print(edges.get(i).getDirection()+" "+dbEdges.get(i).getDirection()+" ");
                                //System.out.println(mark);   
                            }
                            else if(!mark)
                                break;
                        }
                    }
                }
                if(mark){
                    System.out.print(s+" ");
                }
                //System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(GraphMatcher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Object toObject(byte[] bytes){
        Object object = null;
        try {
            object = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bytes))
                    .readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DataBaseConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }
}
