import re
from pprint import pprint

key_val = re.compile(r"\s*\"([()А-Яа-яёЁA-Za-z0-9_., :-]+)\"\s*:\s*\"([()А-Яа-яёЁA-Za-z0-9_:., -]+)\"")
def reg_parse_key_val(str):
    match = re.match(key_val, str)
    return match.group(1), match.group(2)

start_obj = re.compile(r"\s*\"([()А-Яа-яёЁA-Za-z0-9_., :-]+)\"\s*:\s*{\s*")
def reg_parse_start_obj(str):
    match = re.match(start_obj, str)
    if match is None:
        #print(str)
        return None
    return match.group(1)

end_obj = re.compile(r"\s*}\s*")
def reg_parse_end_obj(str):
    match = re.match(end_obj, str)
    return match is not None


def reg_parse(data, deep):
    ans = dict()
    i = 0
    while i < len(data):
        str = data[i]
        if reg_parse_end_obj(str):
            deep = deep - 1
            if deep == 0:
                return ans, int(i + 1)
        elif reg_parse_start_obj(str) is not None:
            key = reg_parse_start_obj(str)
            val, until = reg_parse(data[i + 1:], 1)
            deep += 1
            ans[key] = val
            i = i + until - 1
        else:
            key, val = reg_parse_key_val(str)
            ans[key] = val
        i += 1
    return ans
