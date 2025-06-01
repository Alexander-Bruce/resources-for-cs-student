#include <iostream>
#include <map>
#include <functional>
#include <string>
using namespace std;
//定义printm()函数输出map容器元素
void printm(map<char, double> mymap)
{
	pair<char, double> p;  //创建pair对象，map中元素是成对的，也要成对输出
	map<char, double>::iterator it; //定义迭代器
	for (it = mymap.begin(); it != mymap.end(); it++)
	{
		p = (pair<char, double>)*it; //将迭代器指向的一对元素存放到p中
		cout << p.first << "->" << p.second << endl; //输出一对元素
	}
}

//定义printmt()函数输出multimap容器
void printmt(multimap<int, string> mymul)
{
	pair<int, string> p;
	multimap<int, string>::iterator it;
	for (it = mymul.begin(); it != mymul.end(); it++)
	{
		p = (pair<int, string>)*it;
		cout << p.first << "->" << p.second << endl;
	}
}

int main()
{
	map<char, double> m;   //创建一个map容器
	//向容器m中插入元素
	m['a'] = 1.2;
	m['b'] = 3.6;
	m['c'] = 6.4;
	m['d'] = 0.8;
	m['e'] = 5.3;
	m['f'] = 3.6;

	cout << "map: " << endl;
	printm(m);
	cout << "map中key = a的值： " << m.at('a') << endl;
	cout << "map中key = f的元素出现次数： " << m.count('f') << endl;

	multimap<int, string> mt;  //创建一个multimap容器
	//向容器mt中插入元素
	mt.insert(pair<int, string>(1, "chuan"));
	mt.insert(make_pair(1, "zhi"));
	mt.insert(multimap<int, string>::value_type(3, "bo"));
	mt.insert(multimap<int, string>::value_type(4, "ke"));

	cout << endl << "multimap: " << endl;
	printmt(mt);
	cout << "multimap头部元素： ";
	pair<int, string> p;
	p = (pair<int, string>)*mt.begin();
	cout << p.first << "->" << p.second << endl;
	cout << "multimap尾部元素： ";
	p = (pair<int, string>)*(--mt.end());
	cout << p.first << "->" << p.second << endl;
	system("pause");
	return 0;
}
