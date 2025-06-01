#include<bits/stdc++.h>
using namespace std;
const int NUM = 3;
const int Value[NUM] = {1,2,5};
int main(){
    int i, money;
    int ans[NUM] = {0};   		    //记录每种硬币的数量
    cin >> money;   		    	        //输入钱数
    for(i= NUM-1; i>=0; i--){         //求每种硬币的数量
        ans[i] = money/Value[i];
        money = money - ans[i]*Value[i];
    }
    for(i= NUM-1; i>=0; i--)
        cout << Value[i] << "元硬币数：" << ans[i] << endl;
    return 0;
}
