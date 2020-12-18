from grammar import Grammar


class LrParser:
    def __init__(self):
        self.__dotted_productions = {"S": [[]]}
        self.__grammar = Grammar("g1.txt")
        self.dotted_productions = {}
        self.dot_maker()
        self.__initial_closure = {}
        self.__canonical_collection = {}
        self.__parents = {}
        self.__actions = {}
        self.__states = []

    def dot_maker(self):
        for non_terminal in self.__grammar.get_productions().keys():
            self.__dotted_productions[non_terminal] = []
            for w in self.__grammar.get_productions_by_non_terminals(non_terminal):
                e = ["."] + w
                self.__dotted_productions[non_terminal].append(e)

    def closure(self, closure_map, transition_map, transition_value):
        i_dot = transition_value.index(".") + 1
        if len(transition_value) != i_dot:
            after = transition_value[i_dot]
            if after in self.__grammar.get_non_terminals():
                if after not in closure_map:
                    closure_map[after] = transition_map[after]
                else:
                    closure_map[after] += transition_map[after]
                for trans in transition_map[after]:
                    self.closure(closure_map, transition_map, trans)
        else:
            return

    def shift_dot(self, transition):
        i_dot = transition.index(".")
        reminder = []
        to_return = []
        if not len(transition) > i_dot + 1:
            print("Shift back?")
            return
        if len(transition) > i_dot + 2:
            [reminder.append(transition[i])for i in range(i_dot + 2, len(transition))]
        [to_return.append(transition[i]) for i in range(0, i_dot)]
        to_return.append(transition[i_dot + 1])
        to_return.append(".")
        to_return.append(reminder)
        return to_return

    def conflict(self, state, states):
        transitions = []
        i = states.index(state)
        for key in state:
            for transition in state[key]:
                transitions.append(transition)
        conflicted = None
        for transition in transitions:
            index = transition.index(".")
            if index == len(transition) - 1:
                continue
            for n in transition[index + 1:]:
                if n in self.__grammar.get_terminals():
                    conflicted = transition[index]
                print(f"conflict state {i}. {state} at column {conflicted}")
                exit(1)
        raise Exception(f"conflict state {i}. {state} unknown column")

    def get_reduced(self, states):
        r = {}
        for state in states:
            k = list(state.keys())[0]
            if len(state[k]) and len(state[k][0]) and state[k][0][-1] == ".":
                if len(state) > 1 or len(state[k]) > 1:
                    self.conflict(state, states)
                r[states.index(state)] = state
        return r

    def canonical_collection(self):
        parents = {}
        actions = {}
        states = []
        queue = [{"state": self.__initial_closure}]
        while len(queue) > 0:
            p = -1
            key = "-1"
            s = queue.pop(0)
            if s not in states:
                states.append(s)
                l = len(states) - 1
                parents[l] = {"parent_index": p, "before_dot": key}
                print(f"state {l}")
                for k in s:
                    print(f"{k}: {s[k]}")
                # print(s)
                for k in s:
                    for trans_val in s[k]:
                        if len(trans_val) > trans_val.index(".") + 1:
                            shifted_transition = self.shift_dot(trans_val)
                            closure_map = {k: [shifted_transition]}
                            self.closure(closure_map, shifted_transition)
                            queue.append({
                                "state": closure_map,
                                "parent_key": shifted_transition[shifted_transition.index(".") - 1],
                                "parent": l,
                            })
            else:
                if p in actions:
                    actions[p][key] = states.index(s)
                else:
                    actions[p] = {key: states.index(s)}
        reduced = self.get_reduced(states)
        for key in reduced:
            reduced_keys = list(reduced[key].keys())
            if reduced_keys[0] != "S'":
                trans = reduced_keys + reduced[key][reduced_keys[0]][0][:-1]
                reduce_index = self.__grammar.get_gross_productions().index(trans) + 1
                actions[key] = {terminal: f"r{reduce_index}" for terminal in self.__grammar.get_terminals()}
                actions[key]["$"] = f"r{reduce_index}"
            else:
                actions[key] = {"$": "accept"}

        del parents[0]
        for key in parents.keys():
            p = parents[key]
            if p["parent_index"] in actions:
                actions[p["parent_index"]][p["before_dot"]] = key
            else:
                actions[p["parent_index"]] = {p["before_dot"]: key}
        print(actions)
        print(states)
        table = {f"S{i}": actions[i] for i in range(len(states))}
        self.__canonical_collection = table
        self.__actions = actions
        self.__parents = parents
        self.__states = states
        print(table)

    def parse(self, s):
        self.canonical_collection()
        print(s)
        string = s + "$"
        queue = [c for c in string]
        working_stack = ["$", 0]
        output_band = []
        status = 1
        rows = [["".join([str(element) for element in working_stack]),
                 "".join([str(element) for element in queue]),
                 ",".join([str(element) for element in output_band])]]
        while status == 1:
            pop = queue.pop(0) if queue[0] != "$" else "$"

            last_working_stack_element = working_stack[-1]
            intersection = self.__actions[last_working_stack_element][pop]
            if intersection == "accept":
                status = 0
                break
            if type(intersection) == int:
                # shift
                working_stack.append(pop)
                working_stack.append(intersection)
            else:
                # reduce
                production_index = int(intersection.replace("r", ""))
                output_band.insert(0, production_index)
                reduce_transition = self.__grammar.get_gross_productions()[production_index - 1]
                non_terminal = reduce_transition[0]
                replaceable_string = reduce_transition[1:]
                working_stack_length = len(working_stack)
                replaceable_string_length = len(replaceable_string)
                without_final_partition = working_stack[:working_stack_length - (2 * replaceable_string_length)]
                working_stack = without_final_partition
                state_index = working_stack[-1]
                working_stack.append(non_terminal)
                goto = self.__actions[state_index][non_terminal]
                working_stack.append(goto)
            rows.append(["".join([str(element) for element in working_stack]),
                 "".join([str(element) for element in queue]),
                 ",".join([str(element) for element in output_band])])
        if status == 0:
            print(output_band)
            rows.append(["accepted",
                         "".join([str(element) for element in queue]),
                         ",".join([str(element) for element in output_band])])
            print("Accepted")
            print(rows)
            with open("out.txt", "w") as f:
                f.write("Work stack         Input stack         Output band\n")
                f.write(str(rows))

        if status == 2:
            print("Error")

    def get_terminals(self):
        print(self.__grammar.get_terminals())

    def get_non_terminals(self):
        print(self.__grammar.get_non_terminals())

    def get_productions(self):
        print(self.__grammar.get_productions())

    def get_productions_by_non_terminal(self, non_terminal):
        print(self.__grammar.get_productions_by_non_terminals(non_terminal))
