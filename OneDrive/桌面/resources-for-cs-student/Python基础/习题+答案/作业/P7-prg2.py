import os
path = str(input("Enter the path: "))
directory_list = os.listdir(path)
context = ""
for file in directory_list:
    if file.endswith(".txt"):
        file_path = os.path.join(path, file)
        context += open(file_path, encoding='utf-8').read()
targetPath = os.path.join(path, "all.txt")
file = open(targetPath, 'w', encoding='utf-8')
file.write(context)