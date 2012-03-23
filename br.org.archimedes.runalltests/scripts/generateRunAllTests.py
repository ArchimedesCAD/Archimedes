# entra recursivamente em todos os diretorios do projeto e pega *Test.class
# gera arquivo .java

import os
import re

def contains_test(path, filename):
	f = open(path, 'r')
	r = False
	content = f.read()
	if content.find('@Test') != -1:
		package_line = re.findall('package\s.*;\n', content)
		package_line = package_line[0]
		package_line = package_line.replace('package', 'import')
		package_line = package_line.replace(';\n', '.')
		package_line = package_line + filename + ';\n'
		package_line = package_line.replace('.java', '')
		r = package_line
	f.close()
	return r

def sort_func(t1, t2):
	(r1, f1) = t1
	(r2, f2) = t2
	return -1 if f1 < f2 else 1

def test_files():
	l = []
	for root, dirs, files in os.walk('../../'):
		root = root + '/'
		for f in files:
			if f.endswith('Test.java'):
				tests = contains_test(root+f, f)
				if tests != False:
					l.append((tests, f.replace('java', 'class')))
	l = sorted(l, cmp=sort_func)
	return l

def generate():
	rootList = []
	fileList = []

	for (root, f) in test_files():
		rootList.append(root)
		fileList.append(f)		

	testSuite = open('../src/br/org/archimedes/runalltests/RunAll.java', 'w')

	testSuite.write('package br.org.archimedes.runalltests;\n')
	testSuite.write('import org.junit.runner.RunWith;\n')
	testSuite.write('import org.junit.runners.Suite;\n')

	for r in rootList:
		testSuite.write(r)

	testSuite.write('\n@RunWith(Suite.class)\n@Suite.SuiteClasses({')

	for f in fileList:
		if f != fileList[-1]:
			testSuite.write('\n\t'+f+',')
		else:
			testSuite.write('\n\t'+f)
	testSuite.write('\n})\npublic class RunAll{}\n')

	testSuite.close()

def main():
	generate()

main()

