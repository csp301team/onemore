import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class CC {
	public static void main(String [] args)
	{
		String[] st = new String[105];
		int[][] g=new int[105][105];
		//int m=0;
		File f=new File("pol.txt");
		
		try {int n=0;
			Scanner s=new Scanner(f);
			while(n<105)
			{
				String t=s.next();
				while(!t.equals("value")){ t=s.next();}	
				st[n++]=s.next().substring(1, 2);
						
			}
			 n=0;
			while(n++<=440)
			{
				s.next();s.next();s.next(); s.next();
				int a=Integer.parseInt(s.next());s.next();
				int b=Integer.parseInt(s.next());
				//if(st[a].equals(st[b])){m++;}
				g[a][b]=1;
				g[b][a]=1;
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(CCC(g));
	}
static double CCC(int[][] a){
	double cc1=0.0;
	int n=a.length;
	for(int i=0;i<n;i++)
	{	int [] b=new int[30];//For polbooks max degree<30
		int k=0; for(int j=0;j<105 ;j++){if(a[i][j]==1){b[k++]=j;}}//k=degree of i
		int c=0;
		
		for(int j=0;j<k;j++){for(int m=j+1;m<k;m++){c=c+a[b[j]][b[m]];}}
	//	{System.out.print("\n"+c);}
		double d=(2.0*c/k/(k-1.0));//{System.out.print("\n"+i+" "+d);}
		cc1=cc1+(d);
	}
	return cc1/n;
}
}
