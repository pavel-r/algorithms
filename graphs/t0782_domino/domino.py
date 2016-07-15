#!/usr/bin/python

import sys
import os
from collections import deque


def read_input_file(filename):
    f = open(filename, 'rU')
    graph = [[0 for i in range(7)] for j in range(7)]
    n = int(f.readline().strip())
    for i in range(n):
        d0, d1 = f.readline().split()
        graph[int(d0)][int(d1)] += 1
    return graph


def is_exists_euler_circle(graph):
    n = len(graph)
    for i in range(n):
        out_edges = sum(graph[i])
        in_edges = sum([graph[j][i] for j in range(n)])
        if in_edges != out_edges:
            return False
    return True


def get_euler_circle(graph):
    n = len(graph)
    graph_copy = [[graph[j][i] for i in range(n)] for j in range(n)]
    v = next(i for i in range(n) if sum(graph_copy[i]) > 0)
    vstack = deque([v])
    euler_circle = []
    while vstack:
        from_v = vstack.pop()
        to_v = next((j for j in range(n) if graph_copy[from_v][j] > 0), None)
        if to_v:
            vstack.append(from_v)
            vstack.append(to_v)
            graph_copy[from_v][to_v] -= 1
        else:
            euler_circle.append(from_v)
    euler_circle.reverse()
    return euler_circle


# Util function to check if got equals to expected
def test(got, expected):
    if got == expected:
        prefix = ' OK '
    else:
        prefix = '  X '
    print '%s got: %s expected: %s' % (prefix, repr(got), repr(expected))


def file_as_str(filename):
    f = open(filename, 'rU')
    return f.read().strip()


def in_match_out(infilename, outfilename):
    (infile, inext) = os.path.splitext(infilename)
    (outfile, outext) = os.path.splitext(outfilename)
    return outfile == infile.replace('in', 'out', 1)


def main():
    if len(sys.argv) >= 2:
        filename = sys.argv[1]
        graph = read_input_file(filename)
        print graph
        euler_circle_exists = is_exists_euler_circle(graph)
        if euler_circle_exists:
            print get_euler_circle(graph)
        answer = 'Yes' if euler_circle_exists else 'No'
        print answer
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            print infile, outfile
            graph = read_input_file(infile)
            euler_circle_exists = is_exists_euler_circle(graph)
            if euler_circle_exists:
                print get_euler_circle(graph)
            answer = 'Yes' if euler_circle_exists else 'No'
            test(answer, file_as_str(outfile))


if __name__ == '__main__':
    main()
