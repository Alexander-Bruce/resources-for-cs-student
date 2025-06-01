// brute force, n^n
#include<cstdio>
using namespace std;

int C[50], tot = 0, n = 8, nc = 0, nn=0;

void search(int cur) {
  int i, j;
  nc++;
  if(cur == n) {
  	nn++;
    for(i = 0; i < n; i++)
      for(j = i+1; j < n; j++)
        if(C[i] == C[j] || i-C[i] == j-C[j] || i+C[i] == j+C[j]) return; // column or diagonal or subdiagonal
    tot++;   // recursive boundary; a solution
  } else for(i = 0; i < n; i++) {
    C[cur] = i;   // cur means placing queens row by row
    search(cur+1);
  }
}

int main() {
  scanf("%d", &n);
  search(0);
  printf("%d\n", tot);
  printf("%d\n", nc);
  printf("%d\n", nn);
  return 0;
}
