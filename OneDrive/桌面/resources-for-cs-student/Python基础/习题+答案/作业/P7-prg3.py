import os
import shutil
import zipfile
path = input("Enter the path: ")
dictionary_list = os.listdir(path)
os.chdir(path)
os.makedirs("python_code", exist_ok=True)
destination_path = os.path.join(path, "python_code")
for file in dictionary_list:
    if file.endswith(".py"):
        file_path = os.path.join(path, file)
        shutil.copyfile(file_path, os.path.join(destination_path, file))
zip_file_name = "python_code.zip"
zip_file_path = os.path.join(path, zip_file_name)
with zipfile.ZipFile(zip_file_path, 'w') as zipf:
    for root, dirs, files in os.walk(destination_path):
        for file in files:
            file_path = os.path.join(root, file)
            zipf.write(file_path, arcname=os.path.relpath(file_path, destination_path))

print("Files copied successfully!")


