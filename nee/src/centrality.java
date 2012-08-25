 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
  
public class centrality {
    
    private static Integer [][] shortDistMat; 
    private static int[][] AdjacencyMatrix;
    private static Vector<Vector<Integer>> AdjList;
    private static int n;    
    private static Double [] bC;//betweenness centrality
    
    public static void main(String[] args){
    	/*
    	String[] st = new String[105];
		//int[][] g=new int[105][105];
		int m=0;//##########################DEGREE OF POLARISATION
		File f=new File("pol.txt");
		
		try {int n=0;
			Scanner s=new Scanner(f);
			while(n<105)
			{
				String t=s.next();
				while(!t.equals("value")){ t=s.next();}	
				st[n++]=s.next().substring(1, 2);
			}
			 
			/*n=0;
			while(n++<=440)
			{
				s.next();s.next();s.next(); s.next();
				int a=Integer.parseInt(s.next());s.next();
				int b=Integer.parseInt(s.next());
				if(st[a].equals(st[b])){m++;}
				//g[a][b]=1;
				//g[b][a]=1;
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}*/
		int[][][] g=new int[60][105][105];
		for (int i=0;i<60;i++){
			
		
	File f=new File("r"+i+".txt");

	try {
	Scanner s=new Scanner(f);
	for(int n=1;n<=441;n++)
	{s.next();
	int b=Integer.parseInt(s.next());
	int c=Integer.parseInt(s.next());

	g[i][b][c]=1;
	g[i][c][b]=1;
	s.next();
	//System.out.println(b+" "+c);//System.out.println(c);
	}} catch (FileNotFoundException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	}}
		for(int i=0;i<60;i++){
			FindCentrality(g[i]);
			double[] o=new double[105];
			double maxbc=0.0,bc=0.0;
	    	for(int q=0;q<105;q++){if(o[q]>maxbc){maxbc=o[q];}bc=bc+o[q];}
	    	bc=maxbc-bc/104;
	    	System.out.println(bc);
		}
    	
    	double[] l=new double[105];
    	double lmax=0.0, lavg=0.0;
    	for(int q=0;q<105;q++){
    		l[q]=NCC(q); 
    		lmax=(lmax>l[q])?lmax:l[q];lavg=lavg+l[q];
    		//System.out.println(""+q+" "+l[q]);
    	}lavg=lavg/105;
    	
    	double[] o=new double[105];
    	double omax=0, oavg=0;
    	for(int q=0;q<105;q++){
    		o[q]=NBC(q); omax=(omax>o[q])?omax:o[q];oavg=oavg+o[q];//System.out.println(""+q+" "+o[q]);
    	}oavg=oavg/105;
    	
    	double[] p=new double[105];
    	double pmax=0, pavg=0;
    	for(int q=0;q<105;q++){
    	//	p[q]=NPC(q,g[i]);pmax=(pmax>p[q])?pmax:p[q];pavg=pavg+p[q];// System.out.println(""+q+" "+p[q]);
    	}pavg=pavg/105;
    	
    	//Degree Centralization=SIGMA(max_deg-deg)/(n-1)
    	double maxdeg=0.0,dc=0.0;
    	for(int i=0;i<105;i++){if(p[i]>maxdeg){maxdeg=p[i];}dc=dc+p[i];}
    	dc=maxdeg-dc/104;//0.163
    	//System.out.println(""+dc);
    	double maxbc=0.0,bc=0.0;
    	for(int i=0;i<105;i++){if(o[i]>maxbc){maxbc=o[i];}bc=bc+o[i];}
    	bc=maxbc-bc/104;//0.120
    	//System.out.println(""+bc);
    	double maxcc=0.0,cc=0.0;
    	for(int i=0;i<105;i++){if(l[i]>maxcc){maxcc=l[i];}cc=cc+l[i];}
    	cc=(maxcc-cc/104)*207/103;
    	//System.out.println(""+cc);//0.172
    	
    }
    
    public static void FindCentrality(int[][] a){
    n=a.length;
    int i=0;
    int j=0;
    int k=0;
    
    shortDistMat=new Integer[n][n];
    AdjacencyMatrix=a;
    AdjList=new Vector();
    
    for(i=0;i<n;i++){
        for(j=0;j<n;j++){
            shortDistMat[i][j]=0;
        }
        AdjList.add(new Vector());
    }
    											//Filling AdjList and Initialising shortDistMat
    for(i=0;i<n;i++){
        for(j=0;j<n;j++){
        if(AdjacencyMatrix[i][j]==1)
        {
        AdjList.elementAt(i).add(j);
        AdjList.elementAt(j).add(i);
        }
        }
    }
       
 
    bC=new Double[n];
    Double [] sigma=new Double[n];
    Integer [] d=new Integer[n];
    Double [] delta=new Double[n];
    for(i=0;i<n;i++){bC[i]=0.0; sigma[i]=0.0; d[i]=0;}
    
    Vector<Integer> S=new Vector();
   
    LinkedList<Integer> Q=new LinkedList();
    
    Vector<Vector<Integer>> P=new Vector();
    Vector<Integer> tempVector=new Vector();
    Iterator It;
    
    Integer v;
    Integer w;
  
    Vector<Integer> AdjVec=new Vector();
    Iterator At;
     
    for(Integer s=0;s<n;s++){//iteration not needed if whole graph is connected
      
        //initialization
        S.clear();
        P.clear();
        for(i=0;i<n;i++){sigma[i]=0.0; d[i]=-1; delta[i]=0.0; P.add(new Vector());}
        sigma[s]=1.0; d[s]=0;
        Q.clear();
        Q.add(s);
        
        //perform BFS
        while(!Q.isEmpty()){
            v=Q.remove();
            S.add(v);
            //for all neighbors of v
            AdjVec=AdjList.elementAt(v);
            At=AdjVec.iterator();
            while(At.hasNext()){
                w=(Integer)At.next();
                   //w found for the first time
                    if(d[w]<0){
                        d[w]=d[v]+1;
                        Q.add(w);
                        }
                 
                    //shortest path to w via v?
                    if(d[w]==d[v]+1){
                        sigma[w]=sigma[w]+sigma[v];//sigma[v]=# (shortest) paths from v to s
                        P.elementAt(w).add(v);//P(x)={parents of w in bfs tree for s}
           
                }
            }
        }
      
        for(i=0;i<n;i++){
             shortDistMat[s][i]=d[i];
             shortDistMat[i][s]=d[i];
        }
          								
       
        while(!S.isEmpty()){
            w=S.remove(S.size()-1); 
            tempVector=P.elementAt(w);
            It=tempVector.iterator();
           
            while(It.hasNext()){
                v=(Integer)It.next();
                delta[v]=delta[v]+ (sigma[v]/sigma[w])*(1+delta[w]);//Brandes' algorithm recursion 
            }
            if(w!=s){bC[w]=bC[w]+delta[w];}
        } 
     
    }
    
    //normalize the value of betweenness
    if(n>2){
    for(i=0;i<n;i++){
        bC[i]=bC[i].doubleValue()/((n-1)*(n-2));
    }
    } 
    else{
    for(i=0;i<n;i++){bC[i]=1.0;}
    }
    return;
    }
    
    public static Double NPC(int i, int[][] a){//normalised point/degree centrality for vertex i
    	double d=0.0;
         for(int j=0;j<n;j++){d=d+a[i][j];}
         if(n>1){
         return d/(n-1);
         }
         else return 0.0;
    }
    
    public static Double NCC(int m){//normalised closeness centrality for vertex i
        
        //find a sum of path from vertex to all other vertices
       
        Double sum=0.0;
        for(int i=0;i<m;i++){
            sum=sum+shortDistMat[m][i];
      
        }
        for(int i=m+1;i<n;i++){ 
            sum=sum+shortDistMat[m][i];
        }
        
        if((sum!=0)&&(n>1)){return (n-1)/sum;}
        else return 0.0;
    }
    
    public static Double NBC(int i){//normalized betweenness centrality for vertex i
        
        return bC[i];
    }

}
