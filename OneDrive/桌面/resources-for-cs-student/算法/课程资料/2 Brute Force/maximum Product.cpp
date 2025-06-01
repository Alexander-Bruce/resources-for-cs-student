#include<stdio.h>
#include<string.h>

const int MAXN = 18;
int num[MAXN];

long long maxmul(int * array, int length)
{
	int i,j;
	long long temp, max = 0;    // maxinum product 10^18, long long
	// enumerate all products
	for(i=0;i<length;i++)
	{
		temp = array[i];
		if(temp>max)
			max = temp;
		for(j=i+1;j<length;j++)
		{
			temp *= array[j];
			if(temp>max)
				max = temp;
		}
	}
	return max;
}

int main()
{
	int n;
	int no=0;
	while(scanf("%d",&n)==1)
	{
		no++;
		for(int i=0;i<n;i++)
			scanf("%d", &num[i]);
		printf("Case #%d: The maximum product is %lld.\n",no,maxmul(num, n));
	}
	return 0;
}