package my.test.simpleblockchain;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BlockchainUnitTest {

    public static List<Block> blockChain = new ArrayList<>();
    public static int prefix = 4;
    public static String prefixString = new String(new char[prefix])
            .replace('\0','0');

    @BeforeClass
    public static void setup(){

        Block genesisBlock = new Block("This is the Genesis Block.", "0", new Date().getTime());
        String firstMining = genesisBlock.mineBlock(prefix);
        blockChain.add(genesisBlock);

        Block firstBlock = new Block("This is the First Block", genesisBlock.getHash(), new Date().getTime());
        String secondMining = firstBlock.mineBlock(prefix);
        blockChain.add(firstBlock);


        System.out.println("First Mining Result : " + firstMining);
        System.out.println("Second Mining Result : " + secondMining);
        System.out.println("\n");


        System.out.println("Init of blockchain : ");
        blockChain.forEach(System.out::println);
        System.out.println("\n");
    }

    @Test
    public void givenBlockChain_whenNewBlockAdded_thenSuccess(){
        Block newBlock = new Block(
                "This is a New Block.",
                blockChain.get(blockChain.size() - 1).getHash(),
                new Date().getTime());

        String mining = newBlock.mineBlock(prefix);

        System.out.println("Mining Result : " + mining);
        System.out.println("\n");

        Assertions.assertTrue(newBlock.getHash().substring(0, prefix).equals(prefixString));
        blockChain.add(newBlock);

        System.out.println("Blockchain after added a new block : ");
        blockChain.forEach(System.out::println);
        System.out.println("\n");

    }

    @Test
    public void givenBlockChain_whenValidated_thenSuccess(){
        boolean flag = true;
        for (int i=0; i<blockChain.size(); i++){
            String previousHash = i == 0 ? "0"
                    : blockChain.get(i-1)
                    .getHash();

            flag = blockChain.get(i)
                            .getHash()
                            .equals(blockChain.get(i)
                                    .calculateBlockHash())
                    &&
                    previousHash.equals(blockChain.get(i)
                            .getPreviousHash())
                    &&
                    blockChain.get(i)
                    .getHash()
                    .substring(0, prefix)
                    .equals(prefixString);
            if(!flag)
                break;
        }
        Assertions.assertTrue(flag);
    }

    @AfterClass
    public static void tearDown(){
        blockChain.clear();
    }
}
