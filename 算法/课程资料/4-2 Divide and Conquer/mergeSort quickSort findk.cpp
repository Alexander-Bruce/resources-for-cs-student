#include<iostream>
#include<ctime>
#include<cstdlib>
#include<Windows.h>
using namespace std;
const int N = 10;

int array[N];

//mergeSort
void mergeSort(int p, int r){
	if(p>=r)
		return;
	int q = (p+r)/2;
	mergeSort(p,q);
	mergeSort(q+1,r);
	//merge
	int* a = new int[r-p+1];
	int i=p,j=q+1,k=0;
	while(i<=q && j<=r){
		if(array[i]<array[j])
			a[k++]=array[i++];
		else
			a[k++]=array[j++];
	}
	int start=i, end=q;
	if(j<=r)
		start=j, end=r;
	while(start<=end)
		a[k++]=array[start++];
	for(i=0;i<=r-p;i++)
		array[p+i]=a[i];
	delete [] a;	
}

void quickSort(int p, int r){
	if(p>=r)
		return;
	int i=p;
	for(int j=p;j<r;j++)
		if(array[j]<array[r]){
			int tmp=array[i];
			array[i]=array[j];
			array[j]=tmp;
			i++;
		}
	int tmp=array[r];
	array[r]=array[i];
	array[i]=tmp;
	quickSort(p,i-1);
	quickSort(i+1,r);
}

// finding the kth smallest element in O(n) 
int findSmallestK(int p, int r, int k){
	if(p<=r){
		int i=p;
		for(int j=p;j<r;j++)
			if(array[j]<array[r]){
				int tmp=array[i];
				array[i]=array[j];
				array[j]=tmp;
				i++;
			}
		int tmp=array[r];
		array[r]=array[i];
		array[i]=tmp;
		
		if(i==k-1) return array[i];
		else if(i<k-1) findSmallestK(i+1,r,k);
		else findSmallestK(p,i-1,k);
	}else return -1;
}

int main(){
//	srand((unsigned)time(NULL));
//	for(int i=0;i<N;i++)
//		array[i]=rand()%32768;
	
//	for(int i=0;i<N;i++)
//		cout<<array[i]<<" ";
//	cout<<endl;
	
//   DWORD start = GetTickCount();	
//	mergeSort(0,N-1);
//	quickSort(0,N-1);
//	DWORD end = GetTickCount();
	
//	cout<<end-start<<"ms"<<endl;
//	for(int i=0;i<N;i++)
//		cout<<array[i]<<" ";

	for(int i=0;i<N;i++)
		array[i]=i;
	
	for(int i=0;i<N;i++)
		cout<<array[i]<<" ";
	cout<<endl;
		
	cout<<findSmallestK(0,N-1,1)<<endl;
		
	return 0;
} 
