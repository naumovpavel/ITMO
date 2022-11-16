import re

isu = 367428

print(isu % 6)

def slove(s):
    print(s)
    reg = r'(*?)/(*?)/(*?)'
    m = re.match(r'(.*?)/(.*?)/(.*)', s)
    if m is None:
        print("Не хайку. Должно быть 3 строки.")
        print()
        return
    ye = True
    for i in range(1, 4):
        if 5 + (2 if i % 2 == 0 else 0) != len(re.findall(r'[аяуюоеёэиы]', m.group(i), re.IGNORECASE)):
            ye = False
    if ye:
        print("Хайку!")
    else:
        print("Не хайку.")
    print()

strings = ['апельсиновый. / автобиография./ подосиновик...', 'не хайку',
           'безынициативный / Великобритания / появляются',
           'библиотекарь. / безопасности / библиотекарь',
           'Александровна / совершенствование / предъюбилейный']
print('Tests:')
for s in strings:
    slove(s)

print("Write a string")
slove(input())
#print("Answer:", len(re.findall(r':-/', input())))
