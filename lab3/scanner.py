import re
from specifications import *


def is_identifier(token):
    return re.match(r'^[a-zA-Z]([a-zA-Z]|[0-9]|_){,7}$', token) is not None


def is_constant(token):
    return re.match('^(0|[\+\-]?[1-9][0-9]*)$|^\'.\'$|^\".*\"$', token) is not None


def is_part_of_operator(token):
    for op in operators:
        if token in op:
            return True
    return False


def detect_tokens(line):
    i = 0
    tokens = []
    current_token = ''
    while i < len(line):
        if line[i] == ' ' or line[i] == '\n':
            if current_token:
                tokens.append(current_token)
            i += 1
            current_token = ''

        if line[i] == '-':
            if line[i+1] != ' ':
                current_token += line[i]
                i+=1

        if line[i] in separators:
            if current_token:
                tokens.append(current_token)
            current_token = line[i]
            tokens.append(current_token)
            i += 1
            current_token = ''

        elif line[i] == '"':
            if current_token:
                tokens.append(current_token)
            count = 0
            while i < len(line) and count < 2:
                if line[i] == '"' and line[i - 1] != "\\":
                    count += 1
                current_token += line[i]
                i += 1
            tokens.append(current_token)
            current_token = ''

        elif is_part_of_operator(line[i]):
            while i < len(line) and is_part_of_operator(line[i]):
                current_token += line[i]
                i += 1
            tokens.append(current_token)
            current_token = ''

        else:

            current_token += line[i]
            i += 1
    if current_token:
        tokens.append(current_token)

    return tokens
