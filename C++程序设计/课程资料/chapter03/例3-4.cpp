#include <iostream>
#include <string>
using namespace std;

class Animal{
public:
	void set_weight(int weight){ m_nWeightBase = weight; }
	int get_weight(){ return m_nWeightBase; }
	void set_age(int age){ m_nAgeBase = age; }
protected:
	int m_nAgeBase;
private:
	int m_nWeightBase;
};

class Cat :protected Animal{
public:
	Cat(string con_name) :m_strName(con_name){}
	void set_print_weight(){
		set_weight(6);
		cout << m_strName << " weight = " << get_weight() << endl;
	}
private:
	string m_strName;
};

class PersianCat :protected Cat{
public:
	PersianCat() :Cat("persian cat"){  }
	void persian_set_print_age(){
		set_age(5);
		cout << "PersianCat, age = " << m_nAgeBase << endl;
	}
};

int main(){
	PersianCat persian_cat;
	persian_cat.persian_set_print_age();
	
	return 0;
}
