#include<cstdio>
#include<algorithm>
#include <utility>   // pair: first second 
using namespace std;

#define FU(i,n) for(i=0;i<n;i++)
typedef pair<int,int> PII;   // template<class T1,class T2> struct pair
typedef long long LL;

const int MOD = 365*24*3600;
#define N 100000

PII x[N];
bool cmp(PII a,PII b){
	LL t1,t2;
	t1 = (LL)a.first * b.second;
	t2 = (LL)a.second * b.first;
	return t1 < t2;
}

int main(){
	int i,n;
	LL t;
	while(scanf("%d",&n),n){
		FU(i,n) scanf("%d%d",&x[i].first,&x[i].second);
		sort(x,x+n,cmp);
		t = 0;
		FU(i,n){
			t += t*x[i].second + x[i].first;
			t %= MOD;
		}	
		printf("%lld\n",t);	
	}   
    return 0;
}

