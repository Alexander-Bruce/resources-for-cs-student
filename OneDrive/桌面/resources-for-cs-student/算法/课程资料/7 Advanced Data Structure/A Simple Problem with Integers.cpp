#include <stdio.h>
using namespace std;
const int MAXN = 1e5 + 10;

// sum[] for "the interval sum"
// add[] for "tag"; It records whether "lazy" is used in node i, and its value is c in "C a b c"; 
// if "lazy" for multiple times, add[i] can be accumulated. 
// Once node i goes deeper in a certain "C a b c" and "lazy" is destroyed, then add[i] is reset to 0, 
// and push_down() completes this task.
long long sum[MAXN << 2], add[MAXN << 2];  // 4*N

void push_up(int rt){     // updating upward 
    sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
}

void push_down(int rt, int m){               // updating downward
    if(add[rt]){
        add[rt << 1] += add[rt];
        add[rt << 1 | 1] += add[rt];
        sum[rt << 1] += (m - (m >> 1)) * add[rt];
        sum[rt << 1 | 1] += (m >> 1) * add[rt];
        add[rt] = 0;                             // reset add[i] 
    }
}

#define lson l, mid, rt << 1
#define rson mid + 1, r, rt << 1 | 1
void build(int l, int r, int rt){            // build a full binary tree
    add[rt] = 0;
    if(l == r){                                   // leaf node, assignment the value
        scanf("%lld", &sum[rt]);
        return;
    }
    int mid = (l + r) >> 1;
    build(lson);
    build(rson);
    push_up(rt);                                  // updating upward
}

void update(int a, int b, long long c, int l, int r, int rt){ // C a b c 
    if(a <= l && b >= r){
        sum[rt] += (r - l + 1) * c;
        add[rt] += c;  // lazy
        return;
    }
    push_down(rt, r - l + 1);       // updating downward
    int mid = (l + r) >> 1;
    if(a <= mid) update(a, b, c, lson);        // split and go deeper
    if(b > mid) update(a, b, c, rson);
    push_up(rt); // updating upward
}

long long query(int a, int b, int l, int r, int rt){       // Q a b
    if(a <= l && b >= r) return sum[rt];          // "lazy", return sum[i]
    push_down(rt, r - l + 1);                       // updating downward
    int mid = (l + r) >> 1;
    long long ans = 0;
    if(a <= mid) ans += query(a, b, lson);
    if(b > mid) ans += query(a, b, rson);
    return ans;
}

int main(void){
    int n, m;
    scanf("%d%d", &n, &m);
    build(1, n, 1);
    while(m--){
        char str[2];
        int a, b; long long c;
        scanf("%s", str);
        if(str[0] == 'C'){
            scanf("%d%d%lld", &a, &b, &c);
            update(a, b, c, 1, n, 1);
        }else{
            scanf("%d%d", &a, &b);
            printf("%lld\n", query(a, b, 1, n, 1));
        }
    }
}
