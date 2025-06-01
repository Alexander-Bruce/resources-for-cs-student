#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <cmath>
#include <algorithm>
using namespace std;

const int maxn = 200 + 10;
int n, ans[maxn];

struct node {
    int x, y, k, color;     // color means "apple tree" (0) or "ant colony" (1)
    double rad; // angle
    bool operator < (const struct node &rhs) const {
        return rad < rhs.rad;
    }
}A[maxn], temp[maxn];

void solve(int L, int R) {  // [L,R)
    if (L == R - 2) {  // end of recursion, only 2 points
        if(A[L].color == 1){
            ans[A[L].k] = A[R - 1].k;
        }else
            ans[A[R - 1].k] = A[L].k;
        return;
    }

    int minp = L;
    for(int i = L; i < R; i++)
        if(A[i].y < A[minp].y || (A[i].y == A[minp].y && A[i].x < A[minp].x))
            minp = i;

    swap(A[L], A[minp]);
    for(int i = L + 1; i < R; i++) {
        int nx = A[i].x - A[L].x;
        int ny = A[i].y - A[L].y;
        A[i].rad = atan2(ny, nx);
    }
    sort(A+L+1,A+R);

    if (A[L].color == A[L + 1].color) {
        int cnt = 0, py = L + 1;  // py means the other point
        while(cnt != 0 || py == (L + 1)) {
            if(A[py].color == A[L].color) cnt++;
            else cnt--;
            py++;
        }
        swap(A[L], A[py - 1]);
        solve(L, py - 1);  // divide
        solve(py - 1, R);
    }
    else {
        solve(L, L + 2); // divide
        solve(L + 2, R);
    }
}

int main(){
    while (scanf("%d", &n) == 1 && n) {
        int x, y;
        for (int i = 0; i < 2*n; i++) {
            scanf("%d%d", &x, &y);
            A[i].x = x; A[i].y = y; A[i].k = i % n;
            if (i < n) A[i].color = 1; // ant colony
            else A[i].color = 0; // apple tree
        }
        solve(0, 2 * n);   // [0,2*n)
        for (int i = 0; i < n; i++)
            printf("%d\n", ans[i]+1);
        printf("\n");
    }
    return 0;
}
