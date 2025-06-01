#include<cstdio>
using namespace std;

int A[101];  // n<100

void print_permutation(int n, int* A, int cur) {
  if(cur == n) { // terminate recursion
    for(int i = 0; i < n; i++) printf("%d ", A[i]);
    printf("\n");
  } else for(int i = 1; i <= n; i++) { // try to assign A[cur] with i
    int ok = 1;
    for(int j = 0; j < cur; j++)
      if(A[j] == i) ok = 0; // i is already used in A[0]~A[cur-1]
    if(ok) {
      A[cur] = i;
      print_permutation(n, A, cur+1); // recursion
    }
  }
}

int main() {
  int n;
  scanf("%d", &n);
  print_permutation(n, A, 0); 
  return 0;
}
