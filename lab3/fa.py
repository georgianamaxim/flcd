class FiniteAutomata():
    def __init__(self):
        self.__set_of_states = []
        self.__alphabet = []
        self.__final_state = []
        self.__initial_state = []
        self.__transitions = []
        self.read_fa()

    def read_fa(self):
        with open("fa.txt", "r") as f:
            line = f.readline()
            self.__set_of_states = [value.strip() for value in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]
            line = f.readline()
            self.__alphabet = [value.strip() for value in line.strip().split('=')[1].strip()[1:-1].strip().split(',')]
            line = f.readline()
            self.__initial_state = [value.strip() for value in line.strip().split('=')[1].strip()[1:-1].strip().split(',')][0]
            self.__final_state = [value.strip() for value in line.strip().split('=')[1].strip()[1:-1].strip().split(',')][1]
            while line!='':
                line = f.readline()
                first = line.strip().split('d')[1].split('(')[0]


    def parse_transitions(self):





