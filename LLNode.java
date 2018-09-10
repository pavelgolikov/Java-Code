public class LLNode {
    //Variable declarations
    int value;
    LLNode next;


    //Constructor declaration
    public LLNode(int value) {
        this.value = value;
        this.next = null;
    }

   //setter:
   public void setNext(LLNode node){
       this.next = node;
   }

   //getters:
    public Object getValue() {
        return this.value;
    }

    public LLNode getNext() {
        return this.next;
    }
}
