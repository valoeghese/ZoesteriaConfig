class Compound:
    def __init__(self):
        self.data = {}

index = -1

def parseCompound(compound, data, size):
    global index
    mode = 0 # 0 = var names, 1 = var values
    buffer = ""
    while index + 1 < size:
        index += 1
        char = data[index]
        if char == '}':
            break
        elif mode == 1:
            if char.isspace():
                pass
            else:
                if char == '{': # new compound
                    compound.data[buffer] = parseCompound(Compound(), data, size)
                elif char == '[': # new list
                    compound.data[buffer] = parseList(data, size)
                else: # new data object
                    compound.data[buffer] = parseData(data, size)
                buffer = ""
                mode = 0
        elif char == '=':
            mode = 1
        elif not char.isspace():
            buffer += char # append character to string buffer
        
    return compound

def parseList(data, size):
    global index
    buffer = []
    while index < size:
        index += 1
        char = data[index]
        if char == ']':
            break
        elif not char.isspace():
            if char == '{': # new compound
                buffer.append(parseCompound(Compound(), data, size))
            elif char == '[': # new list
                buffer.append(parseList(data, size))
            else: # new data object
                buffer.append(parseData(data, size))
        
    return buffer

def parseData(data, size):
    global index
    buffer = data[index] # initial character is already at the index
    while index < size:
        index += 1
        char = data[index]
        if char == ';':
            break
        elif (not char.isspace()) or char == ' ':
            # the only form of whitespace in data values allowed is spaces
            # tabs, carriage return, and line feed are considered merely formatting
            buffer += char

    return buffer

fileData = ""

with open(input(), 'r') as file:
    fileData = file.read()

fileSize = len(fileData)
if (fileSize == 0):
    print("File is empty!")
else:
    fileContent = parseCompound(Compound(), fileData, fileSize)


