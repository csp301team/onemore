 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

//import org.apache.*;
public class centrality1 extends TriadColor {
    
    private static Integer [][] shortDistMat; 
    private static int[][] AdjacencyMatrix;
    private static Vector<Vector<Integer>> AdjList;
    private static int n;    
    private static Double [] bC;//betweenness centrality
    private static int m;
    private static int ca;
    
    private static TreeSet<Integer> P1 = new TreeSet<Integer>();
    private static TreeSet<Integer> R = new TreeSet<Integer>();
    private static TreeSet<Integer> X = new TreeSet<Integer>();
    private static LinkedList<TreeSet<Integer>> N = new LinkedList<TreeSet<Integer>>();
    
    public static void main(String[] args){
    	
    	String[] st = new String[1490];
		int[][] g=new int[1490][1490];
		int m=0;
		File f=new File("out.txt");
		
		try {int n=0;
			Scanner s=new Scanner(f);
			while(n<1490)
			{
				String t=s.next();
				while(!t.equals("value")){ t=s.next();}	
				st[n++]=s.next();
			}
			 n=0;
			s.next();s.next();
			while(n++<=19025)
			{
				s.next();s.next();s.next(); s.next();
				int a=Integer.parseInt(s.next())-1;s.next();
				int b=Integer.parseInt(s.next())-1;
				if(st[a].equals(st[b])){m++;}
				g[a][b]=1;
				g[b][a]=1;
				
			}
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		
    	FindCentrality(g);
    	/*
    	double[] l=new double[1490];
    	for(int q=0;q<1490;q++){
    		l[q]=NCC(q); System.out.println(""+q+" "+(l[q]));
    	}
    	*/
    	/*
    	double[] o=new double[1490];
    	for(int q=0;q<1490;q++){
    		o[q]=NBC(q);// System.out.println(""+q+" "+o[q]);
    	}
    	*/
    	/*
    	double[] p=new double[1490];
    	
    	for(int q=0;q<1490;q++){
    		p[q]=NPC(q,g);// System.out.println(""+q+" "+(p[q]));
    	}
    	*/
    	//Degree Centralization=SIGMA(max_deg-deg)/(n-1)
    	/*
    	double maxdeg=0.0,dc=0.0;
    	for(int i=0;i<1490;i++){if(p[i]>maxdeg){maxdeg=p[i];}dc=dc+p[i];}
    	dc=maxdeg-dc/104;//0.163
    	System.out.println(""+dc);
    	*/
    	/*
    	double maxbc=0.0,bc=0.0;
    	for(int i=0;i<1490;i++){if(o[i]>maxbc){maxbc=o[i];}bc=bc+o[i];}
    	bc=maxbc-bc/104;//0.120
    	System.out.println(""+bc);
    	
    	double maxcc=0.0,cc=0.0;
    	for(int i=0;i<1490;i++){if(l[i]>maxcc){maxcc=l[i];}cc=cc+l[i];}
    	cc=(maxcc-cc/104)*207/103;
    	System.out.println(""+cc);//0.172
    	*/
    	//Betweenness sorted desc order - use as clique pivots
    	/*
    	int[] x=new int[45];//nearly top 3%, here >10 triads of same color
    	
    	int[] z=TKT(g,st);
    	int [] z1=TKT(g,st);Arrays.sort(z1);
    	
    	for(int i=0;i<45;i++)
    	{	for(int j=0;j<1490;j++){
    		if(z1[1489-i]==z[j]){x[i]=j;z[j]=0;
    		System.out.println(""+i+" "+x[i]+" "+z1[104-i]);
    		break;
    		}
    	}
    	}
    	*/
    	/*betweenness centrality sorting
    	Arrays.sort(bC);
    	for(int i=0;i<42;i++)
    	{	for(int j=0;j<1490;j++){
    		if(bC[104-i].doubleValue()==o[j]){x[i]=j;//System.out.println(""+i+" "+x[i]);
    		}
    	}
    	}*/
    	//for(int i=0;i<80;i++){System.out.println(TKT(g,st)[x[i]]);}
    	for(int ee=0;ee<=100;ee++){click(R,P1,X,ee);}
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
    
    //add all vertices in P1 to initialise it for clicking
    for(i=0;i<1490;i++){P1.add((Integer)i);}
       
  //We now make linked-lists of sets of neighbours of vertices
    
    for(i=0;i<n;i++){TreeSet<Integer> T1 = new TreeSet<Integer>();N.add(i, T1);}
    
    for(i=0;i<n;i++){
    	int i1=AdjList.elementAt(i).size()/2;
        for(j=0;j<i1;j++){
        N.get(i).add(AdjList.elementAt(i).elementAt(j));
        }
       // System.out.println(""+N.get(i));
    }
    m=0;
    ca=0;
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
     /*
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
    */
    //normalize the value of betweenness
    /*
    if(n>2){
    for(i=0;i<n;i++){
        bC[i]=bC[i].doubleValue()/((n-1)*(n-2));
    }
    } 
    else{
    for(i=0;i<n;i++){bC[i]=1.0;}
    }
    return;
    */
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

    public static <T> Set<T> union(Set<T> setA, Set<T> setB) {//return union set of two input sets A and B
        Set<T> tmp = new TreeSet<T>(setA);
        tmp.addAll(setB);
        return tmp;
      }

      public static <T> Set<T> intersection(Set<T> setA, Set<T> setB) {//return intersection set of two input sets A and B
        Set<T> tmp = new TreeSet<T>();
        for (T x : setA)
          if (setB.contains(x))
            tmp.add(x);
        return tmp;
      }

      public static <T> Set<T> difference(Set<T> setA, Set<T> setB) {//return difference set A\B of two input sets A and B
        Set<T> tmp = new TreeSet<T>(setA);
        tmp.removeAll(setB);
        return tmp;
      }
      public static void click(Set<Integer> A,Set<Integer> B, Set<Integer> C, int ee){//gets maximal clique by bron-kerbosch
    	  //A=currently growing clique
    	  //B=prospective nodes connected to all nodes in A, to be used for expanding A
    	  //C=processed nodes i.e. all maximal cliques through them have been reported
    	  //INVARIANT-all nodes connected to every node of A are either in B or C
    	  if(B.isEmpty()&&C.isEmpty()){
    		  if(A.size()>=8/*&&!A.contains((Integer)8)&&!A.contains((Integer)3)*/)	{
    			//  ca=A.size();
    		  System.out.println(""+A);
    		  		}
    		  }
    	  else{
    		  //int i=B.size();//System.out.println(""+B);
    		  
    		  for(int bu=0;bu<B.size();bu++){//System.out.println(""+bu+" ");//System.out.println(""+A);
    			  TreeSet<Integer> set1 = new TreeSet<Integer>();
    			  Integer u;
    			  if(m==0){u=(Integer)855;}else {u=(Integer)B.toArray()[(bu+ee)%(B.size())];}
    			  m=1;
    			  set1.add(u);
    			  B=difference(B,set1);
    			  A=union(A,set1);
    			  B=intersection(B,N.get(u));
    			  C=intersection(C,N.get(u));
    			  //System.out.println("**"+A);System.out.println("**"+B);System.out.println("**"+C);System.out.println("**"+set1);
    			  click(A,B,C,ee);
    			  C=union(C,set1);
    			  //for(i=0;i<1490;i++){B.add((Integer)i);}
    			  //u=(Integer)B.toArray()[bu];set1.add(u);
    			  //B=difference(B,set1);
    		  }
    	  }
      }

}


