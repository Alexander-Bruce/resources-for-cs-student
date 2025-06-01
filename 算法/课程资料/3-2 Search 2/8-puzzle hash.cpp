// 8-puzzle Hash
#include<cstdio>
#include<cstring>
//#include<set>
#include<Windows.h>
//using namespace std;

typedef int State[9];  // // define the State type: int[9]
const int MAXSTATE = 1000000;
State st[MAXSTATE], goal;  // the array of all states st[MAXSTATE]
int dist[MAXSTATE]; // the distance array

const int MAXHASHSIZE = 1000003;
int head[MAXHASHSIZE], next[MAXSTATE];
void init_lookup_table() { memset(head, 0, sizeof(head)); }
// This hash function perfectly maps keys into hash codes (collisionless)
int hash(State& s) {
  int v = 0;
  for(int i = 0; i < 9; i++) v = v * 10 + s[i]; // s -> a 9-digit decimal integer (876543210)
  return v % MAXHASHSIZE;  // hash(v) < hashsize
}

int try_to_insert(int s) {
  int h = hash(st[s]);
  int u = head[h]; // the head of a linked list
  while(u) {
    if(memcmp(st[u], st[s], sizeof(st[s])) == 0) return 0;  // this state already exists, return
    u = next[u];  // continue to find the state in the linked list
  }
  next[s] = head[h]; 
  head[h] = s; // insert s to the linked list (head)
  return 1;
}

const int dx[] = {-1, 1, 0, 0}; // up down left right
const int dy[] = {0, 0, -1, 1};
int bfs() {
  init_lookup_table();
  int front = 1, rear = 2;  // start from 1 (the index 0 is not used)
  while(front < rear) {
    State& s = st[front];  // use &s to simplify the code
    if(memcmp(goal, s, sizeof(s)) == 0) return front; // find the goal state, return; memcmp() in cstring
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
FILE* fp = fopen("8-puzzle-nosolution.in","r");
  for(int i = 0; i < 9; i++)
    fscanf(fp, "%d", &st[1][i]);
  for(int i = 0; i < 9; i++)
    fscanf(fp, "%d", &goal[i]);
fclose(fp);
    
  DWORD start = GetTickCount();
  int ans = bfs();
  DWORD end = GetTickCount();
  printf("%ums\n",end-start);
  
  if(ans > 0) printf("%d\n", dist[ans]);
  else printf("-1\n");
  return 0;
}
