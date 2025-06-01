#include<iostream>
#include<ctime>
#include<cstdlib>
#include<Windows.h>
#include<cmath>
using namespace std;
const int N = 15;

int array[N];

// Find the first element equal to a given value
int BSearchFirst(int left, int right, int value){
	int l = left;
	while(left<=right){
		int mid = (right-left)/2+left; 
		if(array[mid]>value)
			right = mid-1;
		else if(array[mid]<value)
			left = mid+1;
		else{
			if(mid==l || array[mid-1]!=value)
				return mid;
			else
				right = mid-1;
		}
	}
	return -1;
}

// Find the last element equal to a given value
int BSearchLast(int left, int right, int value){
	int r = right;
	while(left<=right){
		int mid = (right-left)/2+left; 
		if(array[mid]>value)
			right = mid-1;
		else if(array[mid]<value)
			left = mid+1;
		else{
			if(mid==right || array[mid+1]!=value)
				return mid;
			else
				left = mid+1;
		}
	}
	return -1;
}

// Find the first element greater than or equal to a given value
int BSearchFirstGreater(int left, int right, int value){
	int l = left;
	while(left<=right){
		int mid = (right-left)/2+left; 
		if(array[mid]>=value){
			if(mid==l || array[mid-1]<value)
				return mid;
			else
				right = mid-1;
		}else
			left = mid+1;
	}
	return -1;
}

// Find the last element smaller than or equal to a given value
int BSearchLastSmaller(int left, int right, int value){
	int r = right;
	while(left<=right){
		int mid = (right-left)/2+left; 
		if(array[mid]<=value){
			if(mid==r || array[mid+1]>value)
				return mid;
			else
				left = mid+1;
		}else
			right = mid-1;
	}
	return -1;
}

int main(){

	for(int i=0;i<3;i++)
		array[i]=1;	
	for(int i=3;i<8;i++)
		array[i]=3;
	for(int i=8;i<15;i++)
		array[i]=8;
	
	for(int i=0;i<N;i++)
		cout<<array[i]<<" ";
	cout<<endl;
	
	int index=BSearchLastSmaller(0,N-1,9);
	cout<<index<<endl;

	return 0;
} 