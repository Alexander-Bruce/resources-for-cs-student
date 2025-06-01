#include <iostream>
using namespace std;

class Date{
public:
	Date(int con_year, int con_month, int con_day);
	void print_date(){ cout << m_nYear << "-" << m_nMonth << "-" << m_nDay << endl; }
private:
	int m_nYear, m_nMonth, m_nDay;
};

Date::Date(int con_year, int con_month, int con_day){
	cout << "Date constructor!" << endl;
	m_nYear = con_year;
	m_nMonth = con_month;
	m_nDay = con_day;
}

class Bird{
public:
	Bird(int fh){
		cout << "Bird constructor!" << endl;
		m_nFlightAltitude = fh;
	}
private:
	int m_nFlightAltitude;
};

class Fish{
public:
	Fish(int speed){
		cout << "Fish constructor!" << endl;
		m_nSwimSpeed = speed;
	}
private:
	int m_nSwimSpeed;
};

class WaterBird :public Bird, public Fish{
public:
	WaterBird(int year, int month, int day, int fh, int speed) :Bird(fh), Fish(speed), m_iBirthday(year, month, day){
		cout << "WaterBird constructor!" << endl;
	}
	void print_birthday(){
		m_iBirthday.print_date();
	}
private:
	Date m_iBirthday;
};

int main()
{
	WaterBird waterbird(2010, 5, 1, 20, 30);
	cout << "waterbird birthday:";
	waterbird.print_birthday();

	return 0;
}
