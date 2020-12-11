class PIF:
    def __init__(self):
        self.__list = []

    def insert(self, cod, id):
        self.__list.append((cod, id))

    def __str__(self):
        return str(self.__list)
