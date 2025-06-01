// 8-puzzle encoding
#include<cstdio>
#include<cstring>
//#include<set>
#include<Windows.h>
//using namespace std;

typedef int State[9];  // define the State type: int[9]
const int MAXSTATE = 1000000;
State st[MAXSTATE], goal;  // the array of all states st[MAXSTATE]
int dist[MAXSTATE]; // the distance array

// Cantor expansion ¿µÍÐÕ¹¿ª 
int vis[362880], fact[9];   // 0~8 permutations -> 0~362879 (9!=362880)
void init_lookup_table() {
  fact[0] = 1;
  for(int i = 1; i < 9; i++) fact[i] = fact[i-1] * i;
}

int try_to_insert(int s) {
  int code = 0;            // map st[s] to code
  for(int i = 0; i < 9; i++) {
    int cnt = 0;
    for(int j = i+1; j < 9; j++) if(st[s][j] < st[s][i]) cnt++;
    code += fact[8-i] * cnt;
  }
  if(vis[code]) return 0;
  return vis[code] = 1;
}

const int dx[] = {-1, 1, 0, 0}; // up down left right
const int dy[] = {0, 0, -1, 1};
int bfs() {
  init_lookup_table();
  int front = 1, rear = 2;  // start from 1 (the index 0 is not used)
  while(front < rear) {
    State& s = st[front];  // use &s to simplify the code
    if(memcmp(goal, s, sizeof(s)) == 0) return front; // find the goal state, return; memcmp() is in cstring
    int z;
    for(z = 0; z < 9; z++) if(!s[z]) break;  // find the empty position, that is, 0
    int x = z/3, y = z%3;  // row && column (0~2)
    for(int d = 0; d < 4; d++) {
      int newx = x + dx[d];
      int newy = y + dy[d];
      int newz = newx * 3 + newy;  // the new 0 position
      if(newx >= 0 && newx < 3 && newy >= 0 && newy < 3) {  // if the move is possible
        State& t = st[rear];
        memcpy(&t, &s, sizeof(s)); // expand a new state; memcpy s->t
        t[newz] = s[z]; // move 0
        t[z] = s[newz]; // move a number
        dist[rear] = dist[front] + 1;  // compute the new state's distance
        if(try_to_insert(rear)) rear++; // the insert succeeds, rear++ 
      }
    }
    front++; // after expansion, front++ 
  }
  return 0;  // failed
}

int main() {
  for(int i = 0; i < 9; i++)
    scanf("%d", &st[1][i]);
  for(int i = 0; i < 9; i++)
    scanf("%d", &goal[i]);
    
  DWORD start = GetTickCount();
  int ans = bfs();
  DWORD end = GetTickCount();
  printf("%ums\n",end-start);
  
  if(ans > 0) printf("%d\n", dist[ans]);
  else printf("-1\n");
  return 0;
}
