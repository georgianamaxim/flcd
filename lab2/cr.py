def greatest_common_divisor(a,b):
    r = a%b
    while r:
        a=b
        b=r
        r=a%b
    return b
def generators(n):
    generators_list = []
    generators_list.append(1)
    for elem in range(2,n):
        if greatest_common_divisor(n,elem) == 1:
            generators_list.append(elem)
    return generators_list

def generators_without_gcd(n):
    group_set = {i for i in range(0,n)}
    generators_list = []
    generators_list.append(1)
    for possible_generator in range(2,n):
        solutions = set()
        start = 0
        current = -1
        while current != start:
            if current == -1:
                current = (start + possible_generator)%n
            else:
                current = (current + possible_generator)%n
            solutions.add(current)
        if solutions == group_set:
            generators_list.append(possible_generator)
    return generators_list

def eulers_totient_function(n):
    count = 0
    for i in range(1,n):
        if greatest_common_divisor(n,i) == 1:
            count += 1
    return count

if __name__ == '__main__':
    generators_list1 = generators(10)
    generators_list2 = generators_without_gcd(10)
    generators_number = eulers_totient_function(10)
    assert (len(generators_list2) == generators_number)
    assert (generators_list1 == generators_list2)
    print("Number of generators is : " + str(generators_number))
    for i in generators_list1:
        print(i)