#include<cstdio>
#include<queue>
using namespace std;

int main() {
  int n, x;
  while(scanf("%d", &n) == 1 && n) {
    priority_queue<long long, vector<long long>, greater<long long> > q;
    for(int i = 0; i < n; i++) { scanf("%d", &x); q.push(x); }
    long long ans = 0;
    for(int i = 0; i < n-1; i++) {
      long long a = q.top(); q.pop();
      long long b = q.top(); q.pop();
      ans += a+b;
      q.push(a+b);
    }
    printf("%lld\n", ans);
  }
  return 0;
}
