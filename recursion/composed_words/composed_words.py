#!/usr/bin/pyton
import os
import sys


def read_infile(filename):
    f = open(filename, 'rU')
    return [s.strip() for s in f.readlines()]


def execute_alg(words):
    words.sort(key=len, reverse=True)

    simple_words = {}
    longest_composed = ''

    def composed(w):
        if not w:
            return True
        for i in range(len(w)):
            if simple_words.has_key(w[:i + 1]) and composed(w[i + 1:]):
                return True
        return False

    while words:
        w = words.pop()
        if composed(w):
            if len(w) > len(longest_composed):
                longest_composed = w
        else:
            simple_words[w] = w
    return longest_composed


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
        words = read_infile(filename)
        print execute_alg(words)
    else:
        filenames = os.listdir('tests')
        infiles = [f for f in filenames if f.startswith('in')]
        outfiles = [f for f in filenames if f.startswith('out')]
        files = [(os.path.join('tests', infile), os.path.join('tests', outfile)) for infile in infiles for outfile in
                 outfiles if in_match_out(infile, outfile)]
        for infile, outfile in files:
            print infile, outfile
            words = read_infile(infile)
            test(execute_alg(words), file_as_str(outfile))


if __name__ == '__main__':
    main()
