#include <iostream>
#include <string.h>
using namespace std;




class Complex
{
private:
double x;
double y;
public:
Complex(double x = 0.0, double y = 0.0);
Complex & operator+=(const Complex &);
Complex & operator-=(const Complex &);
Complex & operator*=(const Complex &);
Complex & operator/=(const Complex &);
friend Complex operator+(const Complex &, const Complex &);
friend Complex operator-(const Complex &, const Complex &);
friend Complex operator*(const Complex &, const Complex &);
friend Complex operator/(const Complex &, const Complex &);
friend bool operator==(const Complex &, const Complex &);
friend bool operator!=(const Complex &, const Complex &);
friend ostream & operator<<(ostream &, const Complex &);
friend istream & operator>>(istream &, Complex &);
};

ostream & operator<<(ostream & stream, const Complex & c)
{
   stream<<c.x<<c.y;
   return stream;
}

istream & operator>>(istream & stream, Complex & c)
{
   stream>>c.x>>c.y;
   return stream;
}

bool operator!=(const Complex & c1, const Complex & c2)
{
    if(c1.x!=c2.x||c1.y!=c2.y)
    return true;
    else
    return false;
}

bool operator==(const Complex & c1, const Complex & c2)
{
   if(c1.x==c2.x&&c1.y==c2.y)
    return true;
    else
    return false;
}

Complex operator/(const Complex & c1, const Complex & c2)
{
   Complex c;
   c.x=c1.x/c2.x;
   c.y=c1.y/c2.y;
   return c;
}

Complex operator*(const Complex & c1, const Complex & c2)
{
    Complex c;
   c.x=c1.x*c2.x;
   c.y=c1.y*c2.y;
   return c;
}

Complex operator+(const Complex & c1, const Complex & c2)
{
    Complex c;
   c.x=c1.x+c2.x;
   c.y=c1.y+c2.y;
   return c;
}

Complex operator-(const Complex & c1, const Complex & c2)
{
    Complex c;
   c.x=c1.x-c2.x;
   c.y=c1.y-c2.y;
   return c;
}

Complex & Complex::operator+=(const Complex & c1)
{
     x=c1.x+x;
     y=c1.y+y;
     return *this;
}

Complex & Complex::operator-=(const Complex & c1)
{
     x=-c1.x+x;
     y=-c1.y+y;
     return *this;
}

Complex & Complex::operator*=(const Complex & c1)
{
     x=-c1.x*x;
     y=-c1.y*y;
     return *this;
}

Complex & Complex::operator/=(const Complex & c1)
{
     x=x/c1.x;
     y=y/c1.y;
     return *this;
}




int main()
{
Complex c1, c2;
cin >> c1 >> c2;
cout << "c1 = " << c1 << "\n" << "c2 = " << c2 << endl;
cout << "c1+c2 = " << c1 + c2 << endl;
cout << "c1-c2 = " << c1 - c2 << endl;
cout << "c1*c2 = " << c1 * c2 << endl;
cout << "c1/c2 = " << c1 / c2 << endl;
cout << (c1 += c2) << endl;
cout << (c1 -= c2) << endl;
cout << (c1 *= c2) << endl;
cout << (c1 /= c2) << endl;
cout << (c1 == c2) << " " << (c1 != c2) << endl;
return 0;
}
