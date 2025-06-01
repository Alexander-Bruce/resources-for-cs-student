#include<stdio.h>
#include<string.h>
#define MAXN 1010
int n, G[MAXN][MAXN]; // DAG
int x[MAXN], y[MAXN], d[MAXN]; // d[i] means the length of the longest path starting from the vertex i

int max(int a, int b){
	return a>b ? a : b;
}

// Recursion implies order of computation
int dp(int i) {
  int& ans = d[i];  // to simplify the code
  if(ans > 0) return ans;
  ans = 1;
  for(int j = 1; j <= n; j++) if(G[i][j]) ans = max(ans, dp(j)+1);
  return ans;
}

void print_ans(int i) {
  printf("%d ", i);
  for(int j = 1; j <= n; j++) if(G[i][j] && d[i] == d[j]+1) {
    print_ans(j);
    break;
  }
}

int main() {
  int i, j, ans, best;
  scanf("%d", &n);
  for(i = 1; i <= n; i++) {
    scanf("%d%d", &x[i], &y[i]);
    if(x[i] > y[i]) {          // to make sure x<y
      int t = x[i]; x[i] = y[i]; y[i] = t;
    }
  }
  memset(G, 0, sizeof(G));
  for(i = 1; i <= n; i++)
    for(j = 1; j <= n; j++)
      if(x[i] < x[j] && y[i] < y[j]) G[i][j] = 1;  // generate the DAG

  ans = 0;
  for(i = 1; i <= n; i++)
    if(dp(i) > ans) {
      best = i;
      ans = dp(i);
    }
  printf("%d\n", ans);
  print_ans(best);
  printf("\n");
  return 0;
}
