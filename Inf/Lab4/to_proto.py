import lessons_pb2


def to_proto(data, proto_f):
    lessons = lessons_pb2.root()
    lessons.group = data['group']
    day = lessons.day
    for key in data['wednesday']:
        les = day.lessons.add()
        les.lesson = data['wednesday'][key]['lesson']
        les.place = data['wednesday'][key]['place']
        les.teacher = data['wednesday'][key]['teacher']
        les.time = data['wednesday'][key]['time']
        les.weeks = data['wednesday'][key]['weeks']
    proto_f.write(lessons.SerializeToString())
