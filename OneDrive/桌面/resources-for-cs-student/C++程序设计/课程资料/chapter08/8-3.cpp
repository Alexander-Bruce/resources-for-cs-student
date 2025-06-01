#include <iostream>
#include <deque>
using namespace std;
int main()
{
	deque<int> d(10);  //创建一个deque容器

	//为容器赋值并输出容器中的元素
	for (int i = 0; i < 10; i++)
		d[i] = i;   //为容器赋值
	cout << "赋值后，输出容器的元素：" << endl;
	for (int i = 0; i < 10; i++)
		cout << d[i] << " ";
	cout << endl;

	//在头尾部插入元素
	d.push_front(99); //在头部插入33
	d.push_back(77);  //在尾部插入77

	//插入元素后再次输出
	cout << "在头尾部插入元素后：" << endl;
	for (int i = 0; i < 12; i++)   //插入元素后，现在有12个元素
		cout << d.at(i) << " ";
	cout << endl;

	//在头尾部删除元素
	d.pop_front();  //删除头部元素
	d.pop_back();  //删除尾元素
	d.erase(d.begin() + 3); //删除begin + 3位置的元素

	//删除元素后再次输出
	cout << "在头尾部删除元素后：" << endl;
	for (int i = 0; i < 9; i++)   //插入元素后，现在有9个元素
		cout << d.at(i) << " ";
	cout << endl;
	system("pause");
	return 0;
}
