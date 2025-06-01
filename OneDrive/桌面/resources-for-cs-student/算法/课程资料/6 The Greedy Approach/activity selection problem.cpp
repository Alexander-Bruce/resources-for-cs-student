#include<cstdio>
#include<algorithm>
using namespace std;

struct Contest{
	int begin;
	int end;
};

Contest contests[1000];

bool compare(Contest a, Contest b){
	return a.end < b.end;
}

int main() {

	int N;
	while (scanf("%d", &N) != EOF){
		for (int i = 0; i < N; i++)
			scanf("%d%d", &contests[i].begin, &contests[i].end);
	
		sort(contests, contests + N, compare);
		int results = 1;
		int j = 0;
		
		for (int i = 1; i < N; i++){
			if (contests[i].begin >= contests[j].end){
				results++;
				j = i;
			}
		}
		printf("%d\n", results);
	}


	return 0;
}