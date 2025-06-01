#include <iostream>
#include <set>
#include <functional>
using namespace std;
int main()
{
	set<int, greater<int>> s;  //创建一个set容器，元素按降序排列
	multiset<char> ms;  //创建一个multiset容器
	cout << "s能容纳的最大元素数量" << s.max_size() << endl;
	cout << "ms能容纳的最大元素数量" << ms.max_size() << endl;
	//向s中插入元素
	pair<set<int>::iterator, bool> ps;
	ps = s.insert(12);
	if (ps.second == true)
		cout << "insert success" << endl;
	s.insert(39);
	s.insert(32);
	s.insert(26);
	//向ms中插入元素
	ms.insert('a');
	ms.insert('z');
	ms.insert('T');
	ms.insert('u');
	ms.insert('u');
	//输出两个容器中的元素
	set<int>::iterator its; //创建s容器的迭代器，用于获取元素
	cout << "s容器中元素：";
	for (its = s.begin(); its != s.end(); its++)
		cout << *its << " ";
	cout << endl;
	multiset<char>::iterator itms;  //创建ms容器的迭代器
	cout << "ms容器中元素：";
	for (itms = ms.begin(); itms != ms.end(); itms++)
		cout << *itms << " ";
	cout << endl;

	//查找两个容器中头尾元素
	cout << "s头元素： " << *s.begin() << endl;
	cout << "ms尾元素： " << *(--ms.end()) << endl;
	//查找ms容器中u元素出现的次数
	cout << "ms容器中u元素出现的次数：" << ms.count('u') << endl;
	system("pause");
	return 0;
}
