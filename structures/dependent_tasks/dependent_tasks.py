#!/usr/bin/python


class Node:
    """Node in a graph"""

    def __init__(self, idx):
        self.idx = idx
        self.started = False
        self.marked = False
        self.dependencies = []

    def __hash__(self):
        return hash(self.idx)

    def __eq__(self, other):
        return self.idx == other.idx

    def __repr__(self):
        return str(self)

    def __str__(self):
        return 'Node ' + str(self.idx)

    def addDependency(self, node):
        self.dependencies.append(node)


def read_input_file(fileName):
    f = open(fileName, 'rU')
    n = int(f.readline())
    root = Node(-1)
    root.dependencies = [Node(i) for i in range(n)]
    for line in f.readlines():
        i, j = [int(s) for s in line.split()]
        root.dependencies[i].addDependency(root.dependencies[j])
    return root


def deep_first_search(node):
    node.marked = True
    result = []
    for d in node.dependencies:
        if not d.started:
            if d.marked:
                raise Exception('Loop detected!')
            result += deep_first_search(d)
    node.started = True
    result.append(node)
    return result


def main():
    root = read_input_file('tests/input04.txt')
    chain = deep_first_search(root)
    chain.pop()  # remove root
    print chain


if __name__ == '__main__':
    main()
