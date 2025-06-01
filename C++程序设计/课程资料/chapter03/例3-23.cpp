#include <iostream>
using namespace std;

class Outer                                             //定义外部类Outer
{
public:
	class  Inner{                                      //定义内部类Inner
	private:
		int inner_n;
	public:
		//内部类成员函数set_outer_n()通过外部类引用参数访问外部类私有成员outer_n
		void set_outer_n(Outer &ref_outer)	{ ref_outer.outer_n = 10; }
		void set_inner_n() { inner_n = 100; }
		//内部类成员函数show()通过外部类引用参数访问外部类成员函数show()
		void show(Outer &ref_outer)
		{
			ref_outer.show();
			cout << "inner_n = " << inner_n << endl;
		}
	};                                                  //内部类定义结束
	//Inner inner_obj;                               //定义内部类对象inner
	//外部类成员函数
	void show() { cout << "outer_n = " << outer_n << endl; }
private:
	int outer_n;
};

int main(){
	Outer outer_obj;
	Outer::Inner inner_obj;

	inner_obj.set_inner_n();
	inner_obj.set_outer_n(outer_obj);
	inner_obj.show(outer_obj);

	return 0;
}
