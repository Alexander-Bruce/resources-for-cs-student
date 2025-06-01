#include<stdio.h>
using namespace std;
const int Max = 10000;

struct{
    int l, r, len;      //  len means the number of cows
}tree[4*Max];           // 4*N

int pre[Max], ans[Max];

void BuildTree(int left, int right, int u){     // u means node
    tree[u].l = left;
    tree[u].r = right;
    tree[u].len = right - left + 1;
if(left == right)
return;
    BuildTree(left, (left+right)>>1, u<<1);    // left child tree
    BuildTree(((left+right)>>1)+1, right, (u<<1)+1);    // right child tree
}

int query(int u, int num){    // query & update; return the "num"th number in [l,r]
    tree[u].len --;         // len--
    if(tree[u].l == tree[u].r)
        return tree[u].l;   // value == l == r
// if the left range is not enough, query the "num - tree[u<<1].len"th number in the right range
    if(tree[u<<1].len < num)
        return query((u<<1)+1, num - tree[u<<1].len);
// if the left range is enough, query the "num"th number in the left range
    if(tree[u<<1].len >= num)
        return query(u<<1, num);
}

int main(){
    int n, i;
    scanf("%d", &n);
    pre[1] = 0;
    for(i = 2; i <= n; i ++)
        scanf("%d", &pre[i]);
    BuildTree(1, n, 1);
    for(i = n; i >= 1; i --)     // backward
        ans[i] = query(1, pre[i]+1);
    for(i = 1; i <= n; i ++)
        printf("%d\n", ans[i]);
    return 0;
}
