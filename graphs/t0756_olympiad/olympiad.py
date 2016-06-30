#!/usr/bin/python

import sys
import os


def read_matrix(filename):
    f = open(filename, 'rU')
    n = int(f.readline())
    matrix = [[]] * n
    for i in range(n):
        matrix[i] = [int(k) for k in f.readline().split()]
    return matrix


def is_connected(matrix):
    n = len(matrix)
    nodes = [1]
    connected_nodes = set([1])
    while len(nodes) > 0:
        node = nodes.pop(0)
        for other_node in range(n):
            if matrix[node][other_node] == 1 and other_node not in connected_nodes:
                nodes.append(other_node)
                connected_nodes.add(other_node)
    return len(connected_nodes) == n


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


def main():
    if len(sys.argv) >= 2:
        filename = sys.argv[1]
        matrix = read_matrix(filename)
        print 'YES' if is_connected(matrix) else 'NO'
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            print infile, outfile
            matrix = read_matrix(infile)
            test('YES' if is_connected(matrix) else 'NO', file_as_str(outfile))


if __name__ == '__main__':
    main()
