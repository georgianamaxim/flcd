from hashtable import HashTable
INITIAL_CAPACITY = 71


if __name__ == '__main__':
    symbol_table = HashTable(INITIAL_CAPACITY)
    symbol_table.insert("a", 2)
    print(symbol_table.get("a"))
    symbol_table.insert("a", 3)
    print(symbol_table.get("a"))