class Grammar:
    def __init__(self, file):
        self.__file = file
        self.__gross_productions = []
        self.__grammar = self.read_grammar()
        self.__non_terminals = self.__grammar[0]
        self.__terminals = self.__grammar[1]
        self.__transactions = self.__grammar[2:]
        self.__start_symbol = self.__grammar[2][0]
        self.__productions = self.represent_productions()

    def read_grammar(self):
        grammar = []
        p = []

        with open(self.__file, "r") as f:
            line = f.readline()
            grammar.append(line.strip().split(" "))
            line = f.readline()
            grammar.append(line.strip().split(" "))
            line = f.readline()
            grammar.append(line.strip().split(" "))
            line = f.readline()
            while line != "":
                p.append(line.strip().split(" "))
                line = f.readline()
            self.__gross_productions = p

        return grammar

    def represent_productions(self):
        representation = {}
        for p in self.__gross_productions:
            if p[0] not in representation:
                representation[p[0]] = []
            representation[p[0]].append(p[1].split("#"))
        return representation

    def get_non_terminals(self):
        return self.__non_terminals

    def get_terminals(self):
        return self.__terminals

    def get_productions(self):
        return self.__productions

    def get_start_symbol(self):
        return self.__start_symbol

    def get_gross_productions(self):
        return self.__gross_productions


    def get_productions_by_non_terminals(self, non_terminal):
        return self.__productions[non_terminal]


