from hashtable import HashTable
INITIAL_CAPACITY = 71


if __name__ == '__main__':
    file_name = input("Give file name : ")

    with open(file_name, "r") as f:
        for line in f:

