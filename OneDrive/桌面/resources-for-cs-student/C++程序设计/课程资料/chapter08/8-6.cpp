#include <iostream>
#include <list>
using namespace std;

template<typename T>
void print(list<T> mylist)  //定义print()函数，输出list容器元素
{
	list<T>::iterator it;  //创建list列表的迭代器
	for (it = mylist.begin(); it != mylist.end(); it++)
		cout << *it << " ";
	cout << endl;
}

int main()
{
	list<int> lt1, lt2, lt3;
	lt1.push_back(12);
	lt1.push_back(6);
	lt1.push_back(32);
	lt2.push_front(45);
	lt2.push_front(9);
	lt3.push_back(100);
	lt3.push_back(2);
	lt3.push_back(11);
	cout << "lt1:";
	print(lt1);
	cout << "lt2:";
	print(lt2);
	cout << "lt3:";
	print(lt3);
	//调用splice()函数将lt2中的第一个元素插入到lt1末尾
	lt1.splice(lt1.end(), lt2, lt2.begin());
	cout << "lt1与lt2合并后,lt1: ";
	print(lt1);
	cout << "lt1与lt2合并后,lt2: ";
	print(lt2);
	//将lt3容器插入到lt1容器前面
	lt1.splice(lt1.begin(), lt3);
	cout << "lt1与lt3合并后，lt1: ";
	print(lt1);
	cout << "lt1与lt3合并后，lt3: ";
	print(lt3);
	system("pause");
	return 0;
}
