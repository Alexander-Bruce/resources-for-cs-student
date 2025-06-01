#include<iostream>
using namespace std;

int cnt1(int x){
	int c = 0;
	for(;x;x>>=1) if(x&1) c++;
	return c;
}

int main(){
    int a[100],b,i,j,n,m,k,min,t,ca;
    cin >> ca;
    while(ca--){
    	cin >> n >> m;
    	for(i=0;i<n;i++)
			cin>>a[i]; 
    	for(i=0;i<m;i++)
    	{
    		cin>>b;
    		min = cnt1(b^a[0]);
    		k = 0;
    		for(j=1;j<n;j++){
    			t = cnt1(b^a[j]);  // XOR operator
    			if(t < min || t == min && a[j] < a[k]) min = t, k = j;
    		}
    		cout << a[k] << endl;
    	}
    	
    }
    return 0;
}
