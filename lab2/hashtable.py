class Node:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.next = None


class HashTable:
    def __init__(self, capacity):
        self.__capacity = capacity
        self.__size = 0
        self.__list = [None] * self.__capacity

    def hash(self, key):
        """Hash function"""
        hash_sum = 0

        for idx, c in enumerate(key):
            hash_sum += (idx + len(key)) ** ord(c)
            hash_sum = hash_sum % self.__capacity

        return hash_sum

    def insert(self, key, value):
        """Add a value to our array by its key"""
        # increment the size
        self.__size += 1

        # compute the index of the key
        index = self.hash(key)

        # get the node corresponding to the index
        node = self.__list[index]
        # 3. If bucket is empty:

        if node is None:
            # if node does not exist, create it
            self.__list[index] = Node(key, value)
            return index

        # there is a collision, we need to iterate to the end of the ll at that index

        previous = node
        while node is not None and node.key != key:
            previous = node
            node = node.next

        # insert the new node to the end of the list
        if previous.key == key:
            previous.value = value
            return index

        else:
            previous.next = Node(key, value)

        return index

    def get(self, key):
        """Return the corresponding value of the parameter key"""
        # get hash
        index = self.hash(key)

        current_node = self.__list[index]
        # traverse the list from the current node
        while current_node is not None and current_node.key != key:
            current_node = current_node.next

        if current_node is None:
            # the key was not found
            return None
        else:
            # it was found, return the corresponding value
            return current_node.value

    def remove(self, key):

        # get hash
        index = self.hash(key)

        current_node = self.__list[index]
        previous_node = None

        # get to the corresponding node
        while current_node is not None and current_node.key != key:
            previous_node = current_node
            current_node = current_node.next

        if current_node is None:
            # the key was not found
            return None
        else:
            self.__size -= 1
            result = current_node.value

            if previous_node is None:
                current_node = None
            else:
                previous_node.next = previous_node.next.next

            return result