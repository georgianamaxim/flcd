from tabulate import tabulate
from lr_parser import Lr0Parser


def showMenu():
    lr0 = Lr0Parser("g1.txt")

    print("""
    1 - print terminals
    2 - print non terminals
    3 - print productions
    4 - print productions for a given non terminal
    5 - canonical collection
    6 - parse string
    0 - exit
    """)
    menu = {
        "1": lambda: lr0.printData(1),
        "2": lambda: lr0.printData(2),
        "3": lambda: lr0.printData(3),
        "4": lambda: lr0.printProduction(input("Please give a non terminal: ")),
        "5": lambda: lr0.canonicalCollection(),
        "6": lambda: lr0.parseString(input("Please give a string: ")),
        "0": exit
    }
    while True:
        option = input("Choose an option:")
        if option in menu:
            menu[option]()
        else:
            print("Wrong option")


if __name__ == '__main__':
    showMenu()