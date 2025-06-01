#include<iostream>
#include<algorithm>
using namespace std;

const int maxn=100010;
int n;
int arr[maxn],B[maxn];
 
void print_subset(int cur){
    if(cur==n){
        for(int i=0;i<cur;i++)
            if(B[i]) cout<<arr[i]<<' ';
        cout<<endl;
        return;
    }
    B[cur]=1; // Select the cur element
    print_subset(cur+1);
    B[cur]=0; // Do not select the cur element
    print_subset(cur+1);
    return;
}
 
int main () {
    cin>>n;
    for(int i=0;i<n;i++)
        cin>>arr[i];
    print_subset(0);
    return 0;
}

