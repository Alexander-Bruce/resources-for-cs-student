#include <iostream>
#include <list>
#include <algorithm>
#include <functional>
#include <iterator>
#include <numeric>
using namespace std;
int main()
{
	char str[] = { 'a', 'b', 'c', 'd', 'e', 'f', 'g' };
	list<char> lt;
	lt.assign(str, str + sizeof(str) / sizeof(char));
	copy(lt.begin(), lt.end(), ostream_iterator<char>(cout, " "));
	cout << endl;

	list<char>::iterator it = lt.begin();
	advance(it, 5); //将迭代器向下移动5个单位
	cout << "将迭代器向下移动5个单位后，指向的元素为：" << *it << endl;
	cout << "the distance between begin and f is: " << distance(lt.begin(), it)
		<< endl;
	iter_swap(lt.begin(), it); //交换两个位置的元素
	cout << "交换两个位置的元素之后：";
	copy(lt.begin(), lt.end(), ostream_iterator<char>(cout, " "));
	cout << endl;

	system("pause");
	return 0;
}
