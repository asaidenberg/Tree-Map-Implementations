/**
 * Class that implements a red-black tree which implements the MyMap interface.
 * @author Brian S. Borowski
 * @version 1.2.1 March 5, 2024
 */
public class RBTreeMap<K extends Comparable<K>, V> extends BSTreeMap<K, V>
        implements MyMap<K, V> {

    /**
     * Creates an empty red-black tree map.
     */
    public RBTreeMap() { }

    /**
     * Creates a red-black tree map from the array of key-value pairs.
     * @param elements an array of key-value pairs
     */
    public RBTreeMap(Pair<K, V>[] elements) {
        insertElements(elements);
    }

    /**
     * Creates a red-black tree map of the given key-value pairs. If
     * sorted is true, a balanced tree will be created via a divide-and-conquer
     * approach. If sorted is false, the pairs will be inserted in the order
     * they are received, and the tree will be rotated to maintain the red-black
     * tree properties.
     * @param elements an array of key-value pairs
     */
    public RBTreeMap(Pair<K, V>[] elements, boolean sorted) {
        if (!sorted) {
            insertElements(elements);
        } else {
            root = createBST(elements, 0, elements.length - 1);
        }
    }

    /**
     * Recursively constructs a balanced binary search tree by inserting the
     * elements via a divide-snd-conquer approach. The middle element in the
     * array becomes the root. The middle of the left half becomes the root's
     * left child. The middle element of the right half becomes the root's right
     * child. This process continues until low > high, at which point the
     * method returns a null Node.
     * All nodes in the tree are black down to and including the deepest full
     * level. Nodes below that deepest full level are red. This scheme ensures
     * that all paths from the root to the nulls contain the same number of
     * black nodes.
     * @param pairs an array of <K, V> pairs sorted by key
     * @param low   the low index of the array of elements
     * @param high  the high index of the array of elements
     * @return      the root of the balanced tree of pairs
     */
    protected Node<K, V> createBST(Pair<K, V>[] pairs, int low, int high) {
     
      //finds number of redNodes that should be in in the tree
      int redCount = (int)(Math.log(pairs.length + 1) / Math.log(2));
      return createRedBlackTree(pairs, low, high, null, 0, redCount);
        // TODO
    }
    protected Node<K, V> createRedBlackTree(Pair<K,V>[] pairs, int low, int high, Node<K,V> parent, int level, int redCount) {
      
      if(low > high){
        return null;
      }
      int mid = low + (high - low) / 2;
      //create new Node
      RBNode<K, V> node = new RBNode<>(pairs[mid].key, pairs[mid].value);
      if(level == redCount){
        node.color = RBNode.RED;
        
      } else{
        node.color = RBNode.BLACK;
      }
      node.setParent(parent);
      //recursively make lefta nd right subtrees
      node.setLeft(createRedBlackTree(pairs, low, mid - 1, node, level + 1, redCount));
      node.setRight(createRedBlackTree(pairs, mid + 1, high, node, level + 1, redCount));
      size++;
      return node;
      
    }
    

    /**
     * Associates the specified value with the specified key in this map. If the
     * map previously contained a mapping for the key, the old value is replaced
     * by the specified value.
     * @param key   the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    @Override
    public V put(K key, V value) {
      RBNode<K, V> newNode = new RBNode<>(key, value);
      //new node to insert
      RBNode<K, V> current = (RBNode<K, V>)root;
      //node in traversal
      RBNode<K, V> parent = null;
      //going to be the parent of the new one

      while(current!= null){
        parent = current;
        if(key.compareTo(current.key) < 0){
          current = current.getLeft();
          //current node updated to newnode's left child is less than current - opposite for right
        }else if(key.compareTo(current.key) > 0){
          
          current = current.getRight();
        } else{
          
          V prevVal = current.value;
          current.value = value;
          return prevVal;
          //if key is equal value already exists in tree
          
          
        }
        }
      newNode.setParent(parent);
      if(parent == null){
        root = newNode;
      } else if(key.compareTo(parent.key) < 0){
        parent.setLeft(newNode);
        
      } else{
        parent.setRight(newNode);
      }
      newNode.color = RBNode.RED;
      insertFixup(newNode);
      size++;

      return null;
      // TODO

        
      }

      


      
    

    /**
     * Removes the mapping for a key from this map if it is present.
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with key, or null if there was no
     *         mapping for key
     */
    public V remove(K key) {
        // TODO, otherwise return null.
      V valRemove = get(key);
      if(valRemove== null){
        return null;
      }
      RBNode<K, V> nodeRemove = new RBNode<>(key, valRemove);
      RBNode<K, V> nodeFix = nodeRemove;
      RBNode<K, V> replacementNode;

      byte ogcolor = nodeFix.color;
      //keep og color of node 

      //if node has no left child replacement nodei s right child so transplant w right child
      if(nodeRemove.getLeft() == null){
        replacementNode = nodeRemove.getRight();
        RBTransplant(nodeRemove, nodeRemove.getRight());
        
      }else if(nodeRemove.getRight() == null){
        replacementNode = nodeRemove.getLeft();
        RBTransplant(nodeRemove, nodeRemove.getLeft());

      //if node has two kids find minimum key and set it as replacement node
      }else {
        nodeFix = minKey(nodeRemove.getRight());
        ogcolor = nodeFix.color;
        
        replacementNode = nodeFix.getRight();
        if(nodeFix != nodeRemove.getRight()){
          RBTransplant(nodeFix, nodeFix.getRight());
          
          nodeFix.setRight(nodeRemove.getRight());
          nodeFix.getRight().setParent(nodeFix);
        } else{
          replacementNode.setParent(nodeFix);
        }
        RBTransplant(nodeRemove, nodeFix);
        nodeFix.setLeft(nodeRemove.getLeft());
        
        nodeFix.getLeft().setParent(nodeFix);
        nodeFix.color = nodeRemove.color;
        
        
      }
      //from pseudocode
      if(ogcolor == RBNode.BLACK){
        deleteFixup(replacementNode);
      }
      return valRemove;
    }


          
    private void RBTransplant(RBNode<K, V> node, RBNode<K, V> replacement){
      if(node != null && replacement != null){
        if(node.getParent() == null){
          
        //root is replacement if no parent
        root = replacement;
        }
        
      } else if(node == node.getParent().getLeft()){
        node.getParent().setLeft(replacement);
        //parent points to replacement
        
      } else {
        node.getParent().setRight(replacement);
        
      }
      replacement.setParent(node.getParent());
      }
    

  private RBNode<K, V> minKey(RBNode<K, V> node){
    while(node.getLeft() != null){
      //finds minimum in subtree
      node = node.getLeft();
    }
    return node;
  }

          
      




    /**
     * Fixup method described on p. 339 of CLRS, 4e.
     */
    private void insertFixup(RBNode<K, V> z) {
       while(z != root && z.getParent().color == RBNode.RED){
         //check if z's parent is left child of grandparent
         if (z.getParent() == z.getParent().getParent().getLeft()) {
          RBNode<K, V> y = z.getParent().getParent().getRight();

           //if uncle is red 
           if(y != null && y.color == RBNode.RED){
             z.getParent().color = RBNode.BLACK;
             y.color = RBNode.BLACK;
             
          z.getParent().getParent().color = RBNode.RED;
             z = z.getParent().getParent();
             //if uncle is black and and z is right child
           } else{
              if(z == z.getParent().getRight()){
               z = z.getParent();
               leftRotate(z);
             }
          z.getParent().color = RBNode.BLACK;
             
             z.getParent().getParent().color = RBNode.RED;
            rightRotate(z.getParent().getParent());
           }
         }  else {
           //if zs parent is right child of grandparent
        RBNode<K, V> y = z.getParent().getParent().getLeft();
           if(y != null && y.color == RBNode.RED){
             z.getParent().color = RBNode.BLACK;
             y.color = RBNode.BLACK;
             
            z.getParent().getParent().color = RBNode.RED;
             z = z.getParent().getParent();
             
           } else{
             if(z == z.getParent().getLeft()){
               z = z.getParent();
               rightRotate(z);
               
             }
            z.getParent().color = RBNode.BLACK;
             z.getParent().getParent().color = RBNode.RED;
             leftRotate(z.getParent().getParent());
           }
         }
           
         }
      ((RBNode<K, V>) root).color = RBNode.BLACK;
      // TODO

       }
    

    /**
     * Fixup method described on p. 351 of CLRS, 4e.
     */
    private void deleteFixup(RBNode<K, V> x) {
        // TODO, optionally
    }

    /**
     * Left-rotate method described on p. 336 of CLRS, 4e.
     */
    private void leftRotate(Node<K, V> x) {
      Node<K, V> y = x.getRight();
          x.setRight(y.getLeft());
          if(y.getLeft() != null) {
               y.getLeft().setParent(x);
          }
          y.setParent(x.getParent());
      //parent of y is parent of x
      if (x.getParent() == null) {
               root = y;
          }else if (x == x.getParent().getLeft()) {
        
              x.getParent().setLeft(y);
          } else {
              x.getParent().setRight(y);
          }

      // y is new parent of x
      y.setLeft(x);
       x.setParent(y);
      
        // TODO
    }

    /**
     * Right-rotate method described on p. 336 of CLRS, 4e.
     */
    private void rightRotate(Node<K, V> x) {
        // TODO
      //similar to above func
      Node<K, V> y = x.getLeft();
      x.setLeft(y.getRight());
        if(y.getRight() != null) {
          
          y.getRight().setParent(x);
      }
       y.setParent(x.getParent());
      if (x.getParent() == null) {
          root = y;
      }   else if (x == x.getParent().getRight()) {
          x.getParent().setRight(y);
      }else {
          x.getParent().setLeft(y);
      }
       y.setRight(x);
      
      x.setParent(y);

    }
}
