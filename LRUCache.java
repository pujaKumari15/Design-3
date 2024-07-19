import java.util.HashMap;
import java.util.Map;

/***
 Using doubly Linked List
 Get operation - TC : O(1)
 Put operation - TC : O(1)
 */
class LRUCache {

    int capacity;
    Map<Integer, Node> cache;
    Node head, tail;

    //Doubly LinkedList
    class Node{
        int key;
        int value;
        Node prev;
        Node next;

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head.next = tail;
        tail.prev = head;

    }

    //add node at the start- MRU
    public void addToHead(Node node) {
        node.next = head.next;
        head.next = node;
        node.prev = head;
        node.next.prev = node;
    }

    //remove node from any position
    public void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public int get(int key) {

        if(!cache.containsKey(key))
            return -1;

        //add this key node to the MRU
        Node node = cache.get(key);
        removeNode(node);
        addToHead(node);

        return node.value;
    }

    public void put(int key, int value) {
        if(cache.containsKey(key)) {
            Node nodeToUpdate = cache.get(key);
            removeNode(nodeToUpdate);
            nodeToUpdate.value = value;
            addToHead(nodeToUpdate);
            cache.put(key, nodeToUpdate);
            return;
        }

        if(cache.size() == capacity) {
            //remove LRU node from the end
            Node lruNode = tail.prev;
            removeNode(lruNode);
            cache.remove(lruNode.key);
        }

        Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToHead(newNode);

    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */