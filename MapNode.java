import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class MapNode extends TrieNode {
	private Map<String, TrieNode> children;

	public MapNode(TrieNode parent, String edgeToMe) {
		super(parent, edgeToMe);
	}

	@Override
	void addString(String inputStr) {
		if (inputStr.length() == 0) {
			return;
		}

		String firstChar = Character.toString(inputStr.charAt(0));
		if (!children.containsKey(firstChar)) {
			children.put(firstChar, new MapNode(parent, firstChar));
		}
		children.get(firstChar).addString(inputStr.substring(1));
	}

	@Override
	public List<TrieNode> children() {
		return new ArrayList<>(this.children.values());
	}

	@Override
	void addChild(TrieNode child) {
		throw new UnsupportedOperationException();
	}

	@Override
	void removeChildByString(String childToRemove) {
		throw new UnsupportedOperationException();

	}
}


