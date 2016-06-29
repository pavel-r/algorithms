#!/usr/bin/python

import sys
import os


def read_matrix(filename):
    f = open(filename, 'rU')
    h, r = [int(n) for n in f.readline().split()]
    matrix = [[0] * h for i in range(h)]
    for i in range(r):
        fr, to, ln = [int(n) for n in f.readline().split()]
        matrix[fr - 1][to - 1] = ln
        matrix[to - 1][fr - 1] = ln
    return matrix


def calc_min_dist_from(fromv, matrix):
    n = len(matrix)
    # dist_matrix = [[(0, False)] * len(matrix[0]) for n in len(matrix)]
    dist_array = [-1] * n
    vertises = [fromv]
    dist_array[fromv] = 0
    while len(vertises) > 0:
        v = vertises.pop(0)
        for i in range(n):
            if matrix[v][i] != 0 and (dist_array[i] == -1 or dist_array[v] + matrix[v][i] < dist_array[i]):
                dist_array[i] = dist_array[v] + matrix[v][i]
                vertises.append(i)
    return dist_array


def calc_min_dist(matrix):
    vidx, min_dist = -1, sys.maxint
    for i in range(len(matrix)):
        mindist = calc_min_dist_from(i, matrix)
        min_dist_i = sum(mindist)
        if min_dist_i < min_dist:
            min_dist = min_dist_i
            vidx = i
    return vidx, min_dist


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
        vidx, min_dist = calc_min_dist(matrix)
        print vidx + 1, min_dist
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            print infile, outfile
            matrix = read_matrix(infile)
            vidx, min_dist = calc_min_dist(matrix)
            test('%s %s' % (vidx + 1, min_dist), file_as_str(outfile))


if __name__ == '__main__':
    main()
