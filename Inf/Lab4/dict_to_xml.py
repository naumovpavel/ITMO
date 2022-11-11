def to_xml(data, xml_f, deep = 0):
    if deep == 0:
        xml_f.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
    for key, val in data.items():
        xml_f.write("    " * deep + "<" + key + ">")
        if type(val) is tuple:
            xml_f.write('\n')
            to_xml(val[0], xml_f, deep + 1)
            xml_f.write("    " * deep)
        elif type(val) is dict:
            xml_f.write('\n')
            to_xml(val, xml_f, deep + 1)
            xml_f.write("    " * deep)
        else:
            xml_f.write(val)
        xml_f.write("</" + key + ">\n")