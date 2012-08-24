import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TriadColor {
	public static void main(String [] args)
	{
		String[] st = new String[105];
		int[][] g=new int[105][105];
		int m=0;
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
				if(st[a].equals(st[b])){m++;}
				g[a][b]=1;
				g[b][a]=1;
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for(int i=0;i<105;i++){System.out.println(TDT(g,st)[i]);}
	}
static int[] TKT(int[][] a, String[] s){//Three-of-a-Kind Triads
	int[] c=new int[105];
	int n=a.length;
	for(int i=0;i<105;i++)
	{	int [] b=new int[30];//For polbooks max degree<30
		int k=0; for(int j=0;j<105 ;j++){if(a[i][j]==1){b[k++]=j;}}//k=degree of i
		int p=0;
		for(int j=0;j<k;j++){for(int m=j+1;m<k;m++){
			if(a[b[j]][b[m]]==1){if(s[i].equals(s[b[j]])&&s[i].equals(s[b[m]])){p++;
			}}}}
		c[i]=p;
	}
	return c;
}

static int[] TOT(int[][] a, String[] s){//Two-One Triads
	int[] c=new int[105];
	int n=a.length;
	for(int i=0;i<105;i++)
	{	int [] b=new int[30];//For polbooks max degree<30
		int k=0; for(int j=0;j<105 ;j++){if(a[i][j]==1){b[k++]=j;}}//k=degree of i
		int p=0;
		for(int j=0;j<k;j++){for(int m=j+1;m<k;m++){
			if(a[b[j]][b[m]]==1){
				int y=0;
				y=s[i].equals(s[b[j]])?y+1:y;
				y=s[i].equals(s[b[m]])?y+1:y;
				y=s[b[m]].equals(s[b[j]])?y+1:y;
				if(y==1)
				{p++;
			}}}}
		c[i]=p;
	}
	return c;
}
static int[] TDT(int[][] a, String[] s){//Three Distinct Triads
	int[] c=new int[105];
	int n=a.length;
	for(int i=0;i<1;i++)
	{	int [] b=new int[30];//For polbooks max degree<30
		int k=0; for(int j=0;j<105 ;j++){if(a[i][j]==1){b[k++]=j;}}//k=degree of i
		int p=0;
		for(int j=0;j<k;j++){for(int m=j+1;m<k;m++){
			if(a[b[j]][b[m]]==1){
				int y=0;
				y=s[i].equals(s[b[j]])?y+1:y;
				y=s[i].equals(s[b[m]])?y+1:y;
				y=s[b[m]].equals(s[b[j]])?y+1:y;
				if(y==0)
				{p++;
			}}}}
		c[i]=p;
	}
	return c;
}
}
