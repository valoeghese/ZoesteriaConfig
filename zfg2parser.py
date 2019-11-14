class Container:
    def __init__(self):
        self.data = {}

index = -1

# compound "container" entries
def parseContainer(container, data, size):
    global index
    mode = 0 # 0 = var names, 1 = var values
    buffer = ""
    while index + 1 < size:
        index += 1
        char = data[index]
        if char == '}':
            break
        elif char == "#":
            parseComment(data, size)
        elif mode == 1:
            if char.isspace():
                pass
            else:
                if char == '{': # new container
                    container.data[buffer] = parseContainer(Container(), data, size)
                elif char == '[': # new list
                    container.data[buffer] = parseList(data, size)
                elif char == ';': # new empty data object
                    container.data[buffer] = ""
                else: # new data object
                    container.data[buffer] = parseData(data, size)
                buffer = ""
                mode = 0
        elif char == '=':
            mode = 1
        elif not char.isspace():
            buffer += char # append character to string buffer
        
    return container

# list entries
def parseList(data, size):
    global index
    buffer = []
    while index + 1 < size:
        index += 1
        char = data[index]
        if char == ']':
            break
        elif char == "#":
            parseComment(data, size)
        elif not char.isspace():
            if char == '{': # new container
                buffer.append(parseContainer(Container(), data, size))
            elif char == '[': # new list
                buffer.append(parseList(data, size))
            elif char == ';': # new empty data object
                buffer.append("")
            else: # new data object
                buffer.append(parseData(data, size))
        
    return buffer

# data value entries
def parseData(data, size):
    global index
    buffer = data[index] # initial character is already at the index
    while index + 1 < size:
        index += 1
        char = data[index]
        if char == ';':
            break
        elif (not char.isspace()) or char == ' ':
            # the only form of whitespace in data values allowed is spaces
            # tabs, carriage return, and line feed are considered merely formatting
            buffer += char

    return buffer.strip() # remove trailing whitespace

# comments
def parseComment(data, size):
    global index
    while index + 1 < size:
        index += 1
        char = data[index]
        if char == '\n': # break comment on new lines
            break

fileData = ""

with open(input(), 'r') as file:
    fileData = file.read()

fileSize = len(fileData)
if (fileSize == 0):
    print("File is empty!")
else:
    fileContent = parseContainer(Container(), fileData, fileSize)


