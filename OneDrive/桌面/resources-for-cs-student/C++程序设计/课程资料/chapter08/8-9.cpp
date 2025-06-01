#include <iostream>
#include <list>
using namespace std;
int main()
{
	list<int> lt; //创建一个list列表容器
	for (int i = 0; i < 10; i++)
		lt.push_back(i + 1);  //向容器中插入元素
	list<int>::iterator it;  //定义一个list迭代器
	for (it = lt.begin(); it != lt.end(); it++)
		cout << *it << " ";
	cout << endl;
	list<int>::reverse_iterator itr(it); //将it转换为逆向迭代器
	for (itr = lt.rbegin(); itr != lt.rend(); itr++) //itr++是上行移动
		cout << *itr << " ";
	cout << endl;
	system("pause");
	return 0;
}
