#include <bits/stdc++.h>
using namespace std;
const double eps = 1e-8;     // end temperature
double y;
double func(double x){       // compute f(x)
	return 6*pow(x,7.0)+8*pow(x,6.0)+7*pow(x,3.0)+5*pow(x,2.0)-y*x;
}
double solve(){
  	double T = 100;         // starting temperature
  	double delta = 0.98;   //  cooling coefficient
  	double x = 50.0;        // the initual value of x
  	double now = func(x);  
  	double ans = now;      
  	while(T > eps){         // end temperature
     	int f[2]={1,-1};
		double newx = x+f[rand()%2]*T;    // probabilistically modify x according to the cooling coefficient
		if(newx >= 0 && newx <= 100){
	 		double next = func(newx);
			ans = min(ans,next);
			if(now - next > eps){x = newx; now = next;}     // update x
		}
		T *= delta; // decrease to the next temperature according to the cooling coefficient
	}
    return ans;
}
int main(){
     int cas; scanf("%d",&cas);
	 while(cas--){
		scanf("%lf",&y);
		printf("%.4f\n",solve());
	}
}
