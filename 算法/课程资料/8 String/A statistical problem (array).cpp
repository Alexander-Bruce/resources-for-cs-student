#include <bits/stdc++.h>
using namespace std;

int trie[1000010][26];    // using an array to implement the trie
int num[1000010]={0};     // the number of words prefixed with a certain string
int pos = 1; // for the storage position of a string 

void Insert(char str[]){    // insert str to the trie
    int c = 0;
    for(int i=0;str[i];i++){
        int n = str[i]-'a';
        if(trie[c][n]==0)    // if there is no corresponding character
            trie[c][n] = pos++;
        c = trie[c][n];
        num[c]++;
    }
}
int Find(char str[]){   // return the number of words prefixed with str
    int c = 0;
    for(int i=0;str[i];i++){
        int n = str[i]-'a';
        if(trie[c][n]==0)
            return 0;
        c = trie[c][n];
    }
    return num[c];
}

int main(){
    char str[11];
    while(gets(str)){
        if ( !strlen(str) )  break;  // input an empty line
        Insert(str);
    }
    while( gets(str))  cout << Find(str) << endl;
    return 0;
}
