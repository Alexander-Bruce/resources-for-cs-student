#include<iostream>
#include<algorithm>
using namespace std;

const int maxn=100010;
int arr[maxn],A[maxn];
 
void print_subset(int n, int cur){
    for(int i=0;i<cur;i++)
    	cout<<arr[A[i]]<<' ';
    cout<<endl;
    int s=cur?A[cur-1]+1:0;
    for(int i=s;i<n;i++){
        A[cur]=i; //A is used as the subscript corresponding to the output element in the arr array
        print_subset(n,cur+1);
    }
}
 
int main(){
	int n;
    cin>>n;
    for(int i=0;i<n;i++)
        cin>>arr[i];
 //  sort(arr,arr+n); // According to the order of the input if without sort()
    print_subset(n,0);
    return 0;
}

