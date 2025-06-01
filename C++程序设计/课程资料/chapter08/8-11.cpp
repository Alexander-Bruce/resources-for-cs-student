#include <iostream>
#include <deque>
#include <algorithm>
#include <iterator>
using namespace std;
int main()
{
	cout << "please input data:" << endl;
	deque<int> d;  //创建一个deque<int>容器d
	istream_iterator<int> it(cin); //定义一个输入流迭代器，从cin流中读取数据
	istream_iterator<int> itf; //默认输入流迭代器
	while (it != itf) //比较it与itf迭代器，判断读取操作是否合法
	{
		*it; //相当于cin>>
		d.push_back(*it); //将读取到的数据存储到d容器中
		++it; //迭代器向后移动
	}
	// 用输出流迭代器输出容器中数据
	copy(d.begin(), d.end(), ostream_iterator<int>(cout, " "));
	cout << endl;
	system("pause");
	return 0;
}
