#include <iostream>
using namespace std;

template<typename T>
T add(T t1, T t2){
	return t1 + t2;
}

int main()
{
	cout << add(1, 2) << endl;
	cout << add(1.2, 3.4) << endl;
	system("pause");
	return 0;
}
