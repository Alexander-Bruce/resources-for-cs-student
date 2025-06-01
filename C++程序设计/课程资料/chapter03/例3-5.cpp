#include <iostream>
using namespace std;

class Base{
protected:
	int n_base;
};

class Derive :public Base{
public:
	void disp_addr(){
		cout << "n_base addr:" << &n_base << endl;
		cout << "n_derive addr:" << &n_derive << endl;
	}
private:
	int n_derive;
};

int main(){
	Derive obj;
	obj.disp_addr();
	cout << "sizeof(obj) = " << sizeof(obj) << endl;
	
	return 0;
}
