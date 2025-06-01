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

class Cat :private Animal{
public:
	Cat(string con_name) :m_strName(con_name){}
	void set_print_age(){
		set_age(5);
		cout << m_strName << " age = " << m_nAgeBase << endl;
	}
	void set_print_weight(){
		set_weight(6);
		cout << m_strName << ", weight = " << get_weight() << endl;
	}
private:
	string m_strName;
};

int main(){
	Cat cat("Persian"); 
	cat.set_print_age();
	cat.set_print_weight();
	
	return 0;
}
