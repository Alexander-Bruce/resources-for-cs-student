#include <cstring>
#include <utility>
#include <queue>
#include <cstdio>
using namespace std;
 
#define MP make_pair
#define FU(i,n) for(i=0;i<n;i++)

typedef pair<int,int> PII;

#define N 30

int best;  // number of fish expected
int ft[N]; // number of 5-miuntes spent at each lake

void fish(int *f,int *d,int k,int h){
	int i,s=0,t[N]={0}; // s and t[N] are used for updating best and ft[N] respectively	
	priority_queue<PII > a;   // compare "first"; if equal, then compare "second"
	// compare f[i]; if equal, then compare k-i (not i). Using k-i is for the best solution.
	FU(i,k) a.push(MP(f[i],k-i));
	while(h--){
		PII b = a.top();
		// If multiple plans exist, choose the one that spends as long as possible at lake 1, 
		// even if no fish are expected to be caught in some intervals.		
		if(b.first == 0){  // no fish, at lake 1
			t[0]+=h+1;
			break;
		}
		a.pop();
		t[k-b.second]++;
		s += b.first;
		b.first = max(b.first-d[k-b.second],0);
		a.push(b);		
	}
	if(s>best){
		best = s;
		FU(i,k) ft[i]=t[i];
	}
}

int main(){
	int i,j,k,n,h,t[N],f[N],d[N];	

	while(scanf("%d",&n),n){
		scanf("%d",&h);
		h*=12;		
		FU(i,n) scanf("%d",f+i);
		FU(i,n) scanf("%d",d+i);
		t[0] = 0;
		FU(i,n-1) scanf("%d",t+i+1);
		FU(i,n-1) t[i+1] += t[i];  // sum_{i=1}^{n-1}(ti)
		memset(ft,0,sizeof(ft));
		best = -1;
		FU(i,n) if(h>t[i]) fish(f,d,i+1,h-t[i]); // enumerate
		
		FU(i,n){
			if(i!=0) printf(", "); 
			printf("%d",ft[i]*5);			
		}
		printf("\nNumber of fish expected: %d\n\n",best);
	}	
	
	return 0;
}