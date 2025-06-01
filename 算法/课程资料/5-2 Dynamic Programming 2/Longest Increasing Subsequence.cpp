#include<iostream>
using namespace std;

const int MAXN = 10000;
int n, A[MAXN];
int dp[MAXN];

int LIS(){
    int ans = 1;
    dp[1] = 1;
    for(int i = 2; i <= n; i++){
        int max = 0;
        for(int j=1; j<i; j++)
            if(dp[j] > max  &&  A[j] < A[i])
                max = dp[j];
        dp[i] = max+1;
        if(dp[i] > ans) ans = dp[i];
    }
    return ans;
}

int main(){
    while(cin >> n){
        for(int i=1; i<=n; i++)
            cin >> A[i];
        cout << LIS() << endl;
    }
    return 0;
}

