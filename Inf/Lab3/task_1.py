import re

isu = 367428

print(isu % 6, isu % 4, isu % 7)
print('My smail  :-/')

strings = [':-/:-/', ':/', '-:', ':-/:-/:-/ :-', ':-/:/:-/:-/']
print('Tests:')
for s in strings:
    print(s)
    print("Right answer:", s.count(':-/'))
    print("Reg answer:", len(re.findall(r':-/', s)))
    if s.count(':-/') != len(re.findall(r':-/', s)):
        print("!!! Wrong answer")
    print()

print("Write a string")
print("Answer:", len(re.findall(r':-/', input())))
