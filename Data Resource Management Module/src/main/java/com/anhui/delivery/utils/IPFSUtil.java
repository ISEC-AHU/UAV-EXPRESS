package com.anhui.delivery.utils;

import java.io.File;
import java.io.IOException;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;

public class IPFSUtil {
	   private static IPFS ipfs = new IPFS("/ip4/127.0.0.1/tcp/5001");

	   public static String add(File file) throws IOException {
		   
	       NamedStreamable.FileWrapper file1 = new NamedStreamable.FileWrapper(file);
	       MerkleNode addResult = ipfs.add(file1).get(0);
	       return addResult.hash.toString();
	   }

	   public static String cat(String hash) throws IOException {
	        byte[] data = ipfs.cat(Multihash.fromBase58(hash));
	        return new String(data);
	   }
	}
