#include<iostream>
#include<cstring>
using namespace std;

long long m[50];

long long fib1(int n){
	return n<=1 ? n : fib1(n-1)+fib1(n-2);
}

long long fib2(int n){
	return m[n]==-1 ? m[n]=fib2(n-1)+fib2(n-2) : m[n];
}

long long fib3(int n){
	if(n==0)
		return 0;
	else{
		long long pre=0, cur=1;
		for(int i=2;i<=n;i++){
			long long next=pre+cur;
			pre = cur;
			cur = next;
		}
		return cur;
	}
}

// dp fib() to be implemented

int main(){
	memset(m,-1,sizeof(m));
	m[0]=0;
	m[1]=1;
	int n;
	while(cin>>n)
		cout<<fib3(n)<<endl;
	
	return 0;
}
