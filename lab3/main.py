from hashtable import HashTable
from scanner import *
INITIAL_CAPACITY = 71


if __name__ == '__main__':
    file_name = input("Give file name : ")
    symbol_table = HashTable(71)
    pif = HashTable(71)
    with open(file_name, "r") as f:
        nr = 0
        for line in f:
            for token in detect_tokens(line[0:-1]):
                if token in separators + operators + reserved_words:
                    pif.insert(str(cod[token]), -1)
                elif is_identifier(token):
                    index = symbol_table.insert(token, 0)
                    pif.insert(str(cod['identifier']), index)
                elif is_constant(token):
                    index = symbol_table.insert(token)
                    pif.insert(str(cod['constant']), index)
                else:
                    raise Exception('Unknown token ' + token + ' at line ' + str(nr))
            nr += 1

    print("lexically correct")

    with open("ST.out", "w") as f:
        f.write(str(symbol_table))

    with open("PIF.out", "w") as f:
        f.write(str(pif))
    print("SYMBOL TABLE:")
    print(symbol_table)

    print("PIF:")
    print(pif)
