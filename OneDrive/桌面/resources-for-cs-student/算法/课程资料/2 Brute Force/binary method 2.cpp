#include<iostream>
#include<algorithm>
using namespace std;

const int maxn=100010;
int n;
int arr[maxn];
 
void print_subset(int s){
    for(int i=0;i<n;i++)
        if(s&(1<<i)) cout<<arr[i]<<' ';
    cout<<endl;
    return;
}
 
int main () {
    cin>>n;
    for(int i=0;i<n;i++)
        cin>>arr[i];
    for(int i=0;i<(1<<n);i++)
        print_subset(i);
    return 0;
}

