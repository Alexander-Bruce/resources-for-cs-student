#include<iostream>
#include<algorithm>
#include<cmath>
#define SIZE 1024
using namespace std;
unsigned int board[SIZE][SIZE];
int ID = 0; // the number of L cards required
// row and column of a board, row and column of the black square, size of division
void ChessBoard(int tc, int tr, int x, int y, int size)
{
	// exit when the size is 1, that is, a square, because it can no longer be cut
    if (size == 1)
        return;
    // divided, half the size of the original, the board is cut into four parts of the same size
    int s = size / 2;
    int id = ++ID; // id of the card that the current board needs to cover
    
    //*************black square in the upper left corner
    if (x < tc+s && y < tr+s){
        ChessBoard(tc, tr, x, y, s); // notice that the size is halved
    }
    else{// not in the upper left corner, cover the bottom right square
        board[tc+s-1][tr+s-1]=id;
        ChessBoard(tc, tr, tc+s-1, tr+s-1, s);
    }

    //**************black square in the lower left corner
    if (x >= tc+s && y < tr+s){
        ChessBoard(tc+s, tr, x, y, s); // notice that the size is halved
    }
    else{// cover the up right square
        board[tc+s][tr+s-1]=id;
        ChessBoard(tc+s, tr, tc+s, tr+s-1, s);
    }
   
    //**************black square in upper right corner
    if (x < tc+s && y >= tr+s){
        ChessBoard(tc, tr+s, x, y, s); // notice that the size is halved
    }
    else{// cover the bottom left square
        board[tc+s-1][tr+s]=id;
        ChessBoard(tc, tr+s, tc+s-1, tr+s, s);
    }

    //**************black square in the lower right corner
    if (x >= tc+s && y >= tr+s){
        ChessBoard(tc+s, tr+s, x, y, s);// notice that the size is halved
    }
    else{ // cover the up left square
        board[tc+s][tr+s]=id;
        ChessBoard(tc+s, tr+s, tc+s, tr+s, s);
    }

}
int main(){
    int k, x, y, size;
    cin >> k >> x >> y;
    size = pow(2, k);
    ChessBoard(0, 0, x, y, size);
    cout<<ID<<endl;
    for(int i=0;i<size;i++){
        for(int j=0;j<size;j++){
            cout<<board[i][j]<<" ";
        }
        cout<<endl;
    }
    return 0;
}