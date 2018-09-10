import java.lang.reflect.Array;

public class LinkedList {

    //Variable declarations
    LLNode head;
    LLNode tail;
    int len;

    //Constructor
    public LinkedList(LLNode node) {
        this.head = node;
        this.tail = node;
        this.len = 1;
    }

    //string representation of Linked List
    public String toString() {
        String result = String.valueOf(this.head.value);
        LLNode cur = this.head.next;
        while (cur != this.tail) {
            result += " -> " + cur.value;
            cur = cur.next;
        }
        return result;
    }

    //getters
    public LLNode getHead() {
        return this.head;
    }

    //append value to list
    public void append(int value) {
        LLNode node = new LLNode(value);
        if (this.len == 1) {
            this.head.next = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.len += 1;
    }

    //prepend value to list
    public void prepend(int value) {
        LLNode node = new LLNode(value);
        node.next = this.head;
        this.head = node;
        this.len += 1;
    }

    //methods
    public void appendNode(LLNode node) {
        if (this.len == 1) {
            this.head.next = node;
            this.tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
        this.len += 1;
    }

    public void prependNode(LLNode node) {
        node.next = this.head;
        this.head = node;
        this.len += 1;
    }


    //Compare two strings expressed as linked lists lexicographically.
    public int compare(LinkedList list1) {
        //different lengths
        if (this.len != list1.len) {
            if (this.len > list1.len) {
                return 1;
            } else {
                return -1;
            }
        }

        //same lengths - walk through lists and check letters
        LLNode cur1 = this.head;
        LLNode cur2 = list1.head;
        while (cur1 != null) {
            if (cur1.value != cur2.value) {
                if (cur1.value > cur2.value) {
                    return 1;
                } else {
                    return -1;
                }
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return 0;
    }

    //Add two numbers represented as linked lists
    public LLNode addNumLists(LinkedList lst) {
        if (this.len == lst.len) {
            //if lists have same length
            Object[] returned = addSameSize(this.head, lst.head);
            if((Integer)returned[1] == 1) {
                //if there is carry, create extra node with value 1 and prepend it
                LLNode new_node = new LLNode(1);
                new_node.next = (LLNode)returned[0];
                return new_node;
            } else {
                //if there is no carry, returned[0] already contains the node you need
                return (LLNode)returned[0];
            }
        } else {
            //if lists have different lengths
            int d = 0;
            LLNode longer = null;
            LLNode shorter = null;
            if (this.len > lst.len) {
                d = this.len - lst.len;
                longer = this.head;
                shorter = lst.head;
            } else {
                d = lst.len - this.len;
                longer = lst.head;
                shorter = this.head;
            }
            //longer - head node of longer list, d - difference between list lengths
            //walk along the longer list until reach node that makes list same lengths
            LLNode cur = longer;
            int i = d;
            while (i > 0) {
                cur = cur.next;
                i -= 1;
            }
//            System.out.println(cur.value);
//            System.out.println(shorter.value);
            //cur is the piece of longer list that is same length as shorter list
            Object[] returned_same_size = addSameSize(cur, shorter);
            Object[] returned = addDiff(longer, (LLNode)returned_same_size[0], (Integer)returned_same_size[1], d);
            if((Integer)returned[1] == 1) {
                //if there is carry, create extra node with value 1 and prepend it
                LLNode new_node = new LLNode(1);
                new_node.next = (LLNode)returned[0];
                return new_node;
            } else {
                //if there is no carry, returned[0] already contains the node you need
                return (LLNode) returned[0];
            }
        }
    }

    //helper function to add two lists of the same size
    private Object[] addSameSize(LLNode node1, LLNode node2) {
        //base case
        if(node1.next == null) {
            int new_value = node1.value + node2.value;
            int carry = 0;
            if (new_value > 9) {
                new_value = new_value - 10;
                carry = 1;
            }
            LLNode node = new LLNode(new_value);
            Object[] ret = {node, carry};
            return ret;
        } else {
            //get the return of the recursive call
            Object[] returned = this.addSameSize(node1.next, node2.next);
            //create new node
            int new_value = node1.value + node2.value + (Integer)returned[1];
            int carry = 0;
            if (new_value > 9) {
                new_value = new_value - 10;
                carry = 1;
            }
            LLNode node = new LLNode(new_value);
            //set the next node for the new node
            node.next = (LLNode)returned[0];
            //compute carry for the next node
            Object[] ret = {node, carry};
            return ret;
        }
    }


    //helper function to add the carry to the rest of the list
    private Object[] addDiff(LLNode node, LLNode sub_head, int c, int diff) {
        if (diff == 1) {
            int new_value = node.value + c;
            int carry = 0;
            if (new_value > 9) {
                new_value = new_value - 10;
                carry = 1;
            }
//            System.out.println(new_value);
//            System.out.println(carry);
            LLNode new_node = new LLNode(new_value);
            Object[] ret = {new_node, carry};
            new_node.next = sub_head;
            return ret;
        } else {
            //get the return of the recursive call
            Object[] returned = this.addDiff(node.next, sub_head, c, diff-1);
            //create new node
            int new_value = node.value + (Integer)returned[1];
            int carry = 0;
            if (new_value > 9) {
                new_value = new_value - 10;
                carry = 1;
            }
            LLNode new_node = new LLNode(new_value);
            //set the next node for the new node
            new_node.next = (LLNode)returned[0];
            //compute carry for the next node
            Object[] ret = {new_node, carry};
            return ret;
        }
    }

    public static void main(String args[]) {
        LLNode n1 = new LLNode(4);
        LinkedList l1 = new LinkedList(n1);
        l1.append(5);
        l1.append(1);
        l1.append(8);
        l1.append(2);
        l1.append(3);
        LLNode n2 = new LLNode(5);
        LinkedList l2 = new LinkedList(n2);
        l2.append(4);
        l2.append(9);
        LLNode node_res = l1.addNumLists(l2);
        System.out.println(node_res.next.next.value);
    }
}
