from hashtable import HashTable
from scanner import *
from pif import *
from lr_parser import LrParser
INITIAL_CAPACITY = 71


def print_menu():
    print("1. terminals")
    print("2. non terminals")
    print("3. productions")
    print("4. productions by a non terminal")
    print("5. canonical collection")
    print("6. parse string")
    print("7. exit")

if __name__ == '__main__':
    # file_name = input("Give file name : ")
    # symbol_table = HashTable(71)
    # pif = PIF()
    # with open(file_name, "r") as f:
    #     nr = 0
    #     for line in f:
    #         for token in detect_tokens(line[0:-1]):
    #             print(token)
    #             if token in separators + operators + reserved_words:
    #                 pif.insert(str(cod[token]), -1)
    #             elif is_identifier(token):
    #                 index = symbol_table.insert(token, 0)
    #                 pif.insert(str(cod['identifier']), index)
    #             elif is_constant(token):
    #                 index = symbol_table.insert(token)
    #                 pif.insert(str(cod['constant']), index)
    #                 print(token)
    #             else:
    #                 raise Exception('Unknown token ' + token + ' at line ' + str(nr))
    #         nr += 1
    #
    # print("lexically correct")
    #
    # with open("ST.out", "w") as f:
    #     f.write("Hash Table\n")
    #     f.write(str(symbol_table))
    #
    # with open("PIF.out", "w") as f:
    #     f.write(str(pif))

    parser = LrParser()
    while True:
        print_menu()
        command = input(">>")
        if command == "1":
            parser.get_terminals()
        elif command == "2":
            parser.get_non_terminals()
        elif command == "3":
            parser.get_productions()
        elif command == "4":
            nt = input("non terminal : ")
            parser.get_productions_by_non_terminal(nt)
        elif command == "5":
            parser.canonical_collection()
        elif command == "6":
            s = input("string : ")
            parser.parse(s)
        elif command == "8":
            break
