#!/usr/bin/pyhton
import sys
import os
import time
from collections import deque


class Matrix:
    steps = {'U': -3, 'D': 3, 'L': -1, 'R': 1}
    not_allowed_pos = {'U': (0, 1, 2), 'D': (6, 7, 8), 'L': (0, 3, 6), 'R': (2, 5, 8)}
    index_range = range(9)
    good_string = '123456780'

    def __init__(self, matrix_str):
        self.matrix_str = matrix_str
        self.zero_pos = matrix_str.find('0')

    def move_zero(self, step):
        new_zero_pos = self.zero_pos + Matrix.steps[step]
        char_arr = list(self.matrix_str)
        char_arr[self.zero_pos], char_arr[new_zero_pos] = self.matrix_str[new_zero_pos], self.matrix_str[self.zero_pos]
        return Matrix(''.join(char_arr))

    def can_move_zero(self, step):
        return self.zero_pos not in Matrix.not_allowed_pos[step]

    def get_adj_matrixes(self):
        adj_matrixes = {}
        for step in Matrix.steps:
            if self.can_move_zero(step):
                adj_matrixes[step] = self.move_zero(step)
        return adj_matrixes

    def is_good(self):
        return self.matrix_str == Matrix.good_string

    def __eq__(self, other):
        return isinstance(other, self.__class__) and self.matrix_str == other.matrix_str

    def __hash__(self):
        return hash(self.matrix_str)

    def __repr__(self):
        return '\n'.join([self.matrix_str[:3], self.matrix_str[3:6], self.matrix_str[6:]])


def read_initial_matrix(filename):
    f = open(filename, 'rU')
    return Matrix(''.join(f.read().split()))


def find_path(matrix):
    matrix_map = {matrix: ''}  # map between matrixes and shortest paths
    matrixes = deque([(matrix, '')])
    while matrixes:
        matrix, path = matrixes.popleft()
        if matrix.is_good():
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
    # sys.argv.append('tests/input06.txt')
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
            print infile, outfile
            matrix = read_initial_matrix(infile)
            start = time.clock()
            path = find_path(matrix)
            end = time.clock()
            answer = str(len(path)) if path else '-1'
            test(answer, file_as_str(outfile))
            print 'Time', (end - start)


if __name__ == '__main__':
    main()
