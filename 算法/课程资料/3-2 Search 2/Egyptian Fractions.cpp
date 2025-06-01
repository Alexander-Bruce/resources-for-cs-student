// Egyptian Fractions
#include<cstdio>
#include<cstring>
#include<iostream>
#include<algorithm>
#include<cassert>
using namespace std;

int a, b, maxd;

typedef long long LL;

LL gcd(LL a, LL b) {
  return b == 0 ? a : gcd(b, a%b);
}

// the smallest c which satisfies 1/c <= a/b
inline int get_first(LL a, LL b) {
  return b/a+1;
}

const int maxn = 100 + 5;

LL v[maxn], ans[maxn]; // v is the current solution 

// if the current solution v is better than ans, then update ans
bool better(int d) {
  for(int i = d; i >= 0; i--) if(v[i] != ans[i]) {
    return ans[i] == -1 || v[i] < ans[i];
  }
  return false;
}

// the current depth is d; the denominator >= from; the sum of fractions == aa/bb
bool dfs(int d, int from, LL aa, LL bb) {
  if(d == maxd) {
    if(bb % aa) return false; // aa/bb must be an egyptian fraction, that is 1/n
    v[d] = bb/aa;
    if(better(d)) memcpy(ans, v, sizeof(LL) * (d+1));
    return true;
  }
  bool ok = false;
  from = max(from, get_first(aa, bb)); // the start number of the enumaration
  for(int i = from; ; i++) {
//pruning: given the remaining maxd+1-d fractions (1/i), if the sum of them <= aa/bb, then prune the branch
    if(bb * (maxd+1-d) <= i * aa) break;
    v[d] = i;
    //  a2/b2 = aa/bb - 1/i
    LL b2 = bb*i;
    LL a2 = aa*i - bb;
    LL g = gcd(a2, b2); // reduction of a fraction
    if(dfs(d+1, i+1, a2/g, b2/g)) ok = true;
  }
  return ok;
}

int main() {
  int kase = 0;
  while(cin >> a >> b) {
    int ok = 0;
    for(maxd = 1; maxd <= 100; maxd++) {
      memset(ans, -1, sizeof(ans));
      if(dfs(0, get_first(a, b), a, b)) { ok = 1; break; }
    }
    cout << "Case " << ++kase << ": ";
    if(ok) {
      cout << a << "/" << b << "=";
      for(int i = 0; i < maxd; i++) cout << "1/" << ans[i] << "+";
      cout << "1/" << ans[maxd] << "\n";
    } else cout << "No solution.\n";
  }
  return 0;
}
