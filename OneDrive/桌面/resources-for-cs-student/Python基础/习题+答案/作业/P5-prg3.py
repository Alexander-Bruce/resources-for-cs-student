def remove_duplicates(lst):
    return list(set(lst))

if __name__ == "__main__":
    input_str = input("Enter a list of numbers separated by spaces: ")
    input_list = [int(x) for x in input_str.split()] 
    unique_list = remove_duplicates(input_list)
    print("Original list:", input_list)
    print("List with duplicates removed:", unique_list)
