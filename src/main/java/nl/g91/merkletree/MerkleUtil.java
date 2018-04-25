package nl.g91.merkletree;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sguldemond on 24/04/2018.
 */
public class MerkleUtil {

    public static ArrayList<MerkleTree> getBaseLayer(List<String> hashes) {
        // if amount of hashes is not even duplicate last item
        if(hashes.size() % 2 != 0) {
            hashes.add(hashes.get(hashes.size()-1));
        }

        ArrayList<MerkleTree> baseLayer = new ArrayList<>();

        // create merkle trees from given hashes
        for(int i = 0; i < hashes.size(); i+=2) {
            baseLayer.add(new MerkleTree(hashes.get(i), hashes.get(i+1)));
        }

        return baseLayer;
    }

    public static MerkleTree getFullTree(ArrayList<MerkleTree> layer) {
        if(layer.size() == 1) {
            return layer.get(0);
        }

        ArrayList<MerkleTree> newLayer = new ArrayList<>();

        for(int i = 0; i < layer.size(); i++) {
            MerkleTree leftChild = layer.get(i);

            // if amount of trees in layer is uneven and there's only one tree left,
            // add that tree to the new layer & call generate recursively
            if(i == layer.size()-1 && layer.size() % 2 != 0) {
                newLayer.add(layer.get(i));
                return getFullTree(newLayer);
            }

            MerkleTree rightChild = layer.get(i+1);

            newLayer.add(new MerkleTree(leftChild, rightChild));

            i++;
        }

        return getFullTree(newLayer);
    }

    public static String applyHash(String input) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] byteHash = messageDigest.digest(input.getBytes());

            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : byteHash) {
                stringBuilder.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }

            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }

}