# CSC400-1 Module 6

Pseudocode

Data structures

Node { int data; Node next }

CustomLinkedList { Node head; int size }

insert(int data)

create newNode(data)
if head is null:
    head = newNode
else:
    cur = head
    while cur.next != null:
        cur = cur.next
    cur.next = newNode
size++


delete(int data) (delete first occurrence)

if head is null: return false
if head.data == data:
    head = head.next
    size--
    return true
prev = head
cur = head.next
while cur != null and cur.data != data:
    prev = cur
    cur = cur.next
if cur is null: return false
prev.next = cur.next
size--
return true


iterator()

return new LinkedListIterator(head)


LinkedListIterator

current = head
hasNext():
    return current != null
next():
    if current is null: throw NoSuchElementException
    value = current.data
    current = current.next
    return value


loadFromFile(String path) (read integers from text file)

open file at path
for each token separated by whitespace or commas:
    parse integer (supports negative too)
    insert(value)
close file


Demo (main)

create list
insert 1,2,3
iterate & print
delete 2
iterate & print
loadFromFile("numbers.txt")
iterate & print