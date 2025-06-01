#include<cstdio>
using namespace std;

void print_subset(int n, int s) {
  for(int i = 0; i < n; i++)
    if(s&(1<<i)) printf("%d ", i);
  printf("\n");
}

int main() {
  int n;
  scanf("%d", &n);
  for(int i = 0; i < (1<<n); i++)  // 0 ~ 2^n-1
  	print_subset(n,i); // n=2, 00 01 10 11
  return 0;
}
