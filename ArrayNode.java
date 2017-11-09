import java.util.ArrayList;
import java.util.List;

class ArrayNode extends TrieNode {
    private List<TrieNode> children = new ArrayList<>(26);

    @Override
    void collapseIntoParent() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TrieNode> children() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void removeChildByString(String childToRemove) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void addString(String inputStr) {
        throw new UnsupportedOperationException();
    }

	@Override
	void addChild(TrieNode child) {
        throw new UnsupportedOperationException();		
	}
}

