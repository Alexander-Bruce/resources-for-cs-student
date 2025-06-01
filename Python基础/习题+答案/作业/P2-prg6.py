import datetime
name = input("Enter your name: ")
BirthYear = input("Enter your BirthYear: ")
year = datetime.datetime.now().year
age = year - int(BirthYear)
print("This is", name, " and is", age, " years old.")