import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class SortStrings {
	private static final String ALPHABET = "$abcdefghijklmnopqrstuvwxyz";
	
  public static void main(String[] args) {
	System.out.println("main");
    String alphabet = args[0];
    List<String> argsList = Arrays.asList(args);
    List<String> stringsToSort = argsList.subList(1, argsList.size());
    List<String> sortedStrings = SortStrings.sortStrings(alphabet, stringsToSort);
    System.out.println("sorted strings:");
    System.out.println(sortedStrings);
  }
  
  public static List<Integer> radixSort(List<Integer> inputList) {
    List<List<Integer>> buckets = new ArrayList<>(10);
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
  
  public static String convertIntegerToString(int x) {
	  // TODO
	  throw new UnsupportedOperationException();
  }
  
  public static List<String> sortStrings(String alphabet, List<String> strings) {
    // Sort alphabet
  
    // Build trie using hashing representation
	// TODO: Figure out if map node or arraynode
    TrieNode root = new MapNode(null, null);
    for (String string : strings) {
      root.addString(string);  
    }
  
    // Compress trie

    // Build 1 array with all nodes
    // If some node v has edges "a", "c", "x", and "q",
    // Add (v, "a"), (v, "c"), (v, "x"), and (v, "q") to the list
    // Will need to number each node 1->n, and also number
    //   the letters for that node with 1->n
    
    // For each node in trie,
    // use radix sort to sort its edges
    List<TrieNode> allNodes = root.getSubtree();
    for (TrieNode node : allNodes) {
      List<Integer> valuesToSort = new ArrayList<>();
      for (String edge : node.children().stream().map(x -> x.edgeToMe).collect(Collectors.toList())) {
        // Convert edge to an integer
        int edgeValue = 0;
        for (int i = 0; i < edge.length(); i++) {
          char letter = edge.charAt(i);
          int letterIndex = alphabet.indexOf(letter);
          edgeValue += (int) (Math.pow(26, i) * letterIndex);
        }
        valuesToSort.add(edgeValue);
      }
      List<Integer> sortedValues = radixSort(valuesToSort);

      List<String> sortedEdges = sortedValues.stream()
    		  .map(SortStrings::convertIntegerToString)
    		  .collect(Collectors.toList());
 
      // TODO: Re-assign trie to use sortedEdges as children
    }
    
    // In-order traverse the trie to get the strings in sorted order
    List<String> sortedStrings = new ArrayList<>();
    
    
    return new ArrayList<>();
    
  }
  
  

}
  