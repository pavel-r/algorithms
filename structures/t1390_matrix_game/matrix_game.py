#!/usr/bin/pyhton
import sys
import os
from collections import deque


class Matrix:
    steps = {'U': (-1, 0),
             'D': (1, 0),
             'L': (0, -1),
             'R': (0, 1)}
    positions = [(0, 0), (0, 1), (0, 2), (1, 0), (1, 1), (1, 2), (2, 0), (2, 1), (2, 2)]
    good_matrix = ((1, 2, 3), (4, 5, 6), (7, 8, 0))

    def __init__(self, matrix, zero_pos=None):
        if zero_pos:
            assert matrix[zero_pos[0]][zero_pos[1]] == 0
        else:
            zero_pos = [(i, j) for (i, j) in Matrix.positions if matrix[i][j] == 0][0]
        self.matrix = matrix
        self.zero_i, self.zero_j = zero_pos

    def move_zero(self, step):
        new_matrix = [
            list(self.matrix[0]),
            list(self.matrix[1]),
            list(self.matrix[2])
        ]
        di, dj = Matrix.steps[step]
        new_zero_i = self.zero_i + di
        new_zero_j = self.zero_j + dj
        new_matrix[self.zero_i][self.zero_j] = new_matrix[new_zero_i][new_zero_j]
        new_matrix[new_zero_i][new_zero_j] = 0
        tuple_matr = tuple([tuple(row) for row in new_matrix])
        return Matrix(tuple_matr, (new_zero_i, new_zero_j))

    def can_move_zero(self, step):
        di, dj = Matrix.steps[step]
        return (self.zero_i + di, self.zero_j + dj) in Matrix.positions

    def get_adj_matrixes(self):
        adj_matrixes = {}
        for step in Matrix.steps:
            if self.can_move_zero(step):
                adj_matrixes[step] = self.move_zero(step)
        return adj_matrixes

    def is_good(self):
        return (self.zero_i, self.zero_j) == (0, 0)

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.matrix == other.matrix

    def __hash__(self):
        return hash(self.matrix)

    def __repr__(self):
        return '\n'.join(repr(row) for row in self.matrix)


def read_initial_matrix(filename):
    f = open(filename, 'rU')
    row1 = tuple([int(n) for n in f.readline().split()])
    row2 = tuple([int(n) for n in f.readline().split()])
    row3 = tuple([int(n) for n in f.readline().split()])
    matrix = (row1, row2, row3)
    return Matrix(matrix)


def find_path(matrix):
    the_matrix = Matrix(Matrix.good_matrix)
    matrix_map = {}  # map between matrixes and shortest paths
    matrixes = deque([(matrix, '')])
    while matrixes:
        matrix, path = matrixes.popleft()
        if matrix == the_matrix:
            return path
        for step, adj_m in matrix.get_adj_matrixes().iteritems():
            new_path = path + step
            if adj_m not in matrix_map:  # or len(new_path) < len(matrix_map[adj_m]):
                matrix_map[adj_m] = new_path
                matrixes.append((adj_m, new_path))


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
        matrix = read_initial_matrix(filename)
        path = find_path(matrix)
        answer = '%s\n%s' % (len(path), path) if path else '-1'
        print answer
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            matrix = read_initial_matrix(infile)
            path = find_path(matrix)
            answer = str(len(path)) if path else '-1'
            test(answer, file_as_str(outfile))


if __name__ == '__main__':
    main()
