#include <iostream>
#include <vector>
#include <algorithm>
#include <functional>
#include <iterator>
#include <numeric>
using namespace std;
template<typename T>
class Multi  //类模板
{
private:
	T value;
public:
	Multi(const T& v) :value(v){}  //构造函数
	void operator()(T& elem) const{ elem *= value; } //重载()运算符
};
void print(int elem) //打印元素
{
	cout << elem << " ";
}
int main()
{
	int arr[] = { 21, 4, 55, 22, 46, 79, 9, 5, 78, 34, 100 };
	vector<int> v;
	v.assign(arr, arr + sizeof(arr) / sizeof(int)); //用数组给v容器赋值

	//调用for_each()函数将容器中每个元素都乘以2
	for_each(v.begin(), v.end(), Multi<int>(2));

	//调用copy()构造函数将容器中元素输出
	copy(v.begin(), v.end(), ostream_iterator<int>(cout, " "));	cout << endl;

	//调用find()算法查找容器中是否存在值为200的元素
	vector<int>::iterator it = find(v.begin(), v.end(), 200);
	if (*it == 200)
		cout << "容器中有值为200的元素" << endl;
	else
		cout << "容器中不存在值为200的元素" << endl;

	sort(v.begin(), v.end()); //调用sort()算法将容器中元素从小到大排列
	cout << "排序之后：" << endl;
	copy(v.begin(), v.end(), ostream_iterator<int>(cout, " "));
	cout << endl;
	int sum = accumulate(v.begin(), v.end(), 0); //累加容器中元素
	cout << "sum = " << sum << endl;
	system("pause");
	return 0;
}
