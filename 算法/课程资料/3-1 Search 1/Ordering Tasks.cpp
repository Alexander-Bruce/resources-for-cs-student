#include<cstdio>
#include<cstring>
const int maxn = 1000;
int n, m, G[maxn][maxn], c[maxn], topo[maxn], t;
// c[maxn] visited (1) or not (0) or in stack (-1)
// topo[maxn] an order
bool dfs(int u){
  c[u] = -1;
  for(int v = 0; v < n; v++) if(G[u][v]) {
    if(c[v]<0) return false; // a directed cycle; failure
    else if(!c[v]) dfs(v);
  }
  c[u] = 1; topo[--t]=u;
  return true;
}

bool toposort(){
  t = n;
  memset(c, 0, sizeof(c));
  for(int u = 0; u < n; u++) if(!c[u])
    if(!dfs(u)) return false; 
  return true;
}

int main() {
  while(scanf("%d%d", &n, &m) == 2 && n) {
    memset(G, 0, sizeof(G));
    for(int i = 0; i < m; i++) {
      int u, v;
      scanf("%d%d", &u, &v); u--; v--;
      G[u][v] = 1;
    }
    if(toposort()) {
      for(int i = 0; i < n-1; i++)
        printf("%d ", topo[i]+1);
      printf("%d\n", topo[n-1]+1);
    }
    else
      printf("No\n"); // assuming there is always a solution
  }
}
