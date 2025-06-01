def ascii_list(s):
    ascii_codes = []
    for char in s:
        ascii_codes.append(ord(char)) 
    return ascii_codes

if __name__ == "__main__":
    input_str = input("Enter a string: ")
    ascii_codes = ascii_list(input_str)
    print("ASCII codes of each character:", ascii_codes)
