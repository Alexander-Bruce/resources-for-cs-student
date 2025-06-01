#include <iostream>
#include <deque>
#include <algorithm>
#include <iterator>
using namespace std;

int main()
{
	int arr[] = { 1, 2, 3, 4, 5, 6 };
	deque<int> d1; //创建一个空的deque<int>对象
	cout << "d1(1): ";
	copy(arr, arr + 6, back_inserter(d1)); //将数组arr中的元素插入到d1容器中
	//将d1容器中的元素用输出流迭代器输出到屏幕
	copy(d1.begin(), d1.end(), ostream_iterator<int>(cout, " "));
	cout << endl;

	cout << "d1(2): ";
	front_inserter(d1) = 11;  //前端插入元素
	front_inserter(d1) = 22;
	copy(d1.begin(), d1.end(), ostream_iterator<int>(cout, " ")); //再次输入
	cout << endl;

	cout << "d1(3): ";
	//将d1容器中的所有元素再次插入到d1容器的前端
	copy(d1.begin(), d1.end(), front_inserter(d1));
	copy(d1.begin(), d1.end(), ostream_iterator<int>(cout, " "));
	cout << endl;

	cout << "d1(4): ";
	inserter(d1, d1.end()) = 33; //将33插入在d1容器的尾部
	inserter(d1, d1.end()) = 44;
	copy(d1.begin(), d1.end(), ostream_iterator<int>(cout, " "));
	cout << endl;

	deque<int> d2; //创建一个空的deque容器d2
	cout << "d2: ";
	//将d1容器的元素插入到d2容器的头部
	copy(d1.begin(), d1.end(), inserter(d2, d2.begin()));
	copy(d2.begin(), d2.end(), ostream_iterator<int>(cout, " "));
	cout << endl;
	system("pause");
	return 0;
}
