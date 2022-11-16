import re

isu = 367428

print(isu % 5)

def slove(group, string):
    #TODO добавить 2 имена и отсутствие отчества
    m1 = re.findall(r'^([-А-Яа-яЁё]+?) ([А-Яа-яЁё]).([А-Яа-яЁё]). ([A-Z]\d+)$', string, re.MULTILINE)
    m = re.findall(r'^([-А-Яа-яЁё]+?) ([А-Яа-яЁё]|[А-Яа-яЁё].-[А-Яа-яЁё]).([А-Яа-яЁё].|[А-Яа-яЁё].-[А-Яа-яЁё].)? ([A-Z]\d+)$', string, re.MULTILINE)
    #print(m)
    for i in range(len(m)):
        #print(m[i])
        printed = False

        for j in range(3):
            if re.match(r'(.*?)-(.*)', m[i][j]) is not None:
                g = re.match(r'(.*?)-(.*)', m[i][j])
                if g.group(1)[0] != g.group(2)[0]:
                    print(f'{m[i][0]} {m[i][1]}.{m[i][2]} {m[i][3]}')
                    printed = True
                    continue
        if printed:
            continue
        if not (m[i][0][0] == m[i][1][0] and (m[i][2] == '' or m[i][1][0] == m[i][2][0]) and m[i][3] == group):
            print(f'{m[i][0]} {m[i][1]}.{m[i][2]} {m[i][3]}')
    print()

tests = [['P0000', "Петров-Петр П.П. P0000\nАнанасов А.А. P33113\nПриемный Е.В. P0000\nИмпрессивный И.И. P0000"],
         ['P3118', 'Сружиницкий-Занырбаев С.С. P3118\nРванов П.В. P3118\nИванов И.И. P3119'],
         ['P3108', 'Хеминг Х.Х. P3108\nХуарачи Х.Р. P3333\nПепсикольный П.-Р. P3333'],
         ['P3100', 'Виноградов В.В. P3000\nВиноградов П.П. P3100\nХмурый Х.-Х.Х. P3100'],
         ['P2020', 'Жданок Ж.Ж. P2022\n Засоленный З.С. P2020\nСивый С.-С.С.-Р. P2020']]
print('Tests:')
i = 1
for test in tests:
    print(f'Test {i}:')
    i += 1
    slove(test[0], test[1])

print("Write group")
group = input()
print('Write number of people in the group')
cnt = int(input())
strings = []
for i in range(cnt):
    s = input()
    strings.append(s)
string = '\n'.join(strings)

slove(group, string)
