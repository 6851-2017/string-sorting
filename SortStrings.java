
private static final String ALPHABET = "$abcdefghijklmnopqrstuvwxyz";

for (Map.Entry<String, TrieNode> e : children.entrySet()) {
  System.out.println(String.format(
    "My key is %s and my value is %s", 
    e.getKey(), e.getValue()));
}
  

class TrieNode {
  public Map<String, TrieNode> children = new HashMap<>();
  // The root TrieNode has a null parent
  public TrieNode parent;
  public String edgeToMe;
  
  public TrieNode(TrieNode parent, String edgeToMe) {
    this.parent = parent;
    this.edgeToMe = edgeToMe;
  }
  
  /** TODO: returns the number of new nodes added below this one */
  void addString(TrieNode parent, String inputStr) {
    if (inputStr.size() == 0) {
      return;
    }
    
    String firstChar = (String) inputStr.charAt(0);
    if (!children.containsKey(firstChar)) {
      children.put(firstChar, new TrieNode(parent, firstChar));
    }
    children.get(firstChar).descend(children.get(firstChar), inputStr.substring(1));
  }
  
  /** Compresses this and all descendants */
  void compress() {
    Stack<TrieNode> stack = new LinkedList<TrieNode>();
    stack.add(this)
    while (!stack.isEmpty()) {
      TrieNode node = stack.pop();
      if (node.children.size() == 1) {
        node.collapse();
      } else {
        for (TrieNode child : node.children.valueSet()){
          stack.add(child);
        }
      }
    }
  }
  
  /** Collapse this node into its parent. */
  void collapse() {
    assert (this.children.keySet().size() == 1, "This node cannot be collapsed");
    
    // This node's only child should change its parent
    // to be this node's current parent
    TrieNode onlyChild = this.children.values().toArray()[0];
    onlyChild.parent = this.parent;
    
    // Change the parent's edge to include this node's edge
    String myEdge = this.children.keySet().toArray()[0];
    String edgeToMe = this.edgeToMe;
    onlyChild.edgeToMe = edgeToMe + myEdge;
    this.parent.children.remove(edgeToMe);
    this.parent.children.put(edgeToMe + myEdge, onlyChild);
    
  }
  
  List<TrieNode> getSubtree() {
    if (children.isEmpty()) {
      return Collections.singletonList(this);
    }
    List<TrieNode> toReturn = new LinkedList<>();
    toReturn.add(this);
    for (TrieNode child : children.valueSet()) {
      toReturn.add(child.getSubtree());
    }
    return toReturn;
  }
    
}
  
  

class SortNode {
  // Public so we can just access them directly
  public final TrieNode trieNode;
  public final char letter;
  public final int number;
  
  public SortNode(TrieNode trieNode, char letter, int number) {
    this.trieNode = trieNode;
    this.letter = letter;
    this.number = number;
  }
}

public class SortStrings {
  public static void main(String[] args) {
    String alphabet = args[0];
    List<String> strings = args[1];
    List<String> sortedStrings = sortStrings(alphabet, strings);
  }
  
  public static List<Integer> radixSort(List<Integer> inputList) {
    List<List<Integer>> buckets = new ArrayList<LinkedList<>>(10);
    for (int i = 0; i < 10; i++) {
      buckets.add(i, new LinkedList<>());
    }
    
    List<Integer> sorted = new LinkedList<>(inputList);
    int currentPowerOfTen = 10;
    
    while (currentPowerOfTen < Collections.max(inputList)) {
      // put things into the buckets depending on their digit
      for (int input : sorted) {
        buckets.get(input % currentPowerOfTen).add(input);
      }
      
      // take things out of the buckets in order
      sorted.clear();
      for (int bucketNum = 0; bucketNum < 10; bucketNum++) {
        sorted.addAll(buckets.get(bucketNum));
      }
      // move up to the next digit
      currentPowerOfTen *= 10;
    }
    
    return sorted;
  }
  
  public static sortStrings(String alphabet, List<String> strings) {
    // Sort alphabet
  
    // Build trie using hashing representation
    TrieNode root = new TrieNode(null);
    for (String string : strings) {
      root.addString(root, string);  
    }
  
    // Compress trie

    // Build 1 array with all nodes
    // If some node v has edges "a", "c", "x", and "q",
    // Add (v, "a"), (v, "c"), (v, "x"), and (v, "q") to the list
    // Will need to number each node 1->n, and also number
    //   the letters for that node with 1->n
    
    // For each node in trie,
    // use radix sort to sort its edges
    List<TrieNode> allNodes = root.getNodesinSubtree();
    for (TrieNode node : allNodes) {
      List<Integer> valuesToSort = new ArrayList<>();
      for (String edge : node.children.keySet()) {
        // Convert edge to an integer
        int edgeValue = 0;
        for (int i = 0; i < edge.length(); i++) {
          char letter = edgeValue.charAt(i);
          char letterIndex = alphabet.indexOf(letter);
          edgeValue += (int) (Math.pow(26, i) * letterIndex);
        }
        valuesToSort.add(edgeValue);
      }
      List<Integer> sortedValues = radixSort(valuesToSort);
      // TODO: Re-assign trie to use sortedValues as children
    }
    
  }
  
  

}
  