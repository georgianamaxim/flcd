import re


class FiniteAutomata(object):
    def __init__(self):
        self.__set_of_states = []
        self.__alphabet = []
        self.__initial_state = ""
        self.__final_states = []
        self.__transitions = {}
        self.read_fa()

    def read_fa(self):
        with open("fa.txt", "r") as f:
            line = f.readline()
            self.__set_of_states = [value.strip() for value in
                                    line.strip().split('=')[1].strip()[1:-1].strip().split(',')]
            line = f.readline()
            self.__alphabet = [value.strip() for value in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]
            line = f.readline()
            self.__initial_state = \
                [value.strip() for value in line.strip().split('=')][1]
            line = f.readline().strip()
            self.__final_states = \
                [value.strip() for value in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]
            line = f.readline().strip()
            while line != "":
                reg = '|'.join(map(re.escape,  ["(", ")", "="]))
                tokens = re.split(reg, line)
                first = tokens[1].split(",")
                trans_key = (first[0], first[1])

                if trans_key in self.__transitions.keys():
                    self.__transitions[trans_key].append(tokens[3])
                else:
                    self.__transitions[trans_key] = [tokens[3]]

                line = f.readline().strip()

    def get_states(self, transitions):
        states = []
        for t in transitions:
            states.append(t[0])
        return states

    def is_dfa(self):
        for trans in self.__transitions:
            if len(self.__transitions[trans]) > 1:
                return False
        return True

    def get_set_of_states(self):
        return self.__set_of_states

    def get_alphabet(self):
        return self.__alphabet

    def get_final_state(self):
        return self.__final_state

    def get_initial_state(self):
        return self.__initial_state

    def get_transitions(self):
        return self.__transitions

    def get_set_of_final_states(self):
        return self.__final_states

    def is_accepted(self, seq, current_state):
        if self.is_dfa():
            accepted = False
            seq_elems = list(seq)
            if len(seq_elems) > 1 and current_state in self.__final_states and current_state not in self.get_states(self.__transitions):
                return False
            for tr in self.__transitions.keys():
                if tr[0] == current_state and tr[1] == seq_elems[0]:
                    if len(seq_elems) == 1:
                        for transition in self.__transitions[tr]:
                            if transition in self.__final_states:
                                return True
                        return False
                    else:
                        for _ in self.__transitions[tr]:
                            accepted = self.is_accepted(seq[1:], self.__transitions[tr][0])
                            if accepted:
                                break
            return accepted


if __name__ == '__main__':
    fa = FiniteAutomata()
    fa.read_fa()
    ans = True
    while ans:
        print("""
        1.Set Of States
        2.Alphabet
        3.Initial State
        4.Set of final states
        5.Exit/Quit
        """)
        ans = input(">>")
        if ans == "1":
            print(fa.get_set_of_states())
        elif ans == "2":
            print(fa.get_alphabet())
        elif ans == "3":
            print(fa.get_initial_state())
        elif ans == "4":
            print(fa.get_transitions())
        elif ans == "5":
            ans = False
            break
        elif ans != "":
            print("\n Not Valid Choice Try again")






