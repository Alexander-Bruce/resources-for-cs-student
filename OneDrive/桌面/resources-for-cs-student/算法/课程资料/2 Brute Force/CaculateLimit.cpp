#include<iostream>
using namespace std;

int fac(int n){
	return n==0 ? 1 : n*fac(n-1);
}

int main(){
	double base = 1.0,tmp;
	cout<<base<<endl;
	for(int n=2;n<=20;n++){
		tmp = 1.0/fac(n-1);
		base += tmp;
		cout<<base<<endl;
	}
	return 0;
} 
