// UVa1025 A Spy in the Metro
#include<iostream>
#include<cstring>
using namespace std;

const int maxn = 50 + 5;
const int maxt = 200 + 5;
const int INF = 1000000000;

// has_train[t][i][0] means at time t, whether there is a train going to the right at station i
// has_train[t][i][1] means at time t, whether there is a train going to the left at station i
int t[maxn], has_train[maxt][maxn][2];
int dp[maxt][maxn]; // dp[i][j] means at time i, station j, the mininum waiting time (still left)
// dp[T][n] = 0, dp[T][i] = INF (i!=n) 

int main() {
  int kase = 0, n, T;
  while(cin >> n >> T && n) {
    int M1, M2, d;
    for(int i = 1; i <= n-1; i++) cin >> t[i];

    // compute the has_train array
    memset(has_train, 0, sizeof(has_train));
    cin >> M1;
    while(M1--) {
      cin >> d;
      for(int j = 1; j <= n-1; j++) {
        if(d <= T) has_train[d][j][0] = 1;
        d += t[j];
      }
    }
    cin >> M2;
    while(M2--) {
      cin >> d;
      for(int j = n-1; j >= 1; j--) {
        if(d <= T) has_train[d][j+1][1] = 1;
        d += t[j];
      }
    }

    // DP process
    for(int i = 1; i <= n-1; i++) dp[T][i] = INF;
    dp[T][n] = 0;

	// At each state, there are 3 dicisions
    for(int i = T-1; i >= 0; i--)
      for(int j = 1; j <= n; j++) {
        dp[i][j] = dp[i+1][j] + 1; // wait a minute
        if(j < n && has_train[i][j][0] && i+t[j] <= T)
          dp[i][j] = min(dp[i][j], dp[i+t[j]][j+1]); // right
        if(j > 1 && has_train[i][j][1] && i+t[j-1] <= T)
          dp[i][j] = min(dp[i][j], dp[i+t[j-1]][j-1]); // left
      }

    // output
    cout << "Case Number " << ++kase << ": ";
    if(dp[0][1] >= INF) cout << "impossible\n";
    else cout << dp[0][1] << "\n";
  }
  return 0;
}
