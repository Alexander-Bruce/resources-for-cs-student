#include<stdio.h>
#include<string.h>

bool bits[10]; //0~9 used or not
//choose = 1, 5 bits; choose = 2, 10 bits
bool check(int num, int choose) 
{
	if(choose == 1)
		memset(bits,0,sizeof(bits));
	int i,j,k,l,m;
	i = num/10000;
	j = num/1000%10;
	k = num/100%10;
	l = num/10%10;
	m = num%10;
	bits[i] = bits[j] = bits[k] = bits[l] = bits[m] = 1;
	int count = 0;
	for(int x=0;x<=9;x++)
		if(bits[x])
			count++;
	if(choose==1 && count<5 || choose==2 && count<10)
		return false;
	return true;
}
// no need to enumerate 10£¡=3628800
int main()
{
	int n;
	bool flag=false;
	while(scanf("%d",&n) && n!=0)
	{	
		flag=false;
		for(int b=1234;b<=98765;b++)
			if(check(b,1) && b*n>10000 && b*n<99999 && check(b*n,2)){
				flag=true;
				printf("%d/%d = %d\n",b*n,b,n);
			}
		if(flag)
			printf("\n");
		else
			printf("There are no solutions for %d.\n",n);			
	}
	return 0;
}
