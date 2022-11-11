from pprint import pprint

from dict2xml import dict2xml
import json

def lib_parse(data, xml_f):
    data = json.loads(data)
    xml_f.write(dict2xml(data))