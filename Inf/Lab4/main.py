from raw_parse import *
from reg_parse import *
from lib_parse import *
from dict_to_xml import to_xml

print("Вариант " + str(367428 % 36))

def task1(data, xml_f):
    ans = dict()
    ans["root"] = parse(data.split('\n')[1:-1], 0)
    to_xml(ans, xml_f)

def task3(data, xml_f):
    ans = dict()
    ans["root"] = reg_parse(data.split('\n')[1:-1], 0)
    to_xml(ans, xml_f)

data = open("data.json", "r+").read()
xml_f = open("data.xml", "w+")

task1(data, open("data.xml", "w+"))
lib_parse(data, open("data.xml", "w+"))
task3(data, open("data.xml", "w+"))
