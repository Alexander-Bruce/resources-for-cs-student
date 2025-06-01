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
	list<int> lt1, lt2;
	lt1.push_back(12);
	lt1.push_back(6);
	lt1.push_back(32);
	lt2.push_front(45);
	lt2.push_front(9);
	cout << "lt1:";
	print(lt1);
	cout << "lt2:";
	print(lt2);
	//对lt1容器排序
	lt1.sort();
	cout << "lt1排序之后：";
	print(lt1);
	//合并两个容器
	lt1.merge(lt2);
	cout << "合并两个容器后：";
	print(lt1);
	system("pause");
	return 0;
}
