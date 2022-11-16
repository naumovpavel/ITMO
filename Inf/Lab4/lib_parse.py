import json

from dict2xml import dict2xml
from dicttoxml import dicttoxml


def lib_parse(data, xml_f):
    parsed_data = dict()
    parsed_data['root'] = json.loads(data)
    xml_f.write(dict2xml(parsed_data))