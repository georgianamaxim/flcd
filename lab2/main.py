from hashtable import HashTable
INITIAL_CAPACITY = 2


if __name__ == '__main__':
    symbol_table = HashTable(INITIAL_CAPACITY)
    symbol_table.insert("abc")
    print(symbol_table.get("abc"))
    symbol_table.insert("cab")
    print(symbol_table.get("cab"))
    print(symbol_table)