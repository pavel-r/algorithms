#!/usr/bin/python

import sys
import os
from collections import deque
from functools import total_ordering


@total_ordering
class Path:
    """Path in a tree"""

    def __init__(self, cost, nodes):
        self.cost = cost
        self.nodes = nodes

    def __add__(self, other):
        cost = self.cost + other.cost
        nodes = self.nodes + other.nodes
        return Path(cost, nodes)

    def __hash__(self):
        return hash(self.cost)

    def __eq__(self, other):
        return self.cost == other.cost

    def __lt__(self, other):
        return self.cost < other.cost

    def __repr__(self):
        return '%s\n%s' % (self.cost, ' '.join([repr(n) for n in self.nodes]))


class Tree:
    """Simple tree implementation"""

    def __init__(self, idx, bribe):
        self.idx = idx
        self.bribe = bribe
        self.children = []

    def find_child(self, idx):  # TODO: add lambda test
        if self.idx == idx:
            return self
        for child in self.children:
            found_child = child.find_child(idx)
            if found_child:
                return found_child
        return

    def add_child(self, child):
        self.children.append(child)

    def print_children(self):
        print ('%s (%s):' % (self.idx, self.bribe)) + ', '.join([str(c.idx) for c in self.children])
        for child in self.children:
            child.print_children()

    def get_min_cost_path(self):
        paths = [c.get_min_cost_path() for c in self.children]
        if paths:
            return Path(self.bribe, [self.idx]) + min(paths)
        else:
            return Path(self.bribe, [self.idx])


def build_tree(filename):
    f = open(filename, 'rU')
    n = int(f.readline())
    nodes = {1: Tree(1, 0)}
    for i in range(n):
        data = deque([int(n) for n in f.readline().split()])
        node = nodes[data.popleft()]
        for j in range(data.popleft()):
            idx = data.popleft()
            bribe = data.popleft()
            child = Tree(idx, bribe)
            node.add_child(child)
            nodes[idx] = child
    return nodes[1]


# Util function to check if got equals to expected
def test(got, expected):
    if got == expected:
        prefix = ' OK '
    else:
        prefix = '  X '
    print '%s got: %s expected: %s' % (prefix, repr(got), repr(expected))


def file_as_str(filename):
    f = open(filename, 'rU')
    return f.read()


def in_match_out(infilename, outfilename):
    (infile, inext) = os.path.splitext(infilename)
    (outfile, outext) = os.path.splitext(outfilename)
    return outfile == infile.replace('in', 'out', 1)


# If input file specified then it prints the result of the algorithm
# Otherwise it tests all input/output files in ./tests folder
def main():
    if len(sys.argv) >= 2:
        filename = sys.argv[1]
        tree = build_tree(filename)
        print tree.get_min_cost_path()
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            print infile, outfile
            tree = build_tree(infile)
            min_path = tree.get_min_cost_path()
            test(repr(min_path), file_as_str(outfile))


if __name__ == '__main__':
    main()
