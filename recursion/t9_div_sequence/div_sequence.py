#!/usr/bin/python

import sys
import os


def read_infile(file_name):
    f = open(file_name, 'rU')
    return [int(n) for n in f.read().split()[1:]]


def calculate_max_sequence(array):
    n = len(array)
    sorted_array = sorted(array, key=abs)
    longest_sequence = [1] * n
    for i in range(0, n):
        for j in range(i + 1, n):
            a = sorted_array[i]
            b = sorted_array[j]
            if (a == 0 or b % a == 0) and (b != 0):
                longest_sequence[j] = max(longest_sequence[j], longest_sequence[i] + 1)
    return max(longest_sequence)


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
        array = read_infile(filename)
        print calculate_max_sequence(array)
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            print infile, outfile
            array = read_infile(infile)
            test(calculate_max_sequence(array), int(file_as_str(outfile)))


if __name__ == '__main__':
    main()
