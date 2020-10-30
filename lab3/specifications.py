separators = ['{', '}','[', ']', '(', ')', ';', ',']
operators = ['+', '-', '*', '/', '<', '<=', '=', '>=', '>', '>>>', '<<<', '=', '!=']
reserved_words = ['is', 'integer', 'condition', 'then', 'else', 'character', 'charSeq', 'read', 'display','vect', 'forloop']
all = separators + operators + reserved_words
cod = dict([(all[i], i) for i in range(len(all))])
cod["constant"] = -1
cod["identifier"] = -2
