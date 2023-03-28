def parse_story_content(contents):
    content_list = []
    for content in contents:
        content_list.append(content["content"])
    parsed_story = " ".join(content_list)
    return parsed_story
