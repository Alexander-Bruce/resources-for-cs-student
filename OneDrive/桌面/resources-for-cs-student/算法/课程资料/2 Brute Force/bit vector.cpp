#include<cstdio>
using namespace std;

void print_subset(int n, int* B, int cur) {
  if(cur == n) {
    for(int i = 0; i < cur; i++)
      if(B[i]) printf("%d ", i); // print the current set
    printf("\n");
    return;
  }
  B[cur] = 1; // select the "cur" element
  print_subset(n, B, cur+1);
  B[cur] = 0; // do not select the "cur" element
  print_subset(n, B, cur+1);
}

int B[10];
int main() {
  int n;
  scanf("%d", &n);
  print_subset(n, B, 0);
  return 0;
}
