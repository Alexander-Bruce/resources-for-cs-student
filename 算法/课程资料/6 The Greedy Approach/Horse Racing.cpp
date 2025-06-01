#include <cstdio>
#include <algorithm>
#include <functional>

int main(){
	int i,n,tian[1011],king[1011];
	while(scanf("%d",&n),n){
		for(i=0;i<n;i++) scanf("%d",tian+i);
		for(i=0;i<n;i++) scanf("%d",king+i);
		std::sort(tian,tian+n,std::greater<int>());
		std::sort(king,king+n,std::greater<int>());
		int ti,tj,ki,kj,ans = 0;
		ti = ki = 0;
		tj = kj = n-1;
		for(i=0;i<n;i++){
			if(tian[ti]>king[ki]){
				ans++;
				ti++,ki++;
			} else if(tian[ti]<king[ki]){
				ans--;
				tj--,ki++;
			} else {
				if(tian[tj] > king[kj]){
					ans++;
					tj--,kj--;
				} else {
					if(tian[tj] < king[ki]) ans--;
					else if(tian[tj] > king[ki]) ans++;
					tj--,ki++;
				}
			}
		}
		printf("%d\n",ans*200);
	}
	return 0;
}