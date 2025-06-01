#include<cstdio>
#include<algorithm>
using namespace std;

int P[100], A[100];

void print_permutation(int n, int* P, int* A, int cur) {
  if(cur == n) {
    for(int i = 0; i < n; i++) printf("%d ", A[i]);
    printf("\n");
  } else for(int i = 0; i < n; i++) if(!i || P[i] != P[i-1]) {
//with P sorted, the first element or the element which is different from its last element
//c1 is the times of P[i] from A[0] to A[cur-1], c2 is the times of P[i] in P
//When c1<c2, recursion
    int c1 = 0, c2 = 0;
    for(int j = 0; j < cur; j++) if(A[j] == P[i]) c1++;
    for(int j = 0; j < n; j++) if(P[i] == P[j]) c2++;
    if(c1 < c2) {
      A[cur] = P[i];
      print_permutation(n, P, A, cur+1);
    }
  }
}

int main() {
  int i, n;
  scanf("%d", &n);
  for(i = 0; i < n; i++)
    scanf("%d", &P[i]);
  sort(P, P+n);
  print_permutation(n, P, A, 0);
  return 0;
}
