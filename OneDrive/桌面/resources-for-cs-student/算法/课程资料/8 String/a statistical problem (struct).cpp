include <bits/stdc++.h>
using namespace std;

struct Trie{    // Trie
    Trie* next[26];
    int num;    //the number of words prefixed with the current string
    Trie() {   // construction
       for(int i=0;i<26;i++) next[i] = NULL;
       num=0;
    }
};

Trie root;

void Insert(char str[]){    // insert str to the trie
    Trie *p = &root;
    for(int i=0;str[i];i++){    // process every character
        if(p->next[str[i]-¡®a¡¯]==NULL)    // if the character has no corresponding trie node
            p->next[str[i]-¡®a¡¯] = new Trie;    // create the trie node
        p = p->next[str[i]-¡®a¡¯];
        p->num++;
    }
}

int Find(char str[]){    //return the number of words prefixed with str
    Trie *p = &root;
    for(int i=0;str[i];i++){    // find the str in the trie
        if(p->next[str[i]-¡®a¡¯]==NULL) // find none
            return 0;
        p = p->next[str[i]-¡®a¡¯];
    }
    return p->num;
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
