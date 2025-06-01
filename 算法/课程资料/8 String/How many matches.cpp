#include<bits/stdc++.h>
using namespace std;
const int MAXN = 1000+5;
char str[MAXN], pattern[MAXN];
int Next[MAXN];
int cnt;
// compute Next[]; for j's backtracking
int getFail(char *p, int plen){
    Next[0]=0; Next[1]=0;
    for(int i=1; i < plen; i++){
        int j = Next[i];
        while(j && p[i] != p[j])   j = Next[j];
        Next[i+1] = (p[i]==p[j]) ? j+1 : 0;
    }
}

int kmp(char *s, char *p) {      // find p in s
   int last = -1;
    int slen=strlen(s), plen=strlen(p);
    getFail(p, plen);              // compute Next[]
    int j=0;
    for(int i=0; i<slen; i++) {  // match each character of S
        while(j && s[i]!=p[j])  j=Next[j];  // mismatch, use Next[j] to compute the backtracking position
        if(s[i]==p[j])  j++;      // match & continue
        if(j == plen) {           // completely match
           	// this match's beginning position is i+1-plen, and its end position is i (in S)
           	// printf("at location=%d, %s\n", i+1-plen,&s[i+1-plen]);
            //-------------------for cnt
            if( i-last >= plen) {  // whether the new match and the last match can be separated
                cnt++;
                last=i;               // last is the end position of the last match
            }
            //-------------------
        }
    }
}
int main(){
    while(~scanf("%s", str)){      // read the str
        if(str[0] == '#')  break;
        scanf("%s", pattern);      // read the pattern str
        cnt = 0;
        kmp(str, pattern);
        printf("%d\n", cnt);
    }
    return 0;
}
