from time import time

from raw_parse import *
from reg_parse import *
from lib_parse import *
from dict_to_xml import to_xml
from to_proto import to_proto

print("Вариант " + str(367428 % 36))


def del_file_content(file):
    file.seek(0)
    file.truncate()


def task1(data, xml_f):
    ans = dict()
    ans["root"] = parse(data.split('\n')[1:-1], 0)
    to_xml(ans, xml_f)


def task3(data, xml_f):
    ans = dict()
    ans["root"] = reg_parse(data.split('\n')[1:-1], 0)
    to_xml(ans, xml_f)


def bench(data, xml_f, xml_lib_f):
    start = time()
    for j in range(100):
        del_file_content(xml_f)
        task1(data, xml_f)
    print(f"Task 1 time: {time() - start}")
    start = time()
    for j in range(100):
        del_file_content(xml_lib_f)
        lib_parse(data, xml_lib_f)
    print(f"Task 2 time: {time() - start}")
    start = time()
    for j in range(100):
        del_file_content(xml_f)
        task3(data, xml_f)
    print(f"Task 3 time: {time() - start}")


data = open("data.json", "r+", encoding="utf-8").read()
xml_f = open("data.xml", "w+", encoding="utf-8")
xml_lib_f = open("data_lib.xml", "w+", encoding="utf-8")
proto_f = open("SerializedToProto", "wb")


task1(data, open("data.xml", "w+"))
lib_parse(data, xml_lib_f)
task3(data, open("data.xml", "w+", encoding="utf-8"))
to_proto(json.loads(data), proto_f)
bench(data, xml_f, xml_lib_f)

