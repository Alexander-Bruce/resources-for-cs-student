#include <cstdio>
#include <cstring>
#include <string>
#include <iostream>
#include <algorithm>
#include <queue>
using namespace std;

string s;
// http://www.cplusplus.com/reference/queue/priority_queue/
// min heap; if default (the 3rd parameter), max heap
priority_queue <int, vector<int>, greater<int> > q;  

int main()
{
	while(getline(cin, s) && s != "END"){
		// create the priority_queue
		int t = 1;
		sort(s.begin(), s.end()); // for frequency t (times)
		for(int i = 1; i < s.length(); i++){
			if(s[i] != s[i-1]){
				q.push(t);
				t = 1;
			}
			else t++;
		}
		q.push(t);
		
		// only one type of character
		if(q.size() == 1) {
			printf("%d %d 8.0\n", s.length()*8, s.length());
			q.pop();
			continue;
		}

		int ans = 0; // the length by using Huffman Coding
		while(q.size() > 1){
			int a = q.top(); q.pop();
			int b = q.top(); q.pop();
			q.push(a+b);
			ans += a+b;
		}
		q.pop();
		printf("%d %d %.1lf\n", s.length()*8, ans, (double)s.length()*8.0/(double)ans);
	}
}
