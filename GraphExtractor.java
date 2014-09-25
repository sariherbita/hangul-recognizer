/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import entity.Arc;
import entity.Edge;
import entity.Graph;
import entity.HangulGraph;
import entity.HangulImage;
import entity.HangulVertex;
import entity.Vertex;
import java.awt.Point;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 *
 * @author Herbita
 */
public final class GraphExtractor {
    private int pixelMatrix[][];
    private final HangulGraph hangulGraph[];
    private HangulGraph normalizedHangulGraph[];
    private final ArrayList<Point> points;
    private ArrayList<HangulVertex> vertexList;
    private ArrayList<Arc> arcList;
    private ArrayList<Arc> fixedArcList;
    private final HangulImage imageFile;
    private Graph graph[];
    private int height=0,width=0;
    private int neighbour;
    private final int countVertex=0;
    char vId = 'A';
    int arcLength=0;
    char aId = '1';
    
    public GraphExtractor(HangulImage imageFile){
        this.imageFile = imageFile;
        hangulGraph = new HangulGraph[5];
        graph = new Graph[5];
        for(int i=0;i<hangulGraph.length;i++){
            hangulGraph[i] = new HangulGraph();
            graph[i] = new Graph();
        }
        points = new ArrayList<>();
        copyPixelfromImageFile();
        extractGraph();
        graphNormalization();
    }
    
    public void copyPixelfromImageFile(){
        int imageMatrix[][];
        imageMatrix = imageFile.getPixelMatrix();
        pixelMatrix = imageMatrix;
        height = imageFile.getHeight();
        width = imageFile.getWidth();
    }
    
    public int countNeighbour(int x,int y){
        int count = 0;
        if(pixelMatrix[x][y-1]==0) count++;
        if(pixelMatrix[x+1][y-1]==0) count++;
        if(pixelMatrix[x+1][y]==0) count++;
        if(pixelMatrix[x+1][y+1]==0) count++;
        if(pixelMatrix[x][y+1]==0) count++;
        if(pixelMatrix[x-1][y+1]==0) count++;
        if(pixelMatrix[x-1][y]==0) count++;
        if(pixelMatrix[x-1][y-1]==0) count++;
        return count;
    }
    
    public char[] checkNeighbourPosition(int x,int y,int count){
        char d[] = new char[count];
        int i = 0;
        if(pixelMatrix[x+1][y]==0) d[i++] = '0';
        else if(pixelMatrix[x+1][y+1]==0) d[i++] = '1';
        else if(pixelMatrix[x][y+1]==0) d[i++] = '2';
        else if(pixelMatrix[x-1][y+1]==0) d[i++] = '3';
        else if(pixelMatrix[x-1][y]==0) d[i++] = '4';
        else if(pixelMatrix[x-1][y-1]==0) d[i++] = '5';
        else if(pixelMatrix[x][y-1]==0) d[i++] = '6';
        else if(pixelMatrix[x+1][y-1]==0) d[i++] = '7';
        return d;
    }
    
    public int[] changeCurrentPosition(int x,int y,char c){
        switch (c){
            case '0' : x=x+1; break;
            case '1' : x=x+1; y=y+1; break;
            case '2' : y=y+1; break;
            case '3' : x=x-1; y=y+1; break;
            case '4' : x=x-1; break;
            case '5' : x=x-1; y=y-1; break;
            case '6' : y=y-1; break;
            case '7' : x=x+1; y=y-1; break;
        }
        int coord[] = {x,y}; 
        return coord;
    }
    
    public char checkNeighbourExistence(int x,int y){
        if(pixelMatrix[x+1][y]==0) return '0';
        else if(pixelMatrix[x+1][y+1]==0) return '1';
        else if(pixelMatrix[x][y+1]==0) return '2';
        else if(pixelMatrix[x-1][y+1]==0) return '3';
        else if(pixelMatrix[x-1][y]==0) return '4';
        else if(pixelMatrix[x-1][y-1]==0) return '5';
        else if(pixelMatrix[x][y-1]==0) return '6';
        else if(pixelMatrix[x+1][y-1]==0) return '7';
        return ' ';
    }
    
    public void createVertex(int x,int y,char d[]){
        if(vId=='A'){
            vertexList.add(new HangulVertex(vId++, d.length, x, y));
        }
        else{
            vertexList.add(new HangulVertex(vId++, d.length+1, x, y));
        }
        d = checkNeighbourPosition(x, y, d.length);
        for(int i=0;i<d.length;i++){
            vertexList.get(vertexList.size()-1).setNeighbourDirection(i, d[i]);
        }
        pixelMatrix[x][y] = 255;
    }
    
    public void createArc(int length){
        arcList.add(new Arc(aId++, length));
    }
    
    public HangulVertex findLinkedVertex(HangulVertex v, int length){
        HangulVertex foundVertex = new HangulVertex();
        HangulVertex vertexCandidate;
        int x = v.getX();
        int y = v.getY();
        for (HangulVertex vertexList1 : vertexList) {
            vertexCandidate = vertexList1;
            if(vertexCandidate.getX()==x+length && vertexCandidate.getY()==y){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x+length && vertexCandidate.getY()
                    ==y+length){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x && vertexCandidate.getY()==y+length){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x-length && vertexCandidate.getY()==
                    y+length){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x-length && vertexCandidate.getY()==y){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x-length && vertexCandidate.getY()==
                    y-length){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x && vertexCandidate.getY()==y-length){
                foundVertex = vertexCandidate;
            }
            else if(vertexCandidate.getX()==x+length && vertexCandidate.getY()==
                    y-length){
                foundVertex = vertexCandidate;
            }
        }
        return foundVertex;
    }
    
    public void createHangulGraph(int i){
        HangulVertex v2 = vertexList.get(vertexList.size()-1);
        Arc a = arcList.get(arcList.size()-1);
        HangulVertex v1 = findLinkedVertex(v2, a.getLength());
        hangulGraph[i].addSubGraph(v1, v2, a);
    }
    
    public void extractGraph(){
        vertexList = new ArrayList<>();
        arcList = new ArrayList<>();
        int p[];
        int x = 0;
        int y = 0;
        int length = 0;
        int count;
        int fixedlength = 0;
        char d[];
        char direction;
        while(fixedlength<5){
            for(int j=height-1;j>-1;j--){
                for(int i=width-1;i>-1;i--){
                    if(pixelMatrix[i][j]==0){
                        x = i;
                        y = j;
                    }
                }
            }
            count = countNeighbour(x, y);
            d = checkNeighbourPosition(x, y, count);
            createVertex(x, y, d);
            direction = vertexList.get(vertexList.size()-1).getArcDirection(0);
            pixelMatrix[x][y] = 255;
            p = changeCurrentPosition(x, y, direction);
            x = p[0];
            y = p[1];
            do{
                count = countNeighbour(x, y);
                d = checkNeighbourPosition(x, y, count);
                if(count>1 ){
                    createVertex(x, y, d);
                    createArc(length+1);
                    createHangulGraph(fixedlength);
                    length = 0;
                }
                else if(count==1){
                    if(direction==d[0]){
                        length++;
                    }
                    else if(direction!=d[0]  ) {
                        createVertex(x, y, d);
                        createArc(length+1);
                        createHangulGraph(fixedlength);
                        length = 0;
                    }
                }
                else if(count==0 ){
                    createVertex(x, y, d);
                    createArc(length+1);
                    createHangulGraph(fixedlength);
                    length = 0;
                }
                pixelMatrix[x][y] = 255;
                if(count!=0){
                    direction = d[0];
                    p = changeCurrentPosition(x, y, d[0]);
                    x = p[0];
                    y = p[1];
                }
                else if(count==0){
                    for (HangulVertex vertexList1 : vertexList) {
                        if (vertexList1.getLinkedArcsCount() > 1) {
                            HangulVertex v = vertexList1;
                            int neighbourCount = v.getLinkedArcsCount();
                            int tempX = v.getX();
                            int tempY = v.getY();
                            char existance = checkNeighbourExistence(tempX, tempY);
                            if(existance!=' '){
                                p = changeCurrentPosition(tempX, tempY, existance);
                                x = p[0];
                                y = p[1];
                                count = neighbourCount;
                            }
                        }
                    }
                }
            }while(count>0);
            fixedlength++;
        }
    }
    
    public void graphNormalization(){
        ArrayList<Arc> tempArcList = new ArrayList<>();
        int itr = 0;
        for (HangulGraph hg : hangulGraph) {
            arcList = hg.getArcs();
            for (Arc a : arcList) {
                if(a.getLength() > 8){
                    tempArcList.add(a);
                }
            }
            ArrayList<Point> allPoint = new ArrayList<>();
            for(Arc a : tempArcList){
                Point p = new Point(a.getV1().getX(), a.getV1().getY());
                allPoint.add(p);
                p = new Point(a.getV2().getX(), a.getV2().getY());
                allPoint.add(p);
            }
            ArrayList<Point> points = new ArrayList<>();
            boolean mark = false;
            int i,j=0;
            for(i=0;i<allPoint.size();i++){
                if(allPoint.get(i)==allPoint.get(0)){
                    mark = true;
                }
                else if(allPoint.get(i)==allPoint.get(allPoint.size()-1)){
                    points.add(allPoint.get(i));
                }
                else{
                    for(j=i+1;j<allPoint.size();j++){
                        int xDiff = Math.abs(allPoint.get(i).x - allPoint.get(j).x);
                        int yDiff = Math.abs(allPoint.get(i).y - allPoint.get(j).y);
                        if(xDiff<20 && yDiff<20){
                            mark = true;
                            break;
                        }
                        else {
                            mark = false;
                            break;
                        }
                    }
                }
                if(mark){
                    points.add(allPoint.get(i));
                    if(i!=0)
                    {
                        i=j;
                    }
                    mark = false;
                }
            }
            /*for(Point p : points){
                System.out.println(p.x+" "+p.y);
            }
            System.out.println();
            for(Arc a : tempArcList){
                System.out.print(a.getV1Id()+"-"+a.getV2Id()+"\t");
                System.out.print(a.getV1().getX()+","+a.getV1().getY()+" - "+a.getV2().getX()+","+a.getV2().getY());
                System.out.println();
            }
            System.out.println();*/
            ArrayList<HangulVertex> hvList = new ArrayList<>();
            for(Arc a : tempArcList){
                hvList.add(a.getV1());
                hvList.add(a.getV2());
            }
            for(int k=0;k<hvList.size();k++){
                if(k%2 == 0){
                    if(!points.contains(new Point(hvList.get(k).getX(),hvList.get(k).getY()))){
                        hvList.get(k).setID(hvList.get(k-1).getVertexId());
                    } 
                }
            }
            if(tempArcList.size()==1){
                Arc a = tempArcList.get(tempArcList.size()-1);
                Vertex v1 = new Vertex(a.getV1Id());
                Vertex v2 = new Vertex(a.getV2Id());
                graph[itr].addVertex(v1);
                graph[itr].addVertex(v2);
                graph[itr].addEdge(v1, v2, a.getDirection());
            }
            else{
                for(Arc a : tempArcList){
                    Vertex v1 = new Vertex(a.getV1Id());
                    Vertex v2 = new Vertex(a.getV2Id());
                    graph[itr].addVertex(v1);
                    graph[itr].addVertex(v2);
                    graph[itr].addEdge(v1, v2, a.getDirection());
                }
            }
            itr++;
            tempArcList.clear();
            allPoint.clear();
            points.clear();
        }
        /*for(Graph g : graph){
            ArrayList<Edge> edge = g.getEdges();
            for(Edge e : edge){
                Vertex from = e.getFrom();
                Vertex to = e.getTo();
                System.out.print(e.getDirection()+" "+from.getName()+" "+to.getName());
                System.out.println();
            }
            System.out.println();
        }*/
    }
    
    public Graph[] getGraph(){
        return graph;
    }
}
