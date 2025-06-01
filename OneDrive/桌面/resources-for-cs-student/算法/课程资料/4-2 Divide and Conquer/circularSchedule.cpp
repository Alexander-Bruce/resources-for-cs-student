#include <iostream>
using namespace std;

const int maxNum = 1 << 10;
int table[maxNum][maxNum];

void circularSchedule(int row, int column, int n) {
    // end of recursion
    if(n == 1) {
        return ;
    }
    // 2^k*2^k -> four 2^(k-1)*2^(k-1)
    int half = n / 2;  // half = 2^k / 2
    // given the upper left,
    // the lower right = the upper left
    // the upper right = the lower left = the upper left + half
    table[row + half][column + half] = table[row][column];
    table[row][column + half] = table[row + half][column] = table[row][column] + half;
    // recursion
    circularSchedule(row, column, half);  // upper left
    circularSchedule(row, column + half, half); // upper right
    circularSchedule(row + half, column, half);  // lower left
    circularSchedule(row + half, column + half, half); // lower right
}

int main() {
    // n=2^k
    int n;
    while(true) {
        cin >> n;
        if(!n) {
            break;
        }
        // initialize table[0][0]
        table[0][0] = 1;
        circularSchedule(0, 0, n);
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                cout << table[i][j] << "\t";
            }
            cout << endl << endl << endl;
        }

    }
    return 0;
}
