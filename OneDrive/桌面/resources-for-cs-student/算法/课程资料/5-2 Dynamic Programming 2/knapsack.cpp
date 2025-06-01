#include<stdio.h>
#define MAXN 1000
#define MAXC 100000
int W[MAXN], V[MAXN]; 
int d[MAXN][MAXC];

int max(int v1,int v2){
	return v1>v2 ? v1 : v2;
}

// d(i,j) means the maximum value that can be attained with weight less than or equal to j 
// by considering i, i+1, ... n items
// O(n*C)
int main() {
  int n, C; 
  scanf("%d%d", &n, &C);
  for(int i = 0; i < n; i++)
    scanf("%d%d", &W[i], &V[i]);
  for(int i = n; i >= 1; i--)
    for(int j = 0; j <= C; j++) {
      d[i][j] = (i==n ? 0 : d[i+1][j]);
      if(j >= W[i]) d[i][j] = max(d[i][j],d[i+1][j-W[i]]+V[i]);
    }
  printf("%d\n", d[1][C]);
  return 0;
}
