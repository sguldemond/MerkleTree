package nl.g91.merkletree;

/**
 * Created by sguldemond on 24/04/2018.
 */
public class MerkleTree {

    private MerkleTree leftChild;
    private MerkleTree rightChild;

    private String leftHash;
    private String rightHash;

    private String selfHash;

    public MerkleTree(String leftHash, String rightHash) {
        this.leftHash = leftHash;
        this.rightHash = rightHash;

        selfHash = MerkleUtil.applyHash(leftHash + rightHash);
    }

    public MerkleTree(MerkleTree leftChild, MerkleTree rightChild) {
        this.leftChild = leftChild;
        this.rightChild = rightChild;

        this.selfHash = MerkleUtil.applyHash(leftChild.getSelfHash() + rightChild.getSelfHash());
    }


    public String getSelfHash() {
        return selfHash;
    }

    public MerkleTree getLeftChild() {
        return leftChild;
    }

    public MerkleTree getRightChild() {
        return rightChild;
    }
}
