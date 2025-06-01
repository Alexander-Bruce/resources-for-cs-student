// Algorithm: Considering letters as nodes and words as directed edges, 
// there is a solution if and only if there is Euler path in the graph. 
// First determine whether it is a connected graph
#include<cstdio>
#include<cstring>
#include<vector>
using namespace std;

const int maxn = 1000 + 5; // the max length of words

// Union-find set
int pa[256];  // letters as subscripts, for convenience
int findset(int x) { return pa[x] != x ? pa[x] = findset(pa[x]) : x; } 

int used[256], deg[256]; // used or not; degree

int main() {
  int T;
  scanf("%d", &T);
  while(T--) {
    int n;
    char word[maxn];

    scanf("%d", &n);
    memset(used, 0, sizeof(used));
    memset(deg, 0, sizeof(deg));
    for(int ch = 'a'; ch <= 'z'; ch++) pa[ch] = ch; // Initialize the union-find set
    int cc = 26; // Number of connected graphs

    for(int i = 0; i < n; i++) {
      scanf("%s", word);
      char c1 = word[0], c2 = word[strlen(word)-1];
      deg[c1]++;
      deg[c2]--;
      used[c1] = used[c2] = 1;
      int s1 = findset(c1), s2 = findset(c2);
      if(s1 != s2) { pa[s1] = s2; cc--; }  // union
    }

    vector<int> d;
    for(int ch = 'a'; ch <= 'z'; ch++) {
      if(!used[ch]) cc--; // Letters not used
      else if(deg[ch] != 0) d.push_back(deg[ch]);
    }
    bool ok = false;
    if(cc == 1 && (d.empty() || (d.size() == 2 && (d[0] == 1 || d[0] == -1)))) ok = true;
    if(ok) printf("Ordering is possible.\n");
    else printf("The door cannot be opened.\n");
  }
  return 0;
}