// brute force 
#include<cstdio>
#include<algorithm>
#include<Windows.h>
using namespace std;

int is_prime(int x) {
  for(int i = 2; i*i <= x; i++)
    if(x % i == 0) return 0;
  return 1;
}

int main() {
  int n, A[50], isp[50];
  int kase = 0;
  while(scanf("%d", &n) == 1 && n > 0) {
    if(kase > 0) printf("\n");
    printf("Case %d:\n", ++kase);
    
    DWORD start = GetTickCount();
    
    for(int i=2;i<=n*2;i++) isp[i] = is_prime(i);  // generating the prime array
    for(int i=0;i<n;i++) A[i] = i+1; // the first permutation
    do{
    	int ok = 1;
    	for(int i=0;i<n;i++) if(!isp[A[i]+A[(i+1)%n]]){
    		ok = 0;
    		break;
		}
		if(ok){
			for(int i=0;i<n;i++) printf("%d ", A[i]);  // output
			printf("\n");
		}
	}while(next_permutation(A+1,A+n));
	
	DWORD end = GetTickCount();
  	printf("%ums\n",end-start);
  }
  return 0;
}
