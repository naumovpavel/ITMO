def get_key(str):
    return str.split(":")[0].strip().split("\"")[1]


def get_value(str):
    return str.split(":")[1].strip().split("\"")[1]


def parse(data, deep):
    ans = dict()
    i = 0
    while i < len(data):
        str = data[i]
        if '}' in str:
            deep = deep - 1
            if deep == 0:
                return ans, int(i + 1)
        elif '{' in str:
            key = get_key(str)
            val, until = parse(data[i + 1:], 1)
            deep += 1
            ans[key] = val
            i = i + until - 1
        else:
            key = get_key(str)
            val = get_value(str)
            ans[key] = val
        i += 1
    return ans