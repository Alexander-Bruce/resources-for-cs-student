#include <iostream>
using namespace std;

class Person {
public:
 virtual void input();
 virtual void display();
private:
string name;
};

void Person::input()
{
    cin>>name;
}

void Person::display()
{
    cout<<name;
}

class Student : public Person
{
    private:
    string number;
    string sname;
    public:
    virtual void input();
 virtual void display();
};


void Student::input()
{
  cin>>number>>sname;
}

void Student::display()
{
  cout<<number<<sname;
}

int main()
{
Person * p;
p = new Person;
p->input();
p->display();
delete p;
p = new Student;
p->input();
p->display();
delete p;
return 0;
}
