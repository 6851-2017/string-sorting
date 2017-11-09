import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

abstract class TrieNode {

	/** null if this is the root */
	public TrieNode parent;
	public String edgeToMe;

	public TrieNode() {
		this.parent = null;
		this.edgeToMe = "";
	}

	public TrieNode(TrieNode parent, String edgeToMe) {
		this.parent = parent;
		this.edgeToMe = edgeToMe;
	}

	public abstract List<TrieNode> children();

	/** Add inputStr as a string to this node,
	 * if it's not there already
	 */
	abstract void addString(String inputStr);

	/**
	 * Collapses this node into its parent, so that
	 * this node's parent now has this node's child
	 * as its child directly.
	 * 
	 * Required that this node has exactly one child.
	 */
	void collapseIntoParent() {
		assert this.children().size() == 1 : 
			"This node cannot be collapsed";

		// This node's only child should change its parent
		// to be this node's current parent
		TrieNode onlyChild = this.children().get(0);
		onlyChild.parent = this.parent;

		// Change the parent's edge to include this node's edge
		String myEdgeToChild = onlyChild.edgeToMe;
		String edgeToMe = this.edgeToMe;
		onlyChild.edgeToMe = edgeToMe + myEdgeToChild;
		this.parent.removeChildByString(edgeToMe);
		this.parent.addChild(onlyChild);
	}

	abstract void addChild(TrieNode child);

	/** do nothing if not applicable */
	abstract void removeChildByString(String childToRemove);

	/**
	 * Compresses this node in a typical trie compression.
	 * Only needs to be called on the root of your trie.
	 */	
	void compress() {
		Deque<TrieNode> stack = new LinkedList<TrieNode>();
		stack.add(this);
		while (!stack.isEmpty()) {
			TrieNode node = stack.pop();
			if (node.children().size() == 1) {
				node.collapseIntoParent();
			} else {
				for (TrieNode child : node.children()){
					stack.add(child);
				}
			}
		}
	}

	/**
	 * Gets a list of all nodes in the subtree
	 * rooted at this node.
	 * @return the list of nodes in the subtree.
	 */
	List<TrieNode> getSubtree() {
		if (children().isEmpty()) {
			return Collections.singletonList(this);
		}
		List<TrieNode> toReturn = new LinkedList<>();
		toReturn.add(this);
		for (TrieNode child : children()) {
			toReturn.addAll(child.getSubtree());
		}
		return toReturn;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (TrieNode node : this.getSubtree()) {
			stringBuilder.append(String.format(
					"TrieNode<string='%s'>",
					node.edgeToMe
					));
		}
		return stringBuilder.toString();
	}

	/**
	 * Traverses the subtree rooted at this node,
	 *   getting a list of all strings formed by the
	 *   trie rooted at this subtree.
	 * @return the list of strings.
	 */
	List<String> traverse() {
		// Base case
		if (this.children().isEmpty()) {
			return Collections.singletonList(this.edgeToMe);
		}

		// Recursive case
		List<String> results = new ArrayList<>();
		for (TrieNode child : this.children()) {
			List<String> childTraverse = child.traverse();
			for (String childString : childTraverse) {
				results.add(this.edgeToMe + childString);
			}

		}
		return results;
	}


}


