#include<iostream>
#include<ctime>
#include<cstdlib>
#include<Windows.h>
#include<cmath>
using namespace std;
const int N = 15;

int array[N];
// recursive space complexity O(logn)
/* 
int BSearch(int left, int right, int value){
	if(left>right)
		return -1;
	if(array[(right-left)/2+left]==value)
		return (right-left)/2+left;
	else if(array[(right-left)/2+left]>value)
		return BSearch(left,(right-left)/2-1+left,value);
	else return BSearch((right-left)/2+1+left,right,value);
}*/

// iterative  space complexity O(1)
int BSearch(int left, int right, int value){
	while(left<=right){
		int mid = (right-left)/2+left; // or (right+left)>>1
		if(array[mid]==value)
			return mid;
		else if(array[mid]>value)
			right = (right-left)/2-1+left;
		else
			left = (right-left)/2+1+left;
	}
	return -1;
}

double BSqrt(double x){
	double min=0, max=x, mid=x/2;
	while(abs(mid*mid-x)>0.0000001){
		if(mid*mid>x)
			max=mid;
		else
			min=mid;
		mid=(max+min)/2;
	}
	return mid;
}

int main(){
//	srand((unsigned)time(NULL));
/*
	for(int i=0;i<N;i++)
		array[i]=i;
	
	for(int i=0;i<N;i++)
		cout<<array[i]<<" ";
	cout<<endl;
	
	int index=BSearch(0,N-1,0);
	cout<<index<<endl;
*/
	
	double value = BSqrt(5);
	cout<<value<<endl;

	return 0;
} 
